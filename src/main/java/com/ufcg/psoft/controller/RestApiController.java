package com.ufcg.psoft.controller;

import java.text.ParseException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ufcg.psoft.model.*;
import com.ufcg.psoft.model.DTO.*;
import com.ufcg.psoft.service.*;
import com.ufcg.psoft.util.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.ProdutoVendido;
import com.ufcg.psoft.model.DTO.LoteDTO;
import com.ufcg.psoft.model.DTO.ProdutoVencidoDTO;
import com.ufcg.psoft.service.CategoriaService;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.VendaService;

import com.ufcg.psoft.util.CustomErrorType;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private LoteService loteService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private VendaService vendaService;

	@Autowired
	private RelatorioService relatorioService;

	@RequestMapping(value = "/produto/", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutos() {
		List<Produto> produtos = null;
		try {
			produtos = produtoService.findAll();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType(Mensagens.Produto.NENHUM_ENCONTRADO),
					HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {
		if (produtoService.existsByCodigoBarra(produto.getCodigoBarra())) {
			return new ResponseEntity(new CustomErrorType(Mensagens.Produto.JAH_CADASTRADO), HttpStatus.CONFLICT);
		}
		Produto produtoFinal = null;
		try {
			produto.setDisponibilidade(false);
			produtoFinal = produtoService.save(produto);
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<Produto>(produtoFinal, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {
		Produto produto = null;
		try {
			produto = produtoService.findById(id);
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.ID_INVALIDO)) {
				status = HttpStatus.BAD_REQUEST;
			} else if (exception.getMessage().equals(Mensagens.Produto.NAO_ENCONTRADO)) {
				status = HttpStatus.NOT_FOUND;
			}
			return new ResponseEntity(new CustomErrorType("Erro: " + exception.getMessage()), status);
		}
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}/detalhes", method = RequestMethod.GET)
	public ResponseEntity<?> detalhesProduto(@PathVariable("id") long id) {
		ProdutoDetalhadoDTO produtoDetalhado = null;
		try {
			Produto produto = produtoService.findById(id);
			produtoDetalhado = getProdutoDetalhado(produto);
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.ID_INVALIDO)) {
				status = HttpStatus.BAD_REQUEST;
			} else if (exception.getMessage().equals(Mensagens.Produto.NAO_ENCONTRADO)) {
				status = HttpStatus.NOT_FOUND;
			}
			return new ResponseEntity(new CustomErrorType("Erro: " + exception.getMessage()), status);
		}

		return new ResponseEntity<ProdutoDetalhadoDTO>(produtoDetalhado, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarProduto(@PathVariable("id") long id, @RequestBody Produto produto) {
		Produto produtoFinal = null;
		try {
			produtoFinal = produtoService.findById(id);
			produtoFinal.mudaNome(produto.getNome());
			produtoFinal.setPreco(produto.getPreco());
			produtoFinal.setCodigoBarra(produto.getCodigoBarra());
			produtoFinal.mudaFabricante(produto.getFabricante());
			produtoFinal.mudaCategoria(produto.getCategoria());

			produtoService.save(produtoFinal);
		} catch (RuntimeException exception) {
			CustomErrorType mensagemErro = new CustomErrorType("Erro: " + exception.getMessage());
			if (exception.getMessage().equals(Mensagens.Produto.NAO_ENCONTRADO)) {
				return new ResponseEntity(mensagemErro, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(mensagemErro, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		return new ResponseEntity<Produto>(produtoFinal, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarProduto(@PathVariable("id") long id) {
		Produto produto = null;
		try {
			produtoService.deleteById(id);
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.ID_INVALIDO)) {
				status = HttpStatus.BAD_REQUEST;
			} else if (exception.getMessage().equals(Mensagens.Produto.NAO_ENCONTRADO)) {
				status = HttpStatus.NOT_FOUND;
			}
			return new ResponseEntity(new CustomErrorType("Erro: " + exception.getMessage()), status);
		}
		return new ResponseEntity<Produto>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/produtos/fabricante", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutosOrdenadoPorFabricante() {
		List<Produto> produtos = null;
		try {
			produtos = produtoService.findAllByFabricante();
		} catch(RuntimeException exception){
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.Produto.NENHUM_ENCONTRADO)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: "+ exception.getMessage()), status);
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produtos/nome", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutosOrdenadoPorNome() {
		List<Produto> produtos = null;
		try {
			produtos = produtoService.findAllByNome();
		} catch(RuntimeException exception){
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.Produto.NENHUM_ENCONTRADO)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: "+ exception.getMessage()), status);
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produtos/preco", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutosOrdenadoPorPreco() {
		List<Produto> produtos = null;
		try {
			produtos = produtoService.findAllByPreco();
		} catch(RuntimeException exception){
			return new ResponseEntity(new CustomErrorType("Error: "+ exception.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produtos/categoria", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutosOrdenadoPorCategoria() {
		List<Produto> produtos = null;
		try {
			produtos = produtoService.findAllByCategoria();
		} catch(RuntimeException exception){
			return new ResponseEntity(new CustomErrorType("Error: "+ exception.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{categoria}/categoria", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutoOrdenadoPorCategoria(@PathVariable String categoria) {
		List<Produto> produtos = null;
		try {
			produtos = produtoService.findProdutoByCategoria(categoria);
		} catch(RuntimeException exception){
			return new ResponseEntity(new CustomErrorType("Error: "+ exception.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{nome}/nome", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutoOrdenadoPorNome(@PathVariable String nome) {
		List<Produto> produtos = null;
		try {
			produtos = produtoService.findProdutoByNome(nome);
		} catch(RuntimeException exception){
			return new ResponseEntity(new CustomErrorType("Error: "+ exception.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}/lote", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@PathVariable("id") long produtoId, @RequestBody LoteDTO loteDTO) {
		Lote lote = null;
		try {
			Produto produto = produtoService.findById(produtoId);
			lote = loteService.save(new Lote(produto, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade()));

			if (!produto.getDisponibilidade()) {
				if (loteDTO.getNumeroDeItens() > 0) {
					Produto produtoDisponivel = produto;
					produtoDisponivel.setDisponibilidade(true);
					produtoService.save(produtoDisponivel);
				}
			}
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.Produto.NAO_ENCONTRADO)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), status);
		}

		return new ResponseEntity<>(lote, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/lote", method = RequestMethod.GET)
	public ResponseEntity<List<Lote>> listarLotes() {
		List<Lote> lotes = null;
		try {
			lotes = loteService.findAll();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType(Mensagens.Lote.NENHUM_ENCONTRADO), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/baixa", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> produtoLow() {
		List<Produto> produtosLow = null;
		try {
			produtosLow = produtoService.findAllEmBaixa();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType(Mensagens.Produto.NENHUM_EM_BAIXA),
					HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Produto>>(produtosLow, HttpStatus.OK);
	}

	@RequestMapping(value = "/categorias", method = RequestMethod.GET)
	public ResponseEntity<List<Categoria>> listarCategorias() {
		List<Categoria> categorias = null;
		try {
			categorias = categoriaService.findAll();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType(Mensagens.Categoria.NENHUMA_ENCONTRADA), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Categoria>>(categorias, HttpStatus.OK);
	}

	@RequestMapping(value = "/categoria/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> consultarCategoria(@PathVariable Long id) {
		Categoria categoria = null;

		try {
			categoria = categoriaService.findById(id);
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.Categoria.NAO_ENCONTRADA)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), status);
		}

		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
	}

	@RequestMapping(value = "/categoria", method = RequestMethod.POST)
	public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
		Categoria c = null;

		try {
			c = categoria;
			categoriaService.existsById(c.getId());
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.Categoria.NAO_ENCONTRADA)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), status);
		}

		if (categoriaService.existsById(c.getId())) {
			return new ResponseEntity(new CustomErrorType("This Category " + categoria.getNome() + " already exists "), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Categoria>(categoriaService.save(categoria), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/categoria/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> apagarCategoria(@PathVariable Long id) {
		Categoria c = null;

		try {
			categoriaService.deleteById(id);
		} catch (RuntimeException exception) {
			HttpStatus status = null;

			if (exception.getMessage().equals(Mensagens.Categoria.NAO_ENCONTRADA)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), status);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/categoria/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Categoria> colocarDesconto(@PathVariable Long id, BigDecimal discount) {
		Categoria categoria = categoriaService.findById(id);

		if (categoria == null) {
			return new ResponseEntity(new CustomErrorType("Unable to see. This Category with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		try {
			categoria.setDesconto(discount);

		} catch (RuntimeException exception) {
			HttpStatus status = null;

			if (exception.getMessage().equals(Mensagens.Categoria.NAO_ENCONTRADA)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), status);

		}
		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
	}


	@RequestMapping(value = "/venda", method = RequestMethod.POST)
	public ResponseEntity<?> criarVenda(@RequestBody Venda venda) {

		Venda v = null;

		try {
			v = venda;
			vendaService.existsById(v.getId());

			Double result = venda.getValor();
			Map<Long, Integer> idProdutosVendidos = new HashMap<>();

			for (ProdutoVendido p : venda.getProdutosVendidos()) {
				BigDecimal desconto = p.getProduto().getCategoria().getDesconto().multiply(p.getProduto().getPreco());
				BigDecimal valorFinal = p.getProduto().getPreco().subtract(desconto);
				result += valorFinal.multiply(new BigDecimal(p.getQuantProdutos())).doubleValue();
				loteService.deleteQtdById(p.getProduto().getId(), p.getQuantProdutos());

			}

			venda.setValor(result);

			vendaService.save(venda);
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.Venda.NAO_ENCONTRADA)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), status);
		}

		return new ResponseEntity<Venda>(venda, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/vendas", method = RequestMethod.GET)
	public ResponseEntity<List<Venda>> listarVendas() {
		List<Venda> v = null;
		try {
			v = vendaService.findAll();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType(Mensagens.Venda.NENHUMA_ENCONTRADA),
					HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Venda>>(v, HttpStatus.OK);

	}

	@RequestMapping(value = "/venda/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarVenda(@PathVariable("id") long id) {

		Venda v = null;

		try {
			v = vendaService.findById(id);
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.Venda.NAO_ENCONTRADA)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), status);
		}


		return new ResponseEntity<Venda>(v, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/produto/{id}/vencido", method = RequestMethod.PUT)
	public ResponseEntity<?>produtoVencido(@PathVariable("id") long id) {
		ProdutoVencidoDTO produtoVencido = null;
		try {
			Produto produto = produtoService.findVencido(id);
			if(produto.getDisponibilidade() == false) {
				produtoVencido = new ProdutoVencidoDTO(produto.getId(), produto.getNome(), produto.getCodigoBarra(), produto.getFabricante(),
						produto.getDisponibilidade(), produto.getCategoria());
			}
		} catch (RuntimeException exception) {
			HttpStatus status = null;
			if (exception.getMessage().equals(Mensagens.Venda.NAO_ENCONTRADA)) {
				status = HttpStatus.NOT_FOUND;
			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
			}
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), status);
		}
		
		return new ResponseEntity<ProdutoVencidoDTO>(produtoVencido, HttpStatus.OK);
	}

	@RequestMapping(value = "/venda/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> cancelaVenda(@PathVariable("id") long id) {
		try {
			vendaService.deleteById(id);
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType("Error " + exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/vendas/data", method = RequestMethod.GET)
	public ResponseEntity<List<Venda>> listarVendasOrdenadoPorData() {
		List<Venda> vendas = null;
		try {
			vendas = vendaService.findAllByData();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Venda>>(vendas, HttpStatus.OK);
	}

	@RequestMapping(value = "/vendas/valor", method = RequestMethod.GET)
	public ResponseEntity<List<Venda>> listarVendasOrdenadoPorValor() {
		List<Venda> vendas = null;
		try {
			vendas = vendaService.findAllByValor();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Venda>>(vendas, HttpStatus.OK);
	}

	@RequestMapping(value = "/relatorio", method = RequestMethod.GET)
	public ResponseEntity<?> mostraRelatorio() {
		Relatorio relatorio = null;
		try {
			relatorio = relatorioService.make();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Relatorio>(relatorio, HttpStatus.OK);
	}

	private ProdutoDetalhadoDTO getProdutoDetalhado(Produto produto) {
		return new ProdutoDetalhadoDTO(produto.getPreco(), produto.getDisponibilidade());
	}

	@RequestMapping(value = "/lotes/vencer", method = RequestMethod.GET)
	public ResponseEntity<List<Lote>> lotesHaVencer() throws ParseException {
		List<Lote> lotes = null;
		try {
			lotes = loteService.findAllHaVencer();
		} catch (RuntimeException exception) {
			return new ResponseEntity(new CustomErrorType("Error: " + exception.getMessage()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
}
