/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.util;

import java.util.Random;

/**
 *
 * @author Programming
 */
public class GerarBoleto {
   
    public String gerarBoleto(){
        Random gerador = new Random();
       
       
        StringBuilder strBuilder = new StringBuilder();

       
        for (int i = 0; i < 47; i++) {
            int numero = gerador.nextInt(9);
            
            strBuilder.append(Integer.toString(numero));
         }
        
        return strBuilder.toString();
    }
    
}
