/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.services;

import com.example.testeMap.model.entidades.Produto;
import com.example.testeMap.repository.produtoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Service
public class produtoService {
    
    private produtoRepository produtoRepository;

    public produtoService(produtoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    
    public Produto CadastrarProduto(Produto produto) {
        produtoRepository.save(produto);
        return produto;
    }
    
    public ResponseEntity<Produto> filtroID(int id) {
        return produtoRepository.findById(id).
                map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
    
    public ResponseEntity updateProd(int id,
             Produto prod) {
        return produtoRepository.findById(id)
                .map(prodAtualizacao -> {
                    prodAtualizacao.setIdProduto(id);
                    prodAtualizacao.setNomeProduto(prod.getNomeProduto());
                    prodAtualizacao.setQtde(prod.getQtde());
                    prodAtualizacao.setMarca(prod.getMarca());
                    prodAtualizacao.setCategoria(prod.getCategoria());
                    prodAtualizacao.setDescricao(prod.getDescricao());
                    prodAtualizacao.setValor(prod.getValor());
                    prodAtualizacao.setAtivo(prod.isAtivo());
                    Produto updated = produtoRepository.save(prodAtualizacao);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    
        public List<Produto> carregarProdutos() {
        return (List<Produto>) produtoRepository.findAll();
    }
        
        public List<Produto> filtroNome(String nome) {
        return produtoRepository.filtroNome(nome);
    }
        public List<Produto> filtroCategoria(String categoria) {
        return produtoRepository.filtroCategoria(categoria);
    }

        public List<Produto> filtroIndexRamdom(){
            return produtoRepository.filtroIndexRamdom();
        }
        public List<Produto> filtro5MaisVendidosGeral(){
            return produtoRepository.filtro5MaisVendidosGeral();
        }
        
        public List<Produto> filtro5MaisVendidosCategoria(String categoria){
            return produtoRepository.filtro5MaisVendidosCategoria(categoria);
        }
        
    public Optional<Produto> filtroId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
