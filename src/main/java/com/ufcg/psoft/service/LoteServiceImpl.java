package com.ufcg.psoft.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.util.Mensagens;
import com.ufcg.psoft.repository.LoteRepository;
import com.ufcg.psoft.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Lote;

import static java.util.Objects.isNull;

import java.util.Iterator;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private LoteService loteService;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Lote save(Lote lote) {
		verificaIdValido(lote.getId());
		if (isNull(lote)) {
			throw new RuntimeException(Mensagens.Lote.NULO);
		}
		if (isNull(lote.getProduto())) {
			throw new RuntimeException(Mensagens.Produto.NULO);
		}
		if (isNull(lote.getDataDeValidade())) {
			throw new RuntimeException(Mensagens.Lote.DATA_VALIDADE_NULA);
		}
		return loteRepository.save(lote);
	}

	@Override
	public Lote findById(long id) {
		verificaIdValido(id);
		verificaLoteExistente(id);
		Optional<Lote> optionalLote = loteRepository.findById(id);
		return optionalLote.get();
	}


	@Override
	public void deleteById(long id) {
		verificaIdValido(id);
		verificaLoteExistente(id);
		loteRepository.deleteById(id);
	}

	@Override
	public List<Lote> findAll() {
		List<Lote> lotes = loteRepository.findAll();
		if (lotes.isEmpty()) {
			throw new RuntimeException(Mensagens.Lote.NENHUM_ENCONTRADO);
		}
		return lotes;
	}

	private void verificaIdValido(long id) {
		if (id <= 0) {
			throw new RuntimeException(Mensagens.ID_INVALIDO);
		}
	}

	private void verificaLoteExistente(long id) {
		if (!loteRepository.existsById(id)) {
			throw new RuntimeException(Mensagens.Lote.NAO_ENCONTRADO);
		}
	}

	public void deleteQtdById(long id, int quantProdutos) {

		if(isNull(id)) {

            throw new RuntimeException(Mensagens.Lote.NULO);
        }
	
        List<Lote> lotes = loteService.findLotesDispByProdutoId(id);
            
        if (lotes.isEmpty()) {
        	throw new RuntimeException(Mensagens.Lote.NENHUM_ENCONTRADO);
        }
        
        int count = quantProdutos;
               
        Iterator<Lote> iterator = lotes.iterator();   
        Lote lote = null;
                     
        while (iterator.hasNext() && count > 0) {
        	lote = iterator.next();
        	if (lote.getNumeroDeItens() - count <= 0) {
        		lote.setNumeroDeItens(0);
        	}
        	else {
        		lote.setNumeroDeItens(lote.getNumeroDeItens() - count);
        	}
        	count -= lote.getNumeroDeItens();
        	
        	
        }
      
        if(!iterator.hasNext() && !isNull(lote)) {
        	if(lote.getNumeroDeItens() == 0) {
        		lote.getProduto().setDisponibilidade(false);
        		produtoRepository.save(lote.getProduto());
        	}
        }
        
        loteService.deleteQtdById(id, quantProdutos);      
	}

	public List<Lote> findLotesDispByProdutoId(long produtoId) {
		if(isNull(produtoId)) {
			throw new RuntimeException(Mensagens.Lote.NULO);
		}

		return loteService.findLotesDispByProdutoId(produtoId);
	}

	@Override
	public List<Lote> findAllHaVencer() {
		List<Lote> lotes = loteService.findAll();
		if (lotes.isEmpty()) {
			throw new RuntimeException(Mensagens.Lote.NENHUM_ENCONTRADO);
		}
		List<Lote> lotesHaVencer = new ArrayList();

		for (Lote l : lotes) {
			try {
				if (l.diasParaVencer() < 100) {
					lotesHaVencer.add(l);
				}
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return lotesHaVencer;
	}
}