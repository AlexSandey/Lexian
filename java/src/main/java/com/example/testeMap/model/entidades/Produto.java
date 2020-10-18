/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.model.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private int idProduto;
    
    @NotBlank(message = "Teste")
    @Column(name = "nome")
    private String nomeProduto;
    
    @Column(name = "qtde")
    private int qtde;
    
    @NotBlank
    @Column(name = "marca")
    private String marca;
    
    @NotBlank
    @Column(name = "categoria")
    private String categoria;
        
    @NotBlank
    @Column(name = "imagem1")
    private String imagem1;
            
    @NotBlank
    @Column(name = "imagem2")
    private String imagem2;
            
    @NotBlank
    @Column(name = "imagem3")
    private String imagem3;
            
    @NotBlank
    @Column(name = "imagem4")
    private String imagem4;
            
    @NotBlank
    @Column(name = "imagem5")
    private String imagem5;
    
    @NotBlank
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "valor")
    @Positive
    private float valor;
    
    @Column(name = "ativo")
    private boolean ativo;

    public Produto() {
    }
    
    public Produto(String nomeProduto, int qtde, String marca, String imagem1, String imagem2,String imagem3,String imagem4,String imagem5,String categoria, String descricao, float valor, boolean ativo) {
        super();
        
        this.nomeProduto = nomeProduto;
        this.qtde = qtde;
        this.marca = marca;
        this.categoria = categoria;
        this.descricao = descricao;
        this.imagem1 = imagem1;
        this.imagem2 = imagem2;
        this.imagem3 = imagem3;
        this.imagem4 = imagem4;
        this.imagem5 = imagem5;
        this.valor = valor;
        this.ativo = ativo;

    }
    public Produto(int idProduto, String nomeProduto, int qtde, String marca, String categoria, String imagem1, String imagem2, String imagem3, String imagem4, String imagem5, String descricao, float valor, boolean ativo) {
        super();
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.qtde = qtde;
        this.marca = marca;
        this.categoria = categoria;
        this.descricao = descricao;
        this.imagem1 = imagem1;
        this.imagem2 = imagem2;
        this.imagem3 = imagem3;
        this.imagem4 = imagem4;
        this.imagem5 = imagem5;
        this.valor = valor;
        this.ativo = ativo;

    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getImagem1() {
        return imagem1;
    }

    public void setImagem1(String imagem1) {
        this.imagem1 = imagem1;
    }
    
    public String getImagem2() {
        return imagem2;
    }

    public void setImagem2(String imagem2) {
        this.imagem2 = imagem2;
    }
        
    public String getImagem3() {
        return imagem3;
    }

    public void setImagem3(String imagem3) {
        this.imagem3 = imagem3;
    }
        
    public String getImagem4() {
        return imagem4;
    }

    public void setImagem4(String imagem4) {
        this.imagem4 = imagem4;
    }
        
    public String getImagem5() {
        return imagem5;
    }

    public void setImagem5(String imagem5) {
        this.imagem5 = imagem5;
    }
    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
