/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.services;

import com.example.testeMap.model.entidades.ItemPedido;
import com.example.testeMap.model.entidades.Produto;
import com.example.testeMap.repository.ItemPedidoRepository;
import com.example.testeMap.repository.PedidoRepository;
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
public class ItemPedidoService {

    private ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public ItemPedido CadastrarItemPedido(ItemPedido itemPedido) {
        itemPedidoRepository.save(itemPedido);
        return itemPedido;
    }

    public ResponseEntity<ItemPedido> filtroID(int id) {
        return itemPedidoRepository.findById(id).
                map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity updateItemPedido(int id,
            ItemPedido itemPedido) {
        return itemPedidoRepository.findById(id)
                .map(ItemPedidoAtt -> {
                    itemPedido.setIdItem(id);
                    ItemPedidoAtt.setIdPedido(itemPedido.getIdPedido());
                    ItemPedidoAtt.setIdProduto(itemPedido.getIdProduto());
                    ItemPedidoAtt.setQntd(itemPedido.getQntd());
                    ItemPedidoAtt.setValorUnitario(itemPedido.getValorUnitario());
                    
                    ItemPedido updated = itemPedidoRepository.save(ItemPedidoAtt);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    public List<ItemPedido> carregarItemPedido() {
        return (List<ItemPedido>) itemPedidoRepository.findAll();
    }

}
