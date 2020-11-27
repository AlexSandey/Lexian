/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Imagem;
import com.example.testeMap.model.entidades.ItensCarrinho;
import com.example.testeMap.model.entidades.Produto;
import com.example.testeMap.services.imagemService;
import com.example.testeMap.services.produtoService;
import java.io.File;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("")
public class telaInicialController {

    private static String UPLOADED_FOLDER = "c://imagens//";
    
    private produtoService produtoService;
    private imagemService imagemService;
    
    public telaInicialController(produtoService produtoService, imagemService imagemService) {
        this.produtoService = produtoService;
        this.imagemService = imagemService;
    }

    
    @GetMapping
    public ModelAndView telaInicial( 
    ){

        //List<Produto> produtoMaisVend = produtoService.filtro5MaisVendidosGeral();  
        List<Produto> produtoMaisVend = produtoService.carregarProdutos();     
        List<Produto> prodDisp = produtoService.filtroIndexRamdom();
        List<ItensCarrinho> prodMaisVendidos = new ArrayList();
        List<ItensCarrinho> prodInicial = new ArrayList();
        
        for(Produto itemAleatorio : prodDisp){
            String caminhoImagem = imagemService.caminhoImagemMin(itemAleatorio.getIdProduto());
            ItensCarrinho item = new ItensCarrinho(itemAleatorio.getIdProduto(), itemAleatorio.getNomeProduto(), caminhoImagem, itemAleatorio.getValor());
            prodInicial.add(item);
        }
        
        
        for(Produto itemProduto : produtoMaisVend){
            
            String caminhoImagem = imagemService.caminhoImagemMin(itemProduto.getIdProduto());
            ItensCarrinho item = new ItensCarrinho(itemProduto.getIdProduto(), itemProduto.getNomeProduto(), caminhoImagem, itemProduto.getValor());
            prodMaisVendidos.add(item);
        }

        

        List<List<ItensCarrinho>> itemsPedido = new ArrayList<>();
        itemsPedido.add(prodMaisVendidos);
        itemsPedido.add(prodInicial);
           
        
        
        
        return new ModelAndView("index").addObject("produtos", itemsPedido); 
    }
    
    @GetMapping("/categoria/{categoria}")
    public ModelAndView telaFiltrada(@PathVariable String categoria ){

        List<Produto> produtoMaisVend = produtoService.filtro5MaisVendidosCategoria(categoria);  
        //List<Produto> produtoMaisVend = produtoService.filtroCategoria(categoria);     
        List<Produto> prodDisp = produtoService.filtroCategoria(categoria);
        
        List<ItensCarrinho> prodMaisVendidos = new ArrayList();
        List<ItensCarrinho> prodInicial = new ArrayList();
        
        for(Produto itemAleatorio : prodDisp){
            String caminhoImagem = imagemService.caminhoImagemMin(itemAleatorio.getIdProduto());
            ItensCarrinho item = new ItensCarrinho(itemAleatorio.getIdProduto(), itemAleatorio.getNomeProduto(), caminhoImagem, itemAleatorio.getValor());
            prodInicial.add(item);
        }
        
        
        for(Produto itemProduto : produtoMaisVend){
            
            String caminhoImagem = imagemService.caminhoImagemMin(itemProduto.getIdProduto());
            ItensCarrinho item = new ItensCarrinho(itemProduto.getIdProduto(), itemProduto.getNomeProduto(), caminhoImagem, itemProduto.getValor());
            prodMaisVendidos.add(item);
        }

        

        List<List<ItensCarrinho>> itemsPedido = new ArrayList<>();
        itemsPedido.add(prodMaisVendidos);
        itemsPedido.add(prodInicial);
           
        
        return new ModelAndView("index").addObject("produtos", itemsPedido); 
    }

}
