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
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private int idEndereco;
    
    @Column(name = "rua")
    private String rua;
    
    @Column(name = "numero")
    private int numero;
    
    @Column(name = "bairro")
    private String bairro;
    
    @Column(name = "cep")
    private String cep;
    
    @Column(name = "complemento")
    private String complemento;
    
    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "id_usuario")
    private int idUsuario;

    
    public Endereco(){
    }

    public Endereco(int idEndereco, String rua, int numero, String bairro, String cep, String complemento, String tipo, int idUsuario) {
        this.idEndereco = idEndereco;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
        this.tipo = tipo;
        this.idUsuario = idUsuario;
    }
    
     public Endereco(String rua, int numero, String bairro, String cep, String complemento, String tipo, int idUsuario) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
        this.tipo = tipo;
        this.idUsuario = idUsuario;
    }
    


    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
     
    
    
    
}
