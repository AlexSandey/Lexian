/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DAO;

import com.mycompany.db.ConnectionToDb;
import com.mycompany.model.ModelProduto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Devakian
 */
public class ProdutoDAO {

    public static boolean CadastrarProduto(ModelProduto prod) {
        boolean ok = false;
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            String sql = "insert into tb_produto (id_usuario, id_filial, tipo, nome, qtde, descricao, valor)"
                    + " values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prod.getNomeProduto());
            ps.setString(2, prod.getMarca());
            ps.setString(3, prod.getCategoria());
            ps.setFloat(4, prod.getValor());
            ps.setString(5, prod.getFaq());
            ps.setInt(6, prod.getQtde());
            ps.execute();
            ok = true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ok;
    }

    public static boolean atualizarProduto(ModelProduto prod) {
        boolean ok = false;
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            String sql = "update tb_produto set id_usuario = ?, qtde = ?, marca = ?, categoria = ?, "
                    + "descricao = ?, valor = ?, faq = ? where id_produto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prod.getNomeProduto());
            ps.setString(2, prod.getMarca());
            ps.setString(3, prod.getCategoria());
            ps.setFloat(4, prod.getValor());
            ps.setString(5, prod.getFaq());
            ps.setInt(6, prod.getQtde());
            ps.setInt(7, prod.getIdProduto());
            ps.executeUpdate();
            ok = true;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ok;
    }

}
