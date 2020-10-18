/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Produto;
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


@RestController
@RequestMapping("/produto")
public class produtoController {

    private static String UPLOADED_FOLDER = "c://imagens//";
    
    private produtoService produtoService;

    public produtoController(produtoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/cadastrar")
    public ModelAndView CadastrarProduto( 
        @RequestParam(name = "nome_produto", required = true) String nomeProduto,
        @RequestParam(name = "qtde_produto", required = true) int qtde,
        @RequestParam(name = "marca_produto", required = true) String marca,
        @RequestParam(name = "categoria_produto", required = true) String categoria,
        @RequestParam(name = "descricao_produto", required = true) String descricao,
        @RequestParam(name = "valor_produto", required = true) float valor,
        @RequestParam(name = "cadastro_ativo", required = true) boolean ativo,
        @RequestParam(required = true) MultipartFile imagem1,
        @RequestParam(required = true) MultipartFile imagem2,
        @RequestParam(required = true) MultipartFile imagem3,
        @RequestParam(required = true) MultipartFile imagem4,
        @RequestParam(required = true) MultipartFile imagem5
    ){

       try{
       
        byte[] bytes1 = imagem1.getBytes();
        byte[] bytes2 = imagem2.getBytes();
        byte[] bytes3 = imagem3.getBytes();
        byte[] bytes4 = imagem4.getBytes();
        byte[] bytes5 = imagem5.getBytes();
        
        String nameImagem1 = imagem1.getOriginalFilename();
        String nameImagem2 = imagem2.getOriginalFilename();
        String nameImagem3 = imagem3.getOriginalFilename();
        String nameImagem4 = imagem4.getOriginalFilename();
        String nameImagem5 = imagem5.getOriginalFilename();
    

        String folderIMG_DB = "IMAGESARCHIVE\\";
        
        String folderIMG = "src\\main\\resources\\static\\IMAGESARCHIVE\\";

        Path path1 = Paths.get(folderIMG + nameImagem1);
        Path path2 = Paths.get(folderIMG + nameImagem2);
        Path path3 = Paths.get(folderIMG + nameImagem3);
        Path path4 = Paths.get(folderIMG + nameImagem4);
        Path path5 = Paths.get(folderIMG + nameImagem5);

        
        Files.write(path1, bytes1);
        Files.write(path2, bytes2);
        Files.write(path3, bytes3);
        Files.write(path4, bytes4);
        Files.write(path5, bytes5);
        
        Produto produto =  new Produto();
        produto.setNomeProduto(nomeProduto);
        produto.setQtde(qtde);   
        produto.setMarca(marca);
        produto.setCategoria(categoria);
        produto.setDescricao(descricao);
        produto.setValor(valor);
        produto.setAtivo(ativo);
        produto.setImagem1("http://localhost:8080/" + folderIMG_DB + nameImagem1);
        produto.setImagem2("http://localhost:8080/" + folderIMG_DB + nameImagem2);
        produto.setImagem3("http://localhost:8080/" + folderIMG_DB + nameImagem3);
        produto.setImagem4("http://localhost:8080/" + folderIMG_DB + nameImagem4);
        produto.setImagem5("http://localhost:8080/" + folderIMG_DB + nameImagem5);
        
        Produto produtoCadastrado =  produtoService.CadastrarProduto(produto);
       
        produtoService.CadastrarProduto(produto);
        
        }catch (IOException e) {
        
        } 
       
       
       
       return new ModelAndView("produto/cadastrarProduto"); 
       
    }
    
    
    
    @RequestMapping(method = RequestMethod.GET, path = "/cadastrar")
    public ModelAndView CadastrarProdutoView( ) {
       return new ModelAndView("produto/cadastrarProduto");
    }
    
    
    
    
    
    /*@RequestMapping(method = RequestMethod.POST, path = "/atualizar/{id}")
    public ModelAndView AcessarCadastro(@PathVariable("id") int id) {
       List<Produto> produto = (List<Produto>) produtoService.filtroID(id);

       return new ModelAndView("produto/cadastrarProduto").addObject("objeto", produto);
    }*/
    
    
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
        
        return new ModelAndView("produto/visualizarProduto").addObject("produtos", produto);

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
        @RequestParam MultipartFile imagem1,
        @RequestParam MultipartFile imagem2,
        @RequestParam MultipartFile imagem3,
        @RequestParam MultipartFile imagem4,
        @RequestParam MultipartFile imagem5
    ){

       try{
       
        byte[] bytes1 = imagem1.getBytes();
        byte[] bytes2 = imagem2.getBytes();
        byte[] bytes3 = imagem3.getBytes();
        byte[] bytes4 = imagem4.getBytes();
        byte[] bytes5 = imagem5.getBytes();
        
        String nameImagem1 = imagem1.getOriginalFilename();
        String nameImagem2 = imagem2.getOriginalFilename();
        String nameImagem3 = imagem3.getOriginalFilename();
        String nameImagem4 = imagem4.getOriginalFilename();
        String nameImagem5 = imagem5.getOriginalFilename();
    

        String folderIMG_DB = "IMAGESARCHIVE\\";
        
        String folderIMG = "src\\main\\resources\\static\\IMAGESARCHIVE\\";

        Path path1 = Paths.get(folderIMG + nameImagem1);
        Path path2 = Paths.get(folderIMG + nameImagem2);
        Path path3 = Paths.get(folderIMG + nameImagem3);
        Path path4 = Paths.get(folderIMG + nameImagem4);
        Path path5 = Paths.get(folderIMG + nameImagem5);

        
        Files.write(path1, bytes1);
        Files.write(path2, bytes2);
        Files.write(path3, bytes3);
        Files.write(path4, bytes4);
        Files.write(path5, bytes5);
        
        Produto produto =  new Produto();
        produto.setIdProduto(id);
        produto.setNomeProduto(nomeProduto);
        produto.setQtde(qtde);   
        produto.setMarca(marca);
        produto.setCategoria(categoria);
        produto.setDescricao(descricao);
        produto.setValor(valor);
        produto.setAtivo(ativo);
        produto.setImagem1("http://localhost:8080/" + folderIMG_DB + nameImagem1);
        produto.setImagem2("http://localhost:8080/" + folderIMG_DB + nameImagem2);
        produto.setImagem3("http://localhost:8080/" + folderIMG_DB + nameImagem3);
        produto.setImagem4("http://localhost:8080/" + folderIMG_DB + nameImagem4);
        produto.setImagem5("http://localhost:8080/" + folderIMG_DB + nameImagem5);
        
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
