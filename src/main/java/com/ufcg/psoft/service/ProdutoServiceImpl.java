package com.ufcg.psoft.service;

import static java.util.Objects.isNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import com.ufcg.psoft.model.DTO.ProdutoQuantidadeDTO;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.repository.LoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Produto;

import com.ufcg.psoft.repository.ProdutoRepository;
import com.ufcg.psoft.util.GenericQuickSort;
import com.ufcg.psoft.util.Mensagens;

@Service
public class ProdutoServiceImpl implements ProdutoService{

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private LoteRepository loteRepository;

	@Override
	public Produto findById(long id) {
		verificaIdValido(id);
		verificaProdutoExistente(id);
		Optional<Produto> optionalProduto = produtoRepository.findById(id);
		return optionalProduto.get();
	}

	@Override
	public boolean existsByCodigoBarra(String codigoBarra) {
		if(isNull(codigoBarra)) {
			throw new RuntimeException(Mensagens.Produto.CODIGO_BARRA_NULO);
		}
		return produtoRepository.existsByCodigoBarra(codigoBarra);
	}
	
	@Override
	public List<Produto> findAllByDisponibilidade(boolean disponibilidade){
        Iterable<Produto> produtos = produtoRepository.findAllByDisponibilidade(disponibilidade);
        List<Produto> resultadoProdutos = new ArrayList<>();

        for(Produto product : produtos){
            resultadoProdutos.add(product);
        }

        return resultadoProdutos;
	}

	@Override
	public List<Produto> findAllEmBaixa() {
		List<Lote> lotes = loteRepository.findAll();
		if (lotes.isEmpty()) {
			throw new RuntimeException(Mensagens.Lote.NENHUM_ENCONTRADO);
		}

		List<ProdutoQuantidadeDTO> allProdutosLotes = new ArrayList();
		List<Produto> produtosLow = new ArrayList();

		for (Lote l : lotes) {
			ProdutoQuantidadeDTO produtoDTO = new ProdutoQuantidadeDTO(l.getProduto(), l.getNumeroDeItens());
			if (allProdutosLotes.contains(produtoDTO)) {
				int index = allProdutosLotes.indexOf(produtoDTO);
				allProdutosLotes.get(index).incrementaQuantd(produtoDTO.getQuantidade());
			} else {
				allProdutosLotes.add(produtoDTO);
			}
		}
		for (ProdutoQuantidadeDTO produto : allProdutosLotes) {
			if (produto.getQuantidade() < 15) {
				produtosLow.add(produto.getProduto());
			}
		}
		return produtosLow;
	}

	@Override
	public Produto save(Produto produto) {
		verificaIdValido(produto.getId());
		if (isNull(produto)) {
			throw new RuntimeException(Mensagens.Produto.NULO);
		}
		if (isNull(produto.getNome())) {
			throw new RuntimeException(Mensagens.Produto.NOME_NULO);
		}
		if (isNull(produto.getCategoria())) {
			throw new RuntimeException(Mensagens.Produto.CATEGORIA_NULA);
		}
		if (isNull(produto.getPreco())) {
			throw new RuntimeException(Mensagens.Produto.PRECO_NULO);
		}
		if (isNull(produto.getCodigoBarra())) {
			throw new RuntimeException(Mensagens.Produto.CODIGO_BARRA_NULO);
		}
		if (isNull(produto.getFabricante())) {
			throw new RuntimeException(Mensagens.Produto.FABRICANTE_NULO);
		}
		return produtoRepository.save(produto);
	}

	@Override
	public void deleteById(long id) {
		verificaIdValido(id);
		verificaProdutoExistente(id);
		produtoRepository.deleteById(id);
	}

	private void verificaIdValido(long id) {
		if (id <= 0) {
			throw new RuntimeException(Mensagens.ID_INVALIDO);
		}
	}

	private void verificaProdutoExistente(long id) {
		if (!produtoRepository.existsById(id)) {
			throw new RuntimeException(Mensagens.Produto.NAO_ENCONTRADO);
		}
	}


