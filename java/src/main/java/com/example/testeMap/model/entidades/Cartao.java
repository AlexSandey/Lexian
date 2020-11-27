/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.model.entidades;

/**
 *
 * @author Programming
 */
public class Cartao {
 
    private String nomeCartao;
    private String numeroCartao;
    private int parcelas;

    public Cartao(String nomeCartao, String numeroCartao,int parcelas) {
        this.nomeCartao = nomeCartao;
        this.numeroCartao = numeroCartao;
        this.parcelas = parcelas;
    }

    public Cartao() {
    }

    
    
    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }
    
    
}
