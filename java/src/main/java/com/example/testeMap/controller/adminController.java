    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Produto;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.services.usuarioService;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admin")
public class adminController {

    private usuarioService usuarioService;

    public adminController(usuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    
    @GetMapping("/cadastrar-estoquista")
    public ModelAndView cadastrarEstoquistaGET()
    {
       return new ModelAndView("administrador/cadastrarEstoquista"); 
    }
    
    @PostMapping("/cadastrar-estoquista")
    public ModelAndView cadastrarEstoquistaPOST(
        @RequestParam(name = "nome_usuario", required = true) String nome,
        @RequestParam(name = "email_usuario", required = true) String email,
        @RequestParam(name = "senha_usuario", required = true) String senha,
        @RequestParam(name = "cpf_usuario", required = true) String cpf
    ) throws NoSuchAlgorithmException
    {
        
        Usuario usuario =  new Usuario();
        
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
    public ModelAndView carregarUsuarios() {
        List<Usuario> estoquistas = usuarioService.carregarEstoquistas();

        return new ModelAndView("administrador/listaEstoquista").addObject("estoquistas", estoquistas); 
    }
    
    @RequestMapping(value = "/atualizar/{id}", method = RequestMethod.GET)
    public ModelAndView atualizarUser(@PathVariable int id) {
        ResponseEntity<Usuario> usuarioResponse =  usuarioService.filtroID(id);
        
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
        @RequestParam(name = "status_usuario", required = true) boolean status    
    ){

        Usuario usuario =  new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNome(nome);
        usuario.setEmail(email);   
        usuario.setSenha(senha);
        usuario.setCpf(cpf);
        usuario.setPerfil(perfil);
        usuario.setStatus(status);
        
        
        ResponseEntity usuarioAtualizar =  usuarioService.updateUser(id,usuario);
       
       
       
       return "<script>window.location.href = 'http://localhost:8080/usuario/lista'</script>";

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> filtroID(@PathVariable int id) {
        return usuarioService.filtroID(id);
    }

    @PostMapping("/desativar/{id}")
    public String DesativarUsuario( 
        @PathVariable int id
    ){

        ResponseEntity<Usuario> usuarioResponse =  usuarioService.filtroID(id);
        
        Usuario usuario = usuarioResponse.getBody();
        
        usuario.setStatus(false);

        
        
        ResponseEntity usuarioAtualizar =  usuarioService.updateUser(id,usuario);
       
       
       
       return "<script>window.location.href = 'http://localhost:8080/usuario/lista'</script>";

    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") int id,
            @RequestBody Usuario prod) {
        return usuarioService.updateUser(id, prod);
    }
    
   @RequestMapping(value = "/filtroNome/{nome}", method = RequestMethod.GET)
    public List<Usuario> findByNome(@PathVariable String nome) {
        return usuarioService.filtroNome(nome);
    }
    
    @RequestMapping(value = "/filtroCpf/{cpf}", method = RequestMethod.GET)
    public List<Usuario> findByCpf(@PathVariable String cpf) {
        return usuarioService.filtroCpf(cpf);
    }

}
