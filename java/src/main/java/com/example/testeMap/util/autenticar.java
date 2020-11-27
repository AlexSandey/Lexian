/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.util;

import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.services.usuarioService;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AlexSandey
 */
public class autenticar {

    private usuarioService usuarioService;

    public autenticar(usuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public static boolean porSessao(HttpSession session, String perfil) {

        try {
            Usuario usuarioSessao = (Usuario) session.getAttribute("usuario");

            String perfilUsuario = usuarioSessao.getPerfil();

            if (perfilUsuario.equals(perfil)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }
    
}
