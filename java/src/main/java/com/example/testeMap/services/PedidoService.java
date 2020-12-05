package com.example.testeMap.services;

import com.example.testeMap.model.entidades.Pedido;
import com.example.testeMap.repository.PedidoRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class PedidoService {
    
    private PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
    
    public Pedido CadastrarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
        return pedido;
    }
    
    public ResponseEntity<Pedido> filtroID(int id) {
        return pedidoRepository.findById(id).
                map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }    
    
    public ResponseEntity updatePedido(int id,
             Pedido pedido) {
        return pedidoRepository.findById(id)
                .map(pedidoAtualizacao -> {
                    pedidoAtualizacao.setIdPedido(id);
                    pedidoAtualizacao.setIdUsuario(pedido.getIdUsuario());
                    pedidoAtualizacao.setIdEndereco(pedido.getIdEndereco());
                    pedidoAtualizacao.setValorTotal(pedido.getValorTotal());
                    pedidoAtualizacao.setMetodoPagamento(pedido.getMetodoPagamento());
                    pedidoAtualizacao.setNumCartao(pedido.getNumCartao());
                    pedidoAtualizacao.setParcelas(pedido.getParcelas());
                    pedidoAtualizacao.setBoleto(pedido.getBoleto());
                    pedidoAtualizacao.setStatus(pedido.getStatus());
                    
                    Pedido updated = pedidoRepository.save(pedidoAtualizacao);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    
        public List<Pedido> carregarPedido() {
        return (List<Pedido>) pedidoRepository.findAll();
    }
        
    public List<Pedido> carregarPedidoByID(int id) {
        return (List<Pedido>) pedidoRepository.filtroPedidoById(id);
    }
}