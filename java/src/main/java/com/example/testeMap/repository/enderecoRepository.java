/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.repository;


import com.example.testeMap.model.entidades.Endereco;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Programming
 */
public interface enderecoRepository extends CrudRepository<Endereco, Integer>{

    @Query(value="select * from produto where nome like %:nome%",nativeQuery=true)
    public List<Endereco> filtroCEP(String nome);

    @Query(value="select * from endereco where tipo like %:tipo%",nativeQuery=true)
    public List<Endereco> filtroTipo(String tipo);

}