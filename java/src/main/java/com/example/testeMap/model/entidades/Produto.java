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
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "valor")
    @Positive
    private float valor;
    
    @Column(name = "ativo")
    private boolean ativo;

    //private String fotoPrincipal;
    
    public Produto() {
    }
    
    public Produto(String nomeProduto, int qtde, String marca, String categoria, String descricao, float valor, boolean ativo) {
        super();
        
        this.nomeProduto = nomeProduto;
        this.qtde = qtde;
        this.marca = marca;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
        this.ativo = ativo;

    }
    /*public Produto(String fotoPrincipal, Produto produto) {
        super();
        
        this.fotoPrincipal = fotoPrincipal;
        this.nomeProduto = produto.getNomeProduto();
        this.qtde = produto.getQtde();
        this.marca = produto.getMarca();
        this.categoria = produto.getCategoria();
        this.descricao = produto.getDescricao();
        this.valor = produto.getValor();
        this.ativo = produto.isAtivo();

    }*/
    public Produto(int idProduto, String nomeProduto, int qtde, String marca, String categoria, String descricao, float valor, boolean ativo) {
        super();
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.qtde = qtde;
        this.marca = marca;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
        this.ativo = ativo;

    }

    public Produto(int idProduto, String nomeProduto, int qtde, float valor) {
        super();
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.qtde = qtde;
        this.valor = valor;
    }
    
    public int getIdProduto() {
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

    /*public String fotoPrincipal() {
        return fotoPrincipal;
    }

    public void fotoPrincipal(String fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }*/
    
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
