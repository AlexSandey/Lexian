/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.services;

import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.repository.usuarioRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class usuarioService {
    
    private usuarioRepository usuarioRepository;

    public usuarioService(usuarioRepository usuarioRepository1) {
        this.usuarioRepository = usuarioRepository1;
    }
    
    
    public Usuario cadastroUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return usuario;
    }
    
    public List<Usuario> validaCpf(String cpf) {
        List valida = usuarioRepository.validaCpf(cpf);
        return valida;
    }
    
    public Usuario logarUsuario(String email, String senha) throws ServiceExc{
        Usuario usuario = usuarioRepository.BuscaLogin(email, senha);
        return usuario;
    }
    
    public ResponseEntity<Usuario> filtroID(int id) {
        return usuarioRepository.findById(id).
                map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
    
       public List<Usuario> carregarUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }
       public List<Usuario> carregarEstoquistas() {
        return (List<Usuario>) usuarioRepository.listarEstoquista();
    }
    
    public ResponseEntity updateEstoquistaSimples(int id,
             Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(usuarioUpdate -> {
                    usuarioUpdate.setIdUsuario(id);
                    usuarioUpdate.setNome(usuario.getNome());
                    usuarioUpdate.setEmail(usuario.getEmail());
                    usuarioUpdate.setCpf(usuario.getCpf());
                   
                    Usuario updated = usuarioRepository.save(usuarioUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    public ResponseEntity updateEstoquistaSenha(int id,
             Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(usuarioUpdate -> {
                    usuarioUpdate.setIdUsuario(id);
                    usuarioUpdate.setNome(usuario.getNome());
                    usuarioUpdate.setEmail(usuario.getEmail());
                    usuarioUpdate.setCpf(usuario.getCpf());
                    usuarioUpdate.setSenha(usuario.getSenha());

                    Usuario updated = usuarioRepository.save(usuarioUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    
    public ResponseEntity updateEstoquistaPerfil(int id,
             Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(usuarioUpdate -> {
                    usuarioUpdate.setIdUsuario(id);
                    usuarioUpdate.setNome(usuario.getNome());
                    usuarioUpdate.setEmail(usuario.getEmail());
                    usuarioUpdate.setCpf(usuario.getCpf());
                    usuarioUpdate.setPerfil(usuario.getPerfil());

                    Usuario updated = usuarioRepository.save(usuarioUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    
    public ResponseEntity updateEstoquistaSenhaPerfil(int id,
             Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(usuarioUpdate -> {
                    usuarioUpdate.setIdUsuario(id);
                    usuarioUpdate.setNome(usuario.getNome());
                    usuarioUpdate.setEmail(usuario.getEmail());
                    usuarioUpdate.setCpf(usuario.getCpf()); 
                    usuarioUpdate.setSenha(usuario.getSenha());
                    usuarioUpdate.setPerfil(usuario.getPerfil());

                    Usuario updated = usuarioRepository.save(usuarioUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
        public ResponseEntity removeEstoquista(int id) {
        return usuarioRepository.findById(id)
                .map(usuarioUpdate -> {
                    usuarioUpdate.setStatus(false);


                    Usuario updated = usuarioRepository.save(usuarioUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
        
    public ResponseEntity updateUserJustNome(int id,
             String nome) {
        return usuarioRepository.findById(id)
                .map(usuarioUpdate -> {
                    usuarioUpdate.setIdUsuario(id);
                    usuarioUpdate.setNome(nome);

                    Usuario updated = usuarioRepository.save(usuarioUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    
    public List<Usuario> filtroNome(String nome) {
        return usuarioRepository.filtroNome(nome);
    }
    public List<Usuario> filtroNomeEstoquista(String nome) {
        return usuarioRepository.filtroNomeEstoquista(nome);
    }
     public List<Usuario> filtroCpf(String cpf) {
        return usuarioRepository.filtroCpf(cpf);
    }
    
    
    
}
