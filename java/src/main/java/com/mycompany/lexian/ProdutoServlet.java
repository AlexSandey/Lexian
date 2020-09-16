package com.mycompany.lexian;

import com.mycompany.controller.ProdutoController;
import com.mycompany.db.ConnectionToDb;
import com.mycompany.model.ProdutoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AlexSandey, Devakian
 */

@WebServlet(name = "ProdutoServlet", urlPatterns = {"/produto"})
public class ProdutoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        processRequest(req, resp);
        PrintWriter out = resp.getWriter();
        
        int idProduto = Integer.parseInt(req.getParameter("idProduto"));
        String nomeProduto = req.getParameter("nomeProduto");
        int qtde = Integer.parseInt(req.getParameter("qtde"));
        String marca = req.getParameter("marca");
        String categoria = req.getParameter("categoria");      
        String descricao = req.getParameter("descricao");
        float valor = Float.parseFloat(req.getParameter("valor"));
        String faq = req.getParameter("faq");

        
        ProdutoModel produtoModel = new ProdutoModel();
        ProdutoController produtoController = new ProdutoController();

        produtoModel.setIdProduto(idProduto);
        produtoModel.setNomeProduto(nomeProduto);
        produtoModel.setQtde(qtde);
        produtoModel.setMarca(marca);
        produtoModel.setCategoria(categoria);
        produtoModel.setDescricao(descricao);
        produtoModel.setValor(valor);
        produtoModel.setFaq(faq);
        
        String RespController = null;
        try {
            RespController = produtoController.cadastrar(produtoModel);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.print(RespController);
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
        PrintWriter out = resp.getWriter();
        out.print("okay");

        String nomeProduto = req.getParameter("nomeProduto");
        int qtde = Integer.parseInt(req.getParameter("qtde"));
        String marca = req.getParameter("marca");
        String categoria = req.getParameter("categoria");      
        String descricao = req.getParameter("descricao");
        float valor = Float.parseFloat(req.getParameter("valor"));
        String faq = req.getParameter("faq");

        
        ProdutoModel produtoModel = new ProdutoModel();
        ProdutoController produtoController = new ProdutoController();

        produtoModel.setNomeProduto(nomeProduto);
        produtoModel.setQtde(qtde);
        produtoModel.setMarca(marca);
        produtoModel.setCategoria(categoria);
        produtoModel.setDescricao(descricao);
        produtoModel.setValor(valor);
        produtoModel.setFaq(faq);
        
        String RespController = null;
        try {
            RespController = produtoController.cadastrar(produtoModel);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.print(RespController);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
