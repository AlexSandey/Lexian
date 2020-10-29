/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Endereco;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.services.ServiceExc;
import com.example.testeMap.services.enderecoService;
import com.example.testeMap.services.usuarioService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author AlexSandey
 */






@RestController
@RequestMapping("/cliente")
public class clienteController {
    
    private usuarioService usuarioService;
    private enderecoService enderecoService;
    
    public clienteController(usuarioService usuarioService, enderecoService enderecoService) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
    }
    
    @GetMapping
    public ModelAndView loginUsuarioGET(    ) {
        return new ModelAndView("usuario/login");
    }
    
    @PostMapping("/cadastrar")
    public ModelAndView cadastrarClientePOST(
            @RequestParam(name = "nome_usuario", required = true) String nome,
            @RequestParam(name = "email_usuario", required = true) String email,
            @RequestParam(name = "cpf_usuario", required = true) String senha,
            @RequestParam(name = "senha_usuario", required = true) String cpf,
            @RequestParam(name = "rua") String rua,
            @RequestParam(name = "numero", required = true) int numero,
            @RequestParam(name = "bairro", required = true) String bairro,
            @RequestParam(name = "cep", required = true) String cep,
            @RequestParam(name = "complemento") String complemento
    ) {
        String teste = "teste";
        List<Usuario> validaCpf = usuarioService.validaCpf(cpf);
        
        if(!validaCpf.isEmpty()){
            teste = "false";
        }
        
        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setCpf(cpf);
        usuario.setPerfil("Cliente");
        usuario.setStatus(true);
        
        
        Usuario usuarioID = usuarioService.cadastroUsuario(usuario);

        Endereco enderecoCad = new Endereco();

        enderecoCad.setBairro(bairro);
        enderecoCad.setCep(cep);
        enderecoCad.setComplemento(complemento);
        enderecoCad.setIdUsuario(usuarioID.getIdUsuario());
        enderecoCad.setNumero(numero);
        enderecoCad.setRua(rua);
        enderecoCad.setTipo("Principal");

        Endereco enderecoRetorno = enderecoService.cadastroEndereco(enderecoCad);

        if (enderecoRetorno != null) {
            return new ModelAndView("redirect:cliente/login");
        }

        return new ModelAndView("redirect:cliente/login");

    }
    
    @PostMapping("/login")
    public ModelAndView loginCliente(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "senha", required = true) String senha,
            HttpSession session,
            RedirectAttributes redirAttr
    ) throws ServiceExc {

        Usuario usuario = usuarioService.logarUsuario(email, senha);
        
        String verificaCliente = usuario.getPerfil();
        
        if(verificaCliente.equals("Cliente")){
            session.setAttribute("usuario", usuario);
            return new ModelAndView("redirect:cliente/painel");
        }
        
        redirAttr.addFlashAttribute("msgErro", "erroLogin");
        
        return new ModelAndView("redirect:cliente/login");

    }
    
}
