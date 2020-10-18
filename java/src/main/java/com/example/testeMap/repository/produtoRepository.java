/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.repository;


import com.example.testeMap.model.entidades.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Programming
 */
public interface produtoRepository extends CrudRepository<Produto, Integer>{

    @Query(value="select * from produto where nome like %:nome%",nativeQuery=true)
    public List<Produto> filtroNome(String nome);

    @Query(value="select * from produto where id_produto like %:id%",nativeQuery=true)
    public List<Produto> filtroID(int id);
    
    @Query(value="select * from produto where categoria like %:categoria%",nativeQuery=true)
    public List<Produto> filtroCategoria(String categoria);

}
