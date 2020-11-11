/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.ItensCarrinho;
import com.example.testeMap.model.entidades.Produto;
import com.example.testeMap.services.imagemService;
import com.example.testeMap.services.produtoService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author AlexSandey, Devakian
 */
@RestController
@RequestMapping("/carrinho")
public class carrinhoController {

    private produtoService produtoService;

    private imagemService imagemService;

    public carrinhoController(produtoService produtoService, imagemService imagemService) {
        this.produtoService = produtoService;
        this.imagemService = imagemService;
    }

    @GetMapping
    public ModelAndView carregarCarrinhoView(
            HttpSession session
    ) {

        List<ItensCarrinho> carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");

        return new ModelAndView("produto/carrinho").addObject(carrinho);
    }

    @GetMapping("/adicionar")
    public ModelAndView adicionarCarrinho(
            @RequestParam(name = "adicionarCarrinho", required = false) int adicionarCarrinho,
            HttpSession session
    ) {

        List<ItensCarrinho> carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");

        if (carrinho == null) {
            carrinho = new ArrayList<ItensCarrinho>();
        }

        boolean verifica = false;

        for (ItensCarrinho item : carrinho) {

            if (item.getIdProduto() == adicionarCarrinho) {
                verifica = true;
                item.setQtde(item.getQtde() + 1);
            }

        }

        if (verifica == false) {
            ResponseEntity<Produto> produtoCadastrado = produtoService.filtroID(adicionarCarrinho);

            Produto produto = produtoCadastrado.getBody();

            String caminho = imagemService.caminhoImagemMin(produto.getIdProduto());

            ItensCarrinho itensCarrinho = new ItensCarrinho(produto.getIdProduto(), produto.getNomeProduto(), 1, caminho, produto.getValor());

            carrinho.add(itensCarrinho);
        }

        session.setAttribute("carrinho", carrinho);

        return new ModelAndView("redirect:/carrinho");
    }
    
    
    @PostMapping("/atualizar")
    public ModelAndView atualizarQuantidadeCarrinhoView(
            @RequestParam(name = "qtde_produto", required = false) int qtde_produto,
            @RequestParam(name = "id_produto", required = false) int id_produto,
            HttpSession session
    ) {

        List<ItensCarrinho> carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");
        
        
        try{
           for (ItensCarrinho item : carrinho) {

            if (item.getIdProduto() == id_produto) {
                if(qtde_produto == 0){
                    carrinho.remove(item);
                }else{
                    item.setQtde(qtde_produto);
                }
                
            }

        } 
        }catch(Exception e ){
           
            return new ModelAndView("redirect:/carrinho");

        }
        
        
        
        
        
        
        return new ModelAndView("redirect:/carrinho");
    }
    
}
