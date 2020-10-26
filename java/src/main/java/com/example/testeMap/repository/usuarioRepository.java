/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.repository;

import com.example.testeMap.model.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Programming
 */
public interface usuarioRepository extends CrudRepository<Usuario, Integer> {
    
    @Query(value="select * from usuario where nome like %:nome%",nativeQuery=true)
    public List<Usuario> filtroNome(String nome);

    @Query(value="select * from usuario where cpf like %:cpf%",nativeQuery=true)
    public List<Usuario> filtroCpf(String cpf);
}