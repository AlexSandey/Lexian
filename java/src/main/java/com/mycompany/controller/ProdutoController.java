/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.DAO.ProdutoDAO;
import com.mycompany.model.ModelProduto;

/**
 *
 * @author Devakian
 */
public class ProdutoController {
    public boolean atualizar(ModelProduto prod){
        return ProdutoDAO.atualizarProduto(prod);
    }
    public boolean save(ModelProduto prod){
        return ProdutoDAO.CadastrarProduto(prod);
    }
}
