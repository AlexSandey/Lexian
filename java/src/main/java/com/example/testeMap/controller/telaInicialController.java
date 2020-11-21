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
    public ModelAndView AtualizarProduto( 
    ){

        List<Produto> produto = produtoService.carregarProdutos();
        List<ItensCarrinho> itemProdutos = new ArrayList();
        for(Produto itemProduto : produto){
            String caminhoImagem = imagemService.caminhoImagemMin(itemProduto.getIdProduto());
            ItensCarrinho item = new ItensCarrinho(itemProduto.getIdProduto(), itemProduto.getNomeProduto(), caminhoImagem, itemProduto.getValor());
            itemProdutos.add(item);
        }
           
        return new ModelAndView("index").addObject("produtos", itemProdutos); 
    }

}
