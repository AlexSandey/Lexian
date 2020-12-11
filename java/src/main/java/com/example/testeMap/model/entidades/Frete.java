/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.model.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Programming
 */
public class Frete {

    private int id;
    private String tipo;
    private double valor;

    public Frete() {
    }

    public Frete(int id, String tipo, double valor) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Frete sedex() {
        Frete sedex = new Frete(1, "Sedex", 13.00);
        return sedex;
    }

    public Frete loggi() {
        Frete sedex = new Frete(2, "Loggi", 16.00);
        return sedex;
    }

    public Frete iconex() {
        Frete sedex = new Frete(3, "Iconex", 20.00);
        return sedex;
    }


    public List<Frete> fretes(){
        List<Frete> fretes = new ArrayList<>();
        Frete frete = new Frete();
        fretes.add(frete.sedex());
        fretes.add(frete.loggi());
        fretes.add(frete.iconex());
        
        return fretes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public Frete findById(int id){
        Frete frete = new Frete();
        if(id ==1){
            return frete.sedex();
        }
        if(id==2){
            return frete.loggi();
        }
        if(id==3){
            return  frete.iconex();
        }
        return null;
    }
    

}
