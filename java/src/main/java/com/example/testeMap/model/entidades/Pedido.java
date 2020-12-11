/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.model.entidades;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author AlexSandey
 */
@Entity
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int idPedido;
    
    @Column(name = "id_usuario")
    private int idUsuario;
    
    @Column(name = "id_endereco")
    private int idEndereco;
    
    @Column(name = "valor_total")
    private float valorTotal;
    
    
    @Column(name = "metodo_pagamento")
    private String metodoPagamento;
    
    @Column(name = "nome_cartao")
    private String nomeCartao;
    
    @Column(name = "cartao_num")
    private String numCartao;
    
    @Column(name = "parcelas")
    private int parcelas;
    
    @Column(name = "boleto")
    private String boleto;
    
    @Column(name = "id_status")
    private int status;
    
    @Column(name = "data_pedido") 
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate  data;
     
     
    public Pedido(){
    }

    public Pedido(int idPedido, int idUsuario, int idEndereco, float valorTotal, String metodoPagamento, String nomeCartao, String numCartao, int parcelas, String boleto, int status,LocalDate data) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.idEndereco = idEndereco;
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.nomeCartao = nomeCartao;
        this.numCartao = numCartao;
        this.parcelas = parcelas;
        this.boleto = boleto;
        this.status = status;
        this.data = data;
    }
    
        public Pedido( int idUsuario, int idEndereco, float valorTotal, String metodoPagamento, String nomeCartao, String numCartao, int parcelas, String boleto, int status,LocalDate data) {
        
        this.idUsuario = idUsuario;
        this.idEndereco = idEndereco;
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.nomeCartao = nomeCartao;
        this.numCartao = numCartao;
        this.parcelas = parcelas;
        this.boleto = boleto;
        this.status = status;
        this.data = data;
    }



    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    
    public LocalDate getDatePedido() {
        return data;
    }

    public void setDatePedido(LocalDate datePedido) {
        this.data = datePedido;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }
    

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public String getBoleto() {
        return boleto;
    }

    public void setBoleto(String boleto) {
        this.boleto = boleto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
