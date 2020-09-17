/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.DAO.ProdutoDAO;
import com.mycompany.model.ProdutoModel;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Devakian
 */
public class ProdutoController {
    
    public String cadastrar(ProdutoModel prod) throws SQLException{
        return ProdutoDAO.cadastrarProduto(prod);
    }
    
    public String atualizar(ProdutoModel prod){
        return ProdutoDAO.atualizarProduto(prod);
    }
    
    public static List<ProdutoModel> listar(/* inserir pagina e quantidade, tratar na DAO o retorno*/){
        return ProdutoDAO.listarProdutos();
    }
    
    public String deletar(int id){
        return ProdutoDAO.DeletarProduto(id);
    }
    
    public String atualizarQtde(int id ,int qtde){
        return ProdutoDAO.atualizarQtde(id,qtde);
        
    }
    
    public String statusProd(int id, int ativo){
        return ProdutoDAO.statusProd(id,ativo);
    }

}
