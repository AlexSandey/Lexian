/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Endereco;
import com.example.testeMap.model.entidades.Pedido;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.services.PedidoService;
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
import org.springframework.web.bind.annotation.RequestAttribute;
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
    private PedidoService pedidoService;

    public clienteController(usuarioService usuarioService, enderecoService enderecoService, PedidoService PedidoService) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.pedidoService = PedidoService;
    }

    @GetMapping("")
    public ModelAndView loginUsuarioGET(
            RedirectAttributes redirAttr,
            @RequestAttribute(name = "encaminharLogin", required = false) boolean encaminharLogin
    
    ) {
        try{
            if(encaminharLogin==true){
                redirAttr.addFlashAttribute("encaminharLogin", true);
                return new ModelAndView("login");        
            }
        }catch(Exception e){
            return new ModelAndView("login");        
        }

        return new ModelAndView("login");
    }
    
     @GetMapping("registrar")
    public ModelAndView registrarUsuarioGET(
            RedirectAttributes redirAttr,
            @RequestAttribute(name = "encaminharLogin", required = false) boolean encaminharLogin
    
    ) {
        try{
            if(encaminharLogin==true){
                redirAttr.addFlashAttribute("encaminharLogin", true);
                return new ModelAndView("registrar");        
            }
        }catch(Exception e){
            return new ModelAndView("registrar");        
        }

        return new ModelAndView("registrar");
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
            @RequestParam(name = "confirmarEndereco") int confirmarEndereco,
            @RequestParam(name = "rua_entrega",required =false) String rua_entrega,
            @RequestParam(name = "numero_entrega",required =false) int numero_entrega,
            @RequestParam(name = "bairro_entrega",required =false) String bairro_entrega,
            @RequestParam(name = "cep_entrega",required =false) String cep_entrega,
            @RequestParam(name = "complemento_entrega",required =false) String complemento_entrega,
            RedirectAttributes redirAttr
    ) throws NoSuchAlgorithmException {

        
        String trataCep = cep.replaceAll("-", "");
        String trataCepEntrega = cep_entrega.replaceAll("-", "");
        String trataCPF = cpf.replaceAll("-", "");
        trataCPF = trataCPF.replace(".", "");
       
        
        List<Usuario> validaCpf = usuarioService.validaCpf(cpf);

        if (!validaCpf.isEmpty()) {
            redirAttr.addFlashAttribute("msgSucesso", "Erro ao realizar Cadastro! CPF ja existente");
            return new ModelAndView("redirect:/cliente");
        }

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(senha.getBytes());

        BigInteger number = new BigInteger(1, messageDigest);

        String senha_hash = number.toString(16);

        Usuario usuario = new Usuario(nome, email, senha_hash, "Cliente", trataCPF, true);
        Usuario usuarioID = usuarioService.cadastroUsuario(usuario);

        if (confirmarEndereco == 1) {
            Endereco enderecoCad = new Endereco(rua, numero, bairro, trataCep, complemento, "Entrega", usuarioID.getIdUsuario());
            enderecoService.cadastroEndereco(enderecoCad);
        }

        if (confirmarEndereco == 0) {
            Endereco enderecoCad = new Endereco(rua_entrega, numero_entrega, bairro_entrega, trataCepEntrega, complemento_entrega, "Entrega", usuarioID.getIdUsuario());
            enderecoService.cadastroEndereco(enderecoCad);
        }

        Endereco enderecoCad = new Endereco(rua, numero, bairro, trataCep, complemento, "Faturamento", usuarioID.getIdUsuario());
        enderecoService.cadastroEndereco(enderecoCad);

        //redirAttr.addFlashAttribute("msgSucesso", "Cadastro realizado com sucesso! Realize o login Abaixo");
        return new ModelAndView("redirect:/cliente");
    }

    @PostMapping("/login")
    public ModelAndView loginCliente(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "senha", required = true) String senha,
            @RequestParam(name = "encaminharCarrinho", required = false) String encaminhar,
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

            if (verificaCliente.equals("Cliente")) {
                session.setAttribute("usuario", usuario);

                if (encaminhar == "encaminharCarrinho") {
                    return new ModelAndView("redirect:http://localhost:8080/carrinho/dadosdecompra");
                }

                return new ModelAndView("redirect:http://localhost:8080/cliente/painel");
            }
            
        } catch (Exception e) {
            redirAttr.addFlashAttribute("msgErro", "erroLogin");

            return new ModelAndView("redirect:/cliente");
        }
        
        redirAttr.addFlashAttribute("msgErro", "erroLogin");
        return new ModelAndView("redirect:/cliente");
    }

    @GetMapping("/painel")
    public ModelAndView painelCliente(
            HttpSession session
    ) {
        Usuario usuarioSessao = (Usuario) session.getAttribute("usuario");

        List<Pedido> pedidos = pedidoService.carregarPedidoByID(usuarioSessao.getIdUsuario());

       
        session.setAttribute("compraProduto", pedidos);

        return new ModelAndView("cliente/listaPedidos").addObject(session);

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

        Endereco endereco = new Endereco(id_endereco, rua, numero, bairro, cep, complemento, "Faturamento", usuarioID);

        ResponseEntity enderecoResp = enderecoService.updateEndereco(id_endereco, endereco);

        ResponseEntity usuarioResp = usuarioService.updateUserJustNome(usuario.getIdUsuario(), nome);

        Usuario usuarioReturn = (Usuario) usuarioResp.getBody();
        Endereco enderecoReturn = (Endereco) enderecoResp.getBody();

        if (usuarioReturn.getIdUsuario() == usuario.getIdUsuario() && enderecoReturn.getIdUsuario() == usuario.getIdUsuario()) {
            session.setAttribute("mensagem", "Atualizado com Sucesso");
        } else {
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
