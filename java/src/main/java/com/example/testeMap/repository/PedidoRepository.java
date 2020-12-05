/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.repository;

import com.example.testeMap.model.entidades.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Programming
 */
public interface PedidoRepository extends CrudRepository<Pedido, Integer> {
    
    @Query(value="SELECT * FROM pedido WHERE id_usuario like %:id%",nativeQuery=true)
    public List<Pedido> filtroPedidoById(int id);
    
}
