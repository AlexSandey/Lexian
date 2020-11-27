/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Produto;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.services.ServiceExc;
import com.example.testeMap.services.usuarioService;
import com.example.testeMap.util.autenticar;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/admin")
public class adminController {

    private usuarioService usuarioService;

    public adminController(usuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("")
    public ModelAndView loginAdminGET(HttpSession session) {

        if (autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin/cadastrar-estoquista");
        }

        return new ModelAndView("administrador/login");
    }

    @PostMapping("/login")
    public ModelAndView loginCliente(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "senha", required = true) String senha,
            HttpSession session,
            RedirectAttributes redirAttr
    ) throws ServiceExc, NoSuchAlgorithmException {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(senha.getBytes());

            BigInteger number = new BigInteger(1, messageDigest);

            String senha_hash = number.toString(16);

            Usuario usuario = usuarioService.logarUsuario(email, senha_hash);

            String verificaCliente = usuario.getPerfil();

            if (verificaCliente.equals("Administrador")) {
                session.setAttribute("usuario", usuario);

                return new ModelAndView("redirect:http://localhost:8080/admin/cadastrar-estoquista");
            }

        } catch (Exception e) {
            redirAttr.addFlashAttribute("msgErro", "erroLogin");

            return new ModelAndView("redirect:/admin");
        }

        redirAttr.addFlashAttribute("msgErro", "erroLogin");
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/cadastrar-estoquista")
    public ModelAndView cadastrarEstoquistaGET(HttpSession session) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        return new ModelAndView("administrador/cadastrarEstoquista");
    }

    @GetMapping("/logout")
    public ModelAndView logoutAdminGET(
            HttpSession session
    ) {
        session.invalidate();
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/cadastrar-estoquista")
    public ModelAndView cadastrarEstoquistaPOST(
            @RequestParam(name = "nome_usuario", required = true) String nome,
            @RequestParam(name = "email_usuario", required = true) String email,
            @RequestParam(name = "senha_usuario", required = true) String senha,
            @RequestParam(name = "cpf_usuario", required = true) String cpf,
            HttpSession session
    ) throws NoSuchAlgorithmException {
        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setEmail(email);

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(senha.getBytes());

        BigInteger number = new BigInteger(1, messageDigest);

        String senha_hash = number.toString(16);

        usuario.setSenha(senha_hash);
        usuario.setCpf(cpf);
        usuario.setPerfil("Estoquista");
        usuario.setStatus(true);

        usuarioService.cadastroUsuario(usuario);

        return new ModelAndView("redirect:cadastrar-estoquista");

    }

    @GetMapping("/listar-estoquistas")
    public ModelAndView carregarUsuarios(HttpSession session) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        List<Usuario> estoquistas = usuarioService.carregarEstoquistas();

        return new ModelAndView("administrador/listaEstoquista").addObject("estoquistas", estoquistas);
    }

    @RequestMapping(value = "/atualizar/{id}", method = RequestMethod.GET)
    public ModelAndView atualizarUser(@PathVariable int id, HttpSession session) {

        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        ResponseEntity<Usuario> usuarioResponse = usuarioService.filtroID(id);

        Usuario usuario = usuarioResponse.getBody();

        return new ModelAndView("produto/atualizarUsuario").addObject("usuarios", usuario);

    }

    @PostMapping("/atualizar/{id}")
    public String AtualizarProduto(
            @PathVariable int id,
            @RequestParam(name = "nome_usuario", required = true) String nome,
            @RequestParam(name = "email_usuario", required = true) String email,
            @RequestParam(name = "senha_usuario", required = true) String senha,
            @RequestParam(name = "cpf_usuario", required = true) String cpf,
            @RequestParam(name = "perfil_usuario", required = true) String perfil,
            @RequestParam(name = "status_usuario", required = true) boolean status,
            HttpSession session
    ) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return "<script>window.location.href = 'http://localhost:8080/admin'</script>";
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setCpf(cpf);
        usuario.setPerfil(perfil);
        usuario.setStatus(status);

        ResponseEntity usuarioAtualizar = usuarioService.updateUser(id, usuario);

        return "<script>window.location.href = 'http://localhost:8080/usuario/lista'</script>";

    }

    @PostMapping("/desativar/{id}")
    public String DesativarUsuario(
            @PathVariable int id,
            HttpSession session
    ) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return "<script>window.location.href = 'http://localhost:8080/admin'</script>";
        }

        ResponseEntity<Usuario> usuarioResponse = usuarioService.filtroID(id);

        Usuario usuario = usuarioResponse.getBody();

        usuario.setStatus(false);

        ResponseEntity usuarioAtualizar = usuarioService.updateUser(id, usuario);

        return "<script>window.location.href = 'http://localhost:8080/usuario/lista'</script>";

    }

    @GetMapping("/filtroNome/{nome}")
    public ModelAndView findByNome(@PathVariable String nome, HttpSession session) {

        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }
        List<Usuario> estoquistas = usuarioService.filtroNomeEstoquista(nome);

        return new ModelAndView("administrador/listaEstoquista").addObject("estoquistas", estoquistas);
    }

    @GetMapping("/filtroNome")
    public ModelAndView findByNomeVoid(HttpSession session) {

        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

            return new ModelAndView("redirect:/admin/listar-estoquistas");
    }

}