	@Override
	public List<Produto> findAll() {

		Iterable<Produto> products = produtoRepository.findAll();
		List<Produto> result = new ArrayList<>();

		for(Produto p : products) {
			result.add(p);
		}
		if(result.isEmpty())
			throw new RuntimeException(Mensagens.Produto.NENHUM_ENCONTRADO);

		return result;
	}

	@Override
	public List<Produto> findAllByNome() {
			Iterable<Produto> products = produtoRepository.findAll();
			List<Produto> result = new ArrayList<>();

			for(Produto p : products){
				result.add(p);
			}

			if(result.isEmpty())
				throw new RuntimeException(Mensagens.Produto.NENHUM_ENCONTRADO);

			GenericQuickSort quickSort = new GenericQuickSort();
			quickSort.quickSortNome(result,0,result.size()-1);

			return result;
	}


	@Override
	public List<Produto> findAllByFabricante() {
			Iterable<Produto> products = produtoRepository.findAll();
			List<Produto> result = new ArrayList<>();

			for(Produto p : products){
				result.add(p);
			}

			if(result.isEmpty())
				throw new RuntimeException(Mensagens.Produto.NENHUM_ENCONTRADO);

			GenericQuickSort quickSort = new GenericQuickSort();
			quickSort.quickSortFabricante(result,0,result.size()-1);

			return result;

	}

	@Override
	public List<Produto> findAllByPreco() {
		Iterable<Produto> products = produtoRepository.findAll();
		List<Produto> result = new ArrayList<>();
		for(Produto p : products){
			result.add(p);
		}
		if(result.isEmpty())
			throw new RuntimeException(Mensagens.Produto.NENHUM_ENCONTRADO);

		GenericQuickSort quickSort = new GenericQuickSort();
		quickSort.quickSortPreco(result,0,result.size()-1);

		return result;
	}

	@Override
	public List<Produto> findAllByCategoria() {
		Iterable<Produto> products = produtoRepository.findAll();
		List<Produto> result = new ArrayList<>();

		for(Produto p : products){
			result.add(p);
		}

		if(result.isEmpty())
			throw new RuntimeException(Mensagens.Produto.NENHUM_ENCONTRADO);
		
		GenericQuickSort quickSort = new GenericQuickSort();
		quickSort.quickSortCategoria(result,0,result.size()-1);

		return result;
	}

	@Override
	public Produto findVencido(long id) {
		Produto produto = findById(id);
		List<Lote> lotes = loteRepository.findAllByProduto(produto);
		
		for (Lote lote : lotes) {
			try {
				Date date = new SimpleDateFormat("dd-MM-yyyy").parse(lote.getDataDeValidade());
				Date dataAtual = new Date();
				if(date.compareTo(dataAtual) < 0) {
					loteRepository.deleteById(lote.getId());
					lotes.remove(lote);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(lotes.isEmpty()) {
			produto.setDisponibilidade(false);
			produtoRepository.save(produto);
		} else {
			throw new RuntimeException("Produto não passou da validade");
		}
		
		return produto;

	}

	@Override
	public List<Produto> findProdutoByCategoria(String categoria){
		Iterable<Produto> products = produtoRepository.findAll();
		List<Produto> result = new ArrayList<>();
		for(Produto produto:  products){
			if(produto.getCategoria().getNome().equals(categoria.replace("-"," "))){
				result.add(produto);
			}
		}
		if(result.isEmpty())
			throw new RuntimeException(Mensagens.Produto.NENHUM_ENCONTRADO);

		return result;
	}

	@Override
	public List<Produto> findProdutoByNome(String nome) {
		Iterable<Produto> products = produtoRepository.findAll();
		List<Produto> result = new ArrayList<>();

		for(Produto produto:  products){
			if(produto.getNome().equals(nome.replace("-"," "))){
				result.add(produto);
			}
		}
		if(result.isEmpty())
			throw new RuntimeException(Mensagens.Produto.NENHUM_ENCONTRADO);

		return result;
	}
}

