/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DAO;

import com.mycompany.db.ConnectionToDb;
import com.mycompany.model.FaqModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Devakian
 */
public class FaqDAO {
    public static String cadastrarFaq(FaqModel faq) throws SQLException {
        String ok = "DAO ok!";
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            String sql = "insert into tb_faq (pergunta, resposta, idProduto)"
                    + " values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, faq.getPergunta());
            ps.setString(2, faq.getResposta());
            ps.setInt(3, faq.getIdProduto());

            boolean resultado = ps.execute();
            ok = "{'resultado' : 'sucesso', 'mensagem' : 'Insercao no banco realizado. Erro: " + resultado+ "' }";

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ok = "{'resultado' : 'erro', 'mensagem' : 'erro em DAO de Cadastro do Produto'" + ex + " }";
            return ok;
        }

        return ok;
    }
    
     public static String deletarFaq(int idFaq) {
        String ok = "DAO ok";
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            PreparedStatement ps = con.prepareStatement("DELETE from tb_faq WHERE id_faq = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, idFaq);
            boolean resultado = ps.execute();
            ok = "{'resultado' : 'sucesso', 'mensagem' : 'Insercao no banco realizado. Erro: " + resultado + "' }";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ok = "{'resultado' : 'erro', 'mensagem' : 'erro em DAO de excluir Produto: "+ex+"' }";
            return ok;
        }

        return ok;
    }
     
     public static String editarFaq(FaqModel faq) {
        String ok = "DAO ok";
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            String sql = "UPDATE tb_faq SET pergunta = ?, resposta = ?, id_produto = ?,"
                    + "where id_faq = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, faq.getPergunta());
            ps.setString(2, faq.getResposta());
            ps.setInt(3, faq.getIdProduto());
            ps.setInt(4,faq.getIdFaq());

            int resultado = ps.executeUpdate();//Tratar posteriormente         
            ok = "{'resultado' : 'sucesso', 'mensagem' : 'Insercao no banco realizado. Erro: " + resultado + "' }";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ok = "{'resultado' : 'erro', 'mensagem' : 'erro em DAO de Cadastro do Produto:"+ex+"' }";
            return ok;
        }

        return ok;
    }

    public static List<FaqModel> listarFaq() {
        String ok = "DAO ok";
        
        List<FaqModel> faqs = new ArrayList<>();
       
        Connection con;
             
        
        try {
            String sqlState = "SELECT * FROM tb_faq ORDER BY ativo DESC";

            con = ConnectionToDb.obterConexao();

            PreparedStatement ps = con.prepareStatement(sqlState,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FaqModel faq = new FaqModel();
                faq.setIdFaq(rs.getInt("id_faq"));
                faq.setPergunta(rs.getString("pergunta"));
                faq.setResposta(rs.getString("resposta"));
                faq.setIdProduto(rs.getInt("id_produto"));
                
                faqs.add(faq);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return faqs;
    }
}
