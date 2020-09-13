/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.model;

import java.util.Date;

/**
 *
 * @author Devakian
 */
public class ModelUsuario {
    private int idUsuario;
    private int id_Perfil;// Chave estrangeira... OBS:Checar se será utilizada
    private int idEndereco;// Chave estrangeira
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Date dataNsc;
    private String senha;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getId_Perfil() {
        return id_Perfil;
    }

    public void setId_Perfil(int id_Perfil) {
        this.id_Perfil = id_Perfil;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNsc() {
        return dataNsc;
    }

    public void setDataNsc(Date dataNsc) {
        this.dataNsc = dataNsc;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
