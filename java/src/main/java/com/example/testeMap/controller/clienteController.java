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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
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
    
    @GetMapping("")
    public ModelAndView loginUsuarioGET(    ) {
        return new ModelAndView("usuario/login");
    }
    
    @PostMapping("/cadastrar")
    public ModelAndView cadastrarClientePOST(
            @RequestParam(name = "nome_usuario", required = true) String nome,
            @RequestParam(name = "email_usuario", required = true) String email,
            @RequestParam(name = "cpf_usuario", required = true) String cpf,
            @RequestParam(name = "senha_usuario", required = true) String senha,
            @RequestParam(name = "rua") String rua,
            @RequestParam(name = "numero", required = true) int numero,
            @RequestParam(name = "bairro", required = true) String bairro,
            @RequestParam(name = "cep", required = true) String cep,
            @RequestParam(name = "complemento") String complemento,
            @RequestParam(name = "verify-revenues-choice") int verify,
            @RequestParam(name = "rua_entrega") String rua_entrega,
            @RequestParam(name = "numero_entrega") int numero_entrega,
            @RequestParam(name = "bairro_entrega") String bairro_entrega,
            @RequestParam(name = "cep_entrega") String cep_entrega,
            @RequestParam(name = "complemento_entrega") String complemento_entrega,
            RedirectAttributes redirAttr
    ) throws NoSuchAlgorithmException {

        List<Usuario> validaCpf = usuarioService.validaCpf(cpf);
        
        if(!validaCpf.isEmpty()){
            redirAttr.addFlashAttribute("msgSucesso", "Erro ao realizar Cadastro! CPF ja existente");
            return new ModelAndView("redirect:/cliente");
        }
        
                
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        byte[] messageDigest = md.digest(senha.getBytes());
        
        BigInteger number = new BigInteger(1, messageDigest);
        
        String senha_hash = number.toString(16);
        
        
        Usuario usuario = new Usuario(nome,email,senha_hash,"Cliente",cpf,true);
        Usuario usuarioID = usuarioService.cadastroUsuario(usuario);
        
        if(verify == 1){
            Endereco enderecoCad = new Endereco(rua,numero,bairro,cep,complemento,"Entrega",usuarioID.getIdUsuario());
            enderecoService.cadastroEndereco(enderecoCad);
        }
        
        if(verify==0){
            Endereco enderecoCad = new Endereco(rua_entrega,numero_entrega,bairro_entrega,cep_entrega,complemento_entrega,"Entrega",usuarioID.getIdUsuario());
            enderecoService.cadastroEndereco(enderecoCad);
        }
        
        Endereco enderecoCad = new Endereco(rua,numero,bairro,cep,complemento,"Faturamento",usuarioID.getIdUsuario());
        enderecoService.cadastroEndereco(enderecoCad);

        redirAttr.addFlashAttribute("msgSucesso", "Cadastro realizado com sucesso! Realize o login Abaixo");
        return new ModelAndView("redirect:/cliente");
    }
    
    @PostMapping("/login")
    public ModelAndView loginCliente(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "senha", required = true) String senha,
            HttpSession session,
            RedirectAttributes redirAttr
    ) throws ServiceExc, NoSuchAlgorithmException {
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        byte[] messageDigest = md.digest(senha.getBytes());
        
        BigInteger number = new BigInteger(1, messageDigest);
        
        String senha_hash = number.toString(16);
        
        Usuario usuario = usuarioService.logarUsuario(email, senha_hash);
        
        String verificaCliente = usuario.getPerfil();
        
        if(verificaCliente.equals("Cliente")){
            session.setAttribute("usuario", usuario);
            return new ModelAndView("redirect:/cliente/painel");
        }
        
        redirAttr.addFlashAttribute("msgErro", "erroLogin");
        
        return new ModelAndView("redirect:/cliente/login");

    }
    
    @GetMapping("/painel")
    public ModelAndView painelCliente(
        HttpSession session
    ) {
        Usuario usuarioSessao =  (Usuario) session.getAttribute("usuario");
        
        ResponseEntity<Usuario> usuarioBancoResp = usuarioService.filtroID(usuarioSessao.getIdUsuario());
        
        Usuario usuarioBanco = usuarioBancoResp.getBody();
        
        
        Endereco enderecoBanco = enderecoService.filtroTipoAndId("Faturamento", usuarioSessao.getIdUsuario());
        
        
        session.removeAttribute("usuario");
        session.removeAttribute("endereco");
       
        session.setAttribute("usuario", usuarioBanco);
        session.setAttribute("endereco", enderecoBanco);
        
        return new ModelAndView("usuario/painel").addObject(session);

    }
    
    @PostMapping("/atualizar")
    public ModelAndView atualizarNomeEFaturamentoPOST(
            @RequestParam(name = "nome_cliente", required = true) String nome,
            
            @RequestParam(name = "rua_cliente") String rua,
            @RequestParam(name = "numero_cliente", required = true) int numero,
            @RequestParam(name = "bairro_cliente", required = true) String bairro,
            @RequestParam(name = "cep_cliente", required = true) String cep,
            @RequestParam(name = "complemento_cliente") String complemento,
            @RequestParam(name = "idEndereco_cliente", required = true) int id_endereco,


            HttpSession session
    ) {
        session.removeAttribute("mensagem");
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        int usuarioID = usuario.getIdUsuario();
        
        Endereco endereco = new Endereco(id_endereco, rua, numero, bairro, cep, complemento,"Faturamento",usuarioID);

        
        ResponseEntity enderecoResp = enderecoService.updateEndereco(id_endereco, endereco);
        
        ResponseEntity usuarioResp = usuarioService.updateUserJustNome(usuario.getIdUsuario(), nome);
        
        Usuario usuarioReturn = (Usuario) usuarioResp.getBody();
        Endereco enderecoReturn = (Endereco) enderecoResp.getBody();
        
        if(usuarioReturn.getIdUsuario() == usuario.getIdUsuario() && enderecoReturn.getIdUsuario() == usuario.getIdUsuario()){
            session.setAttribute("mensagem", "Atualizado com Sucesso");
        }else{
            session.setAttribute("mensagem", "Falha no Cadastro");
        }

        return new ModelAndView("redirect:/cliente/painel");
    }
    
    @GetMapping("/logout")
    public ModelAndView logoutUsuarioGET(  
            HttpSession session
    ) {
        session.invalidate();
        return new ModelAndView("redirect:/cliente");
    }
    
    @PostMapping("/adicionarEndereco")
    public ModelAndView cadastrarEnderecoUsuarioPOST(
            @RequestParam(name = "rua_cliente") String rua,
            @RequestParam(name = "numero_cliente", required = true) int numero,
            @RequestParam(name = "bairro_cliente", required = true) String bairro,
            @RequestParam(name = "cep_cliente", required = true) String cep,
            @RequestParam(name = "complemento_cliente") String complemento,
            HttpSession session
    ) {
        Endereco endereco = new Endereco();
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setCep(cep);
        endereco.setComplemento(complemento);
        endereco.setTipo("Entrega");
        endereco.setIdUsuario(usuario.getIdUsuario());
        
        enderecoService.cadastroEndereco(endereco);

        session.setAttribute("mensagem", "Atualizado com Sucesso");

        return new ModelAndView("redirect:/cliente/painel");

    }
    
}


