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

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagem")
    private int idImagem;

    @Column(name = "caminho")
    private String caminho;

    @Column(name = "id_produto")
    private int idProduto;


    public Imagem(){
    }

    public Imagem(int idImagem, String caminho, int idProduto) {
        this.idImagem = idImagem;
        this.caminho = caminho;
        this.idProduto = idProduto;
    }
    public Imagem(String caminho, int idProduto) {
        this.caminho = caminho;
        this.idProduto = idProduto;
    }

    public int getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(int idImagem) {
        this.idImagem = idImagem;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }


}
