package com.mycompany.lexian;

import com.mycompany.Utils.*;
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
import com.mycompany.model.FaqModel;
import java.util.Enumeration;

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
        String ativo = req.getParameter("ativo");
        String imagem1m = req.getParameter("imagem1m");

        
        ProdutoModel produtoModel = new ProdutoModel();
        ProdutoController produtoController = new ProdutoController();
        verificaStatus verifica = new verificaStatus();

        produtoModel.setNomeProduto(nomeProduto);
        produtoModel.setQtde(qtde);
        produtoModel.setMarca(marca);
        produtoModel.setCategoria(categoria);
        produtoModel.setDescricao(descricao);
        produtoModel.setValor(valor);
        produtoModel.setAtivo(verifica.verifica(ativo));
        
        

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
        int ativo = Integer.parseInt(req.getParameter("Ativo"));

        ProdutoController produtoController = new ProdutoController();

        String RespController = null;
        if (ativo == 3) {
            RespController = produtoController.deletar(id);
        }
        if (ativo == 1 || ativo == 0) {
            RespController = produtoController.statusProd(id, ativo);
        }

        RespController = produtoController.deletar(id);
        out.print(RespController);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);

        processRequest(req, resp);
        PrintWriter out = resp.getWriter();

        String RespController = null;
        String solicitacao = req.getParameter("solicitacao");

        if (solicitacao.equals("attQtde")) {
            int id = Integer.parseInt(req.getParameter("idProduto"));
            int quantidade = Integer.parseInt(req.getParameter("qtde"));

            ProdutoController produtoController = new ProdutoController();
            RespController = produtoController.atualizarQtde(id, quantidade);
        }
        if (solicitacao.equals("attProduto")) {
            ProdutoModel produtoModel = new ProdutoModel();

            int id = Integer.parseInt(req.getParameter("id"));
            String nomeProduto = req.getParameter("nomeProduto");
            int qtde = Integer.parseInt(req.getParameter("qtde"));
            String marca = req.getParameter("marca");
            String categoria = req.getParameter("categoria");
            String descricao = req.getParameter("descricao");
            float valor = Float.parseFloat(req.getParameter("valor"));
            String faq = req.getParameter("faq");

            produtoModel.setIdProduto(id);
            produtoModel.setNomeProduto(nomeProduto);
            produtoModel.setQtde(qtde);
            produtoModel.setMarca(marca);
            produtoModel.setCategoria(categoria);
            produtoModel.setDescricao(descricao);
            produtoModel.setValor(valor);

            ProdutoController produtoController = new ProdutoController();
            RespController = produtoController.atualizar(produtoModel);

        }
        out.print(RespController);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
