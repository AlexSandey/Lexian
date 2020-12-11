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
@RequestMapping("/produto")
public class produtoController {

    private static String UPLOADED_FOLDER = "c://imagens//";
    
    private produtoService produtoService;
    private imagemService imagemService;
    
    public produtoController(produtoService produtoService, imagemService imagemService) {
        this.produtoService = produtoService;
        this.imagemService = imagemService;
    }


    
    
    
    @RequestMapping(method = RequestMethod.GET, path = "/cadastrar")
    public ModelAndView CadastrarProdutoView( ) {
       return new ModelAndView("produto/cadastrarProduto");
    }
    
    
    @RequestMapping(value = "/atualizar/{id}", method = RequestMethod.GET)
    public ModelAndView findById(@PathVariable int id) {
        ResponseEntity<Produto> produtoResponse =  produtoService.filtroID(id);
        
        Produto produto = produtoResponse.getBody();
        
        return new ModelAndView("produto/atualizarProduto").addObject("produtos", produto);

    }

        @RequestMapping(value = "/visualizar/{id}", method = RequestMethod.GET)
    public ModelAndView findByIdVisualizar(@PathVariable int id) {
        ResponseEntity<Produto> produtoResponse =  produtoService.filtroID(id);
        Produto produto = produtoResponse.getBody();
        
        List<Imagem> imagens = imagemService.imagemProdutosPorID(id);

        List itemProduto = new ArrayList();
        
        itemProduto.add(produto);
        itemProduto.add(imagens);

        return new ModelAndView("produtoDetalhes").addObject("itemProduto", itemProduto);

    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/lista")
    public ModelAndView carregarProdutos() {
        List<Produto> produto = produtoService.carregarProdutos();
        
        return new ModelAndView("produto/listarProduto").addObject("produtos", produto); 
    }

    
    @PostMapping("/atualizar/{id}")
    public String AtualizarProduto( 
        @PathVariable int id,
        @RequestParam(name = "nome_produto") String nomeProduto,
        @RequestParam(name = "qtde_produto") int qtde,
        @RequestParam(name = "marca_produto") String marca,
        @RequestParam(name = "categoria_produto") String categoria,
        @RequestParam(name = "descricao_produto") String descricao,
        @RequestParam(name = "valor_produto") float valor,
        @RequestParam(name = "cadastro_ativo") boolean ativo,
        @RequestParam("imagem") MultipartFile[] imagens
    ){

       try{
       
        for(MultipartFile uploadedFile : imagens) {
                int idProduto = id;
                String folderIMG_DB = "IMAGESARCHIVE\\";
                String folderIMG = "src\\main\\resources\\static\\IMAGESARCHIVE\\"+idProduto+"\\";
                
                byte[] bytes = uploadedFile.getBytes();
                
                String nameImagem = uploadedFile.getOriginalFilename();
                Path path = Paths.get(folderIMG + nameImagem);
                
                File file = new File(folderIMG);
                
                file.delete();
                
                file.mkdir();
                
                Files.write(path, bytes);
                
                Imagem imagem =  new Imagem();  
                
                imagem.setCaminho("http://localhost:8080/" + folderIMG_DB + "\\" + idProduto + "\\" + nameImagem);
                imagem.setIdProduto(idProduto);
                imagemService.cadastroImagem(imagem);
                
         }   
           
           
           
           
        
        Produto produto =  new Produto();
        produto.setIdProduto(id);
        produto.setNomeProduto(nomeProduto);
        produto.setQtde(qtde);   
        produto.setMarca(marca);
        produto.setCategoria(categoria);
        produto.setDescricao(descricao);
        produto.setValor(valor);
        produto.setAtivo(ativo);

        
        ResponseEntity produtoAtualizar =  produtoService.updateProd(id,produto);
       
        }catch (IOException e) {
        
        } 
       
       return "<script>window.location.href = 'http://localhost:8080/produto/lista'</script>";

    }
    
    @PostMapping("/desativar/{id}")
    public String DesativarProduto( 
        @PathVariable int id
    ){

        ResponseEntity<Produto> produtoResponse =  produtoService.filtroID(id);
        
        Produto produto = produtoResponse.getBody();
        
        produto.setAtivo(false);

        
        
        ResponseEntity produtoAtualizar =  produtoService.updateProd(id,produto);
       
       
       
       return "<script>window.location.href = 'http://localhost:8080/produto/lista'</script>";

    }
    
    
    @RequestMapping(value = "/filtroNome/{nome}", method = RequestMethod.GET)
    public List<Produto> findByNome(@PathVariable String nome) {
        return produtoService.filtroNome(nome);
    }
    
     @RequestMapping(value = "/filtroTipo/{categoria}", method = RequestMethod.GET)
    public List<Produto> findByCategoria(@PathVariable String categoria) {
        return produtoService.filtroCategoria(categoria);
    }

}
