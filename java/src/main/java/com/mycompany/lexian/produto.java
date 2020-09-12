package com.mycompany.lexian;

import com.mycompany.db.ConnectionToDb;
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
public class produto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        PrintWriter out = response.getWriter();

    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
        PrintWriter out = resp.getWriter();

        String testando = req.getParameter("teste");
        
        Connection con;

        try {
            con = ConnectionToDb.obterConexao();
            String sql = "insert into tb_produto (nome, qtde, marca, categoria, descricao, valor, faq)"
                    + " values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, "Testando_nome");
            ps.setInt(2, 122);
            ps.setString(3, "Testando_marca");
            ps.setString(4, "Testando_categoria");
            ps.setString(5, "Testando_descricao");
            ps.setFloat(6, (float) 12.5);
            ps.setString(7, "Testando_faq");


            ps.execute();

        } catch (SQLException | ClassNotFoundException ex){
            out.print(ex);
            Logger.getLogger(produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
