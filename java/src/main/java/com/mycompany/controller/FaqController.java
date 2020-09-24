/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.DAO.FaqDAO;
import com.mycompany.model.FaqModel;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Devakian
 */
public class FaqController {
      public String cadastrar(FaqModel faq) throws SQLException{
        return FaqDAO.cadastrarFaq(faq);
    }
    
    public String editar(FaqModel faq){
        return FaqDAO.editarFaq(faq);
    }
    
    public static List<FaqModel> listar(){
        return FaqDAO.listarFaq();
    }
    
    public String deletar(int id){
        return FaqDAO.deletarFaq(id);
    }
}
