/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.repository;


import com.example.testeMap.model.entidades.ItensCarrinho;
import com.example.testeMap.model.entidades.Produto;
import java.util.List;
import java.util.Optional;
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
    
    @Query(value="SELECT * FROM produto WHERE categoria like %:categoria% AND ativo=1 ORDER BY id_produto DESC LIMIT 9 OFFSET 0" ,nativeQuery=true)
    public List<Produto> filtroCategoria(String categoria);

    @Query(value="SELECT * FROM produto WHERE ativo = 1 ORDER BY RAND ( ) LIMIT 2",nativeQuery = true)
    public List<Produto> filtroIndexRamdom();
    
    @Query(value="SELECT id_produto,SUM(qntd) FROM item_pedido GROUP BY id_produto ORDER BY SUM(qntd) DESC LIMIT 5",nativeQuery = true)
    public List<Produto> filtro5MaisVendidosGeral();
    
    @Query(value="SELECT produto.id_produto,produto.nome,produto.qtde,produto.categoria,produto.marca,produto.descricao,produto.valor,produto.ativo,SUM(item_pedido.qntd) FROM produto INNER JOIN item_pedido ON item_pedido.id_produto = produto.id_produto WHERE produto.categoria LIKE %:categoria% AND produto.ativo LIKE 1 GROUP BY item_pedido.id_produto ORDER BY SUM(item_pedido.qntd) DESC LIMIT 5",nativeQuery = true)
    public List<Produto> filtro5MaisVendidosCategoria(String categoria);
    
    
}

