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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;


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
        List<ProdutoModel> produtos = ProdutoController.listar();
               
        Gson gson = new Gson();
        
        String produtosJson = gson.toJson(produtos);

        out.print(produtosJson);
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
        PrintWriter out = resp.getWriter();
        

        String nomeProduto = req.getParameter("nomeProduto");
        int qtde = Integer.parseInt(req.getParameter("qtde"));
        String marca = req.getParameter("marca");
        String categoria = req.getParameter("categoria");      
        String descricao = req.getParameter("descricao");
        float valor = Float.parseFloat(req.getParameter("valor"));
        String faq = req.getParameter("faq");
        String ativoString = req.getParameter("ativo");
                
        boolean ativo = "".equals(ativoString);
        
        
        ProdutoModel produtoModel = new ProdutoModel();
        ProdutoController produtoController = new ProdutoController();

        produtoModel.setNomeProduto(nomeProduto);
        produtoModel.setQtde(qtde);
        produtoModel.setMarca(marca);
        produtoModel.setCategoria(categoria);
        produtoModel.setDescricao(descricao);
        produtoModel.setValor(valor);
        produtoModel.setFaq(faq);
        produtoModel.setAtivo(ativo);

        String RespController = null;
        try {
            RespController = produtoController.cadastrar(produtoModel);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.print(RespController);
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
        PrintWriter out = resp.getWriter();

        int id = Integer.parseInt(req.getParameter("id"));


        ProdutoController produtoController = new ProdutoController();

        

        String RespController = null;
        RespController = produtoController.deletar(id);
        out.print(RespController);
        
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPut(req, resp);
        
        processRequest(req, resp);
        PrintWriter out = resp.getWriter();

        int id = Integer.parseInt(req.getParameter("id"));
        String nomeProduto = req.getParameter("nomeProduto");
        int qtde = Integer.parseInt(req.getParameter("qtde"));
        String marca = req.getParameter("marca");
        String categoria = req.getParameter("categoria");      
        String descricao = req.getParameter("descricao");
        float valor = Float.parseFloat(req.getParameter("valor"));
        String faq = req.getParameter("faq");

        ProdutoController produtoController = new ProdutoController();
        ProdutoModel produtoModel = new ProdutoModel();
        
        produtoModel.setIdProduto(id);
        produtoModel.setNomeProduto(nomeProduto);
        produtoModel.setQtde(qtde);
        produtoModel.setMarca(marca);
        produtoModel.setCategoria(categoria);
        produtoModel.setDescricao(descricao);
        produtoModel.setValor(valor);
        produtoModel.setFaq(faq);
        
        String RespController = null;
        if(req.getParameter("attQtde").equals("attQtde")){
        RespController = produtoController.atualizarQtde(id, qtde);
        }
        if(req.getParameter("attProd").equals("attProd")){
        RespController = produtoController.atualizar(produtoModel);
        }
        out.print(RespController);
        

    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}