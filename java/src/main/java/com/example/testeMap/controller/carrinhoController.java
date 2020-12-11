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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        try {

            List<ItensCarrinho> carrinho;

            carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");

            if (carrinho.size() == 0 || carrinho == null) {
                return new ModelAndView("carrinhoVazio");
            }
            return new ModelAndView("carrinho").addObject(carrinho);

        } catch (Exception e) {

            return new ModelAndView("carrinhoVazio");

        }

    }

    @GetMapping("/adicionar")
    public ModelAndView adicionarCarrinho(
            @RequestParam(name = "adicionarCarrinho", required = false) int adicionarCarrinho,
            HttpSession session
    ) {

        List<ItensCarrinho> carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");
        float total = 0;

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

        for (ItensCarrinho itensCarrinho : carrinho) {
            total = total + (itensCarrinho.getValor() * itensCarrinho.getQtde());
        }

        session.setAttribute("totalCarrinho", total);
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
        float totalCarrinho = 0;
        session.removeAttribute("totalCarrinho");
        try {
            for (ItensCarrinho item : carrinho) {

                if (item.getIdProduto() == id_produto) {
                    if (qtde_produto == 0) {
                        carrinho.remove(item);
                        break;
                    } else {
                        item.setQtde(qtde_produto);
                    }

                }

            }

            for (ItensCarrinho itensCarrinho : carrinho) {
                totalCarrinho = totalCarrinho + (itensCarrinho.getValor() * itensCarrinho.getQtde());
            }

            session.setAttribute("totalCarrinho", totalCarrinho);

        } catch (Exception e) {
            return new ModelAndView("redirect:/carrinho");
        }

        return new ModelAndView("redirect:/carrinho");
    }

    @GetMapping("/dadosdecompra")
    public ModelAndView dadosDeCompra(
            HttpSession session,
            RedirectAttributes redirAttr
    ) {

        List<ItensCarrinho> carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");

        boolean verificarLogin = session.getAttribute("usuario") != null;

        if (verificarLogin == false) {

            redirAttr.addFlashAttribute("encaminharLogin", true);

            return new ModelAndView("redirect:http://localhost:8080/cliente");
        }

        int teste = 1;

        return new ModelAndView("redirect:/carrinho");
    }

}
