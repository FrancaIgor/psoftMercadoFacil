package com.ufcg.psoft.service;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.repository.CategoriaRepository;
import com.ufcg.psoft.util.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;


/**
 * @author Igor Franca
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public boolean existsById(Long id){

        if(isNull(id)) {
            throw new RuntimeException(Mensagens.Categoria.NULO);
        }
        if(!categoriaRepository.existsById(id)) {
            throw new RuntimeException(Mensagens.Categoria.NAO_ENCONTRADA);
        }
        return categoriaRepository.existsById(id);
    }

    @Override
    public List<Categoria> findAll() {

        Iterable<Categoria> categorias = categoriaRepository.findAll();
        List<Categoria> result = new ArrayList<>();

        for(Categoria c : categorias) {
            result.add(c);
        }

        if(result.isEmpty())
            throw new RuntimeException(Mensagens.Categoria.NENHUMA_ENCONTRADA);

        return result;
    }

    @Override
    public Categoria findById(Long id) {

        if(isNull(id)) {
            throw new RuntimeException(Mensagens.Categoria.NULO);
        }
        if(!categoriaRepository.existsById(id)) {
            throw new RuntimeException(Mensagens.Categoria.NAO_ENCONTRADA);
        }
        return categoriaRepository.findById(id).get();
    }
    @Override
    public Categoria save(Categoria categoria) {

        if(isNull(categoria)) {
            throw new RuntimeException(Mensagens.Categoria.NULO);
        }
        if(isNull(categoria.getDesconto())) {
            throw new RuntimeException(Mensagens.Categoria.NULO);
        }
        if(isNull(categoria.getNome())) {
            throw new RuntimeException(Mensagens.Categoria.NULO);
        }

        return categoriaRepository.save(categoria);
    }
    @Override
    public void deleteById(Long id) {

        if(isNull(id)) {
            throw new RuntimeException(Mensagens.Categoria.NULO);
        }
        if(!categoriaRepository.existsById(id)) {
            throw new RuntimeException(Mensagens.Categoria.NAO_ENCONTRADA);
        }

        categoriaRepository.deleteById(id);
    }
}