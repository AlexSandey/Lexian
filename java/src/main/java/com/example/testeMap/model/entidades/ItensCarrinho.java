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


public class ItensCarrinho {

    private int idProduto;

    private String nomeProduto;
    

    private int qtde;

    private String imagem;

    private float valor;

    public ItensCarrinho() {
    }
    
    public ItensCarrinho(int idProduto, String nomeProduto, int qtde, String imagem, float valor) {
        super();
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.qtde = qtde;
        this.imagem = imagem;
        this.valor = valor;
    }
    public ItensCarrinho(int idProduto, String nomeProduto, String imagem, float valor) {
        super();
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.imagem = imagem;
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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
    
}
