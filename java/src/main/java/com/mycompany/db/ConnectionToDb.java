/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author AlexSandey
 */
public class ConnectionToDb {
    


    //private static final String CAMINHO = "jdbc:mysql://thinkcode.coczbjefacyr.us-east-2.rds.amazonaws.com:3306/dbprojeto3?zeroDateTimeBehavior=convertToNull&useSSL=false";//Caminho de produ��o
    //private static final String CAMINHO = "jdbc:mysql://localhost:3306/bdprojeto3?zeroDateTimeBehavior=convertToNull";
    private static final String CAMINHO = "jdbc:mysql://localhost:3306/dblexian?zeroDateTimeBehavior=convertToNull";
    private static final String USER = "root";
    //private static final String SENHA = "12345678";//Senha de produção
    private static final String SENHA = "";

    //private static final String SENHA = "";
    public static Connection obterConexao()
            throws ClassNotFoundException, SQLException {
// 1) Declarar o driver JDBC de acordo com o Banco de dados usado
        Class.forName("com.mysql.jdbc.Driver");
// 2) Abrir a conex�o
        Connection conn = DriverManager.getConnection(CAMINHO, USER, SENHA); // Usu�rio de conex�o no BD"SENHA"); // Senha
        return conn;
    }
}
