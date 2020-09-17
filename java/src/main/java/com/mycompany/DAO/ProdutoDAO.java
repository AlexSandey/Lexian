/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.DAO;

import com.mycompany.db.ConnectionToDb;
import com.mycompany.model.ProdutoModel;
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
public class ProdutoDAO {

    public static String cadastrarProduto(ProdutoModel prod) throws SQLException {
        String ok = "DAO ok!";
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            String sql = "insert into tb_produto (nome, qtde, marca, categoria, descricao, valor, faq, ativo)"
                    + " values (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prod.getNomeProduto());
            ps.setInt(2, prod.getQtde());
            ps.setString(3, prod.getMarca());
            ps.setString(4, prod.getCategoria());
            ps.setString(5, prod.getDescricao());
            ps.setFloat(6, prod.getValor());
            ps.setString(7, prod.getFaq());
            ps.setBoolean(8, prod.isAtivo());

            boolean resultado = ps.execute();
            ok = "{'resultado' : 'sucesso', 'mensagem' : 'Insercao no banco realizado. Erro: " + prod.isAtivo() + "' }";

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ok = "{'resultado' : 'erro', 'mensagem' : 'erro em DAO de Cadastro do Produto'" + ex + " }";
            return ok;
        }

        return ok;
    }

    public static String atualizarProduto(ProdutoModel prod) {
        String ok = "DAO ok";
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            String sql = "UPDATE tb_produto SET nome = ?, qtde = ?, marca = ?, categoria = ?, "
                    + "descricao = ?, valor = ?, faq = ? where id_produto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, prod.getNomeProduto());
            ps.setInt(2, prod.getQtde());
            ps.setString(3, prod.getMarca());
            ps.setString(4, prod.getCategoria());
            ps.setString(5, prod.getDescricao());
            ps.setFloat(6, prod.getValor());
            ps.setString(7, prod.getFaq());
            ps.setInt(8, prod.getIdProduto());

            int resultado = ps.executeUpdate();//Tratar posteriormente         
            ok = "{'resultado' : 'sucesso', 'mensagem' : 'Insercao no banco realizado. Erro: " + resultado + "' }";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ok = "{'resultado' : 'erro', 'mensagem' : 'erro em DAO de Cadastro do Produto:"+ex+"' }";
            return ok;
        }

        return ok;
    }

    public static String DeletarProduto(int id) {
        String ok = "DAO ok";
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            PreparedStatement ps = con.prepareStatement("DELETE from tb_produto WHERE id_produto = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, id);
            boolean resultado = ps.execute();
            ok = "{'resultado' : 'sucesso', 'mensagem' : 'Insercao no banco realizado. Erro: " + resultado + "' }";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ok = "{'resultado' : 'erro', 'mensagem' : 'erro em DAO de excluir Produto: "+ex+"' }";
            return ok;
        }

        return ok;
    }

    public static String atualizarQtde(int id, int qtde) {
        String ok = "DAO ok";
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            PreparedStatement ps = con.prepareStatement("UPDATE tb_produto SET qtde = ? WHERE id_produto=?");
            ps.setInt(1, qtde);
            ps.setInt(2, id);
            boolean resultado = ps.execute();
            ok = "{'resultado' : 'sucesso', 'mensagem' : 'Insercao no banco realizado. Erro: " + resultado + "' }";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ok = "{'resultado' : 'erro', 'mensagem' : 'erro em DAO de Atualizar Qtde Produto:"+ex+"' }";
            return ok;
        }

        return ok;
    }

<<<<<<< HEAD
    public static List<ProdutoModel> listarProdutos(/* inserir pagina e quantidade, tratar AQUI o retorno*/) {
        String ok = "DAO ok";
        
        List<ProdutoModel> produtos = new ArrayList<>();
       
        Connection con;

        try {
            String sqlState = "SELECT * FROM tb_produto ORDER BY ativo DESC";

            con = ConnectionToDb.obterConexao();

            PreparedStatement ps = con.prepareStatement(sqlState,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProdutoModel produto = new ProdutoModel();
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.setNomeProduto(rs.getString("nome"));
                produto.setQtde(rs.getInt("qtde"));
                produto.setMarca(rs.getString("marca"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setValor(rs.getFloat("valor"));
                produto.setFaq(rs.getString("faq"));
                produto.setAtivo(rs.getBoolean("ativo"));

                produtos.add(produto);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return produtos;
    }
=======
    public static String statusProd(int id,int ativo) {
                String ok = "DAO ok";
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            PreparedStatement ps = con.prepareStatement("UPDATE tb_produto set ativo = ? WHERE id_produto = ?");
            ps.setInt(0, ativo);
            ps.setInt(1, id);
            boolean resultado = ps.execute();
            ok = "{'resultado' : 'sucesso', 'mensagem' : 'Alteração no banco realizada. Erro: " + resultado + "' }";
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            ok = "{'resultado' : 'erro', 'mensagem' : 'erro em DAO de excluir Produto: "+ex+"' }";
            return ok;
        }

        return ok;
        
    }
        
>>>>>>> fff27e0e624e5696cb880d2a92eb7b7a0e94aadb

}
