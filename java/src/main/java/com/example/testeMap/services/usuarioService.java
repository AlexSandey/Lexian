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
    
    public ResponseEntity<Usuario> filtroID(int id) {
        return usuarioRepository.findById(id).
                map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
    
       public List<Usuario> carregarUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }
    
    public ResponseEntity updateUser(int id,
             Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(usuarioUpdate -> {
                    usuarioUpdate.setIdUsuario(id);
                    usuarioUpdate.setNome(usuario.getNome());
                    usuarioUpdate.setEmail(usuario.getEmail());
                    usuarioUpdate.setSenha(usuario.getSenha());
                    usuarioUpdate.setCpf(usuario.getCpf());
                    usuarioUpdate.setPerfil(usuario.getPerfil());
                    usuarioUpdate.setStatus(usuario.isStatus());
                   
                    Usuario updated = usuarioRepository.save(usuarioUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    
    public List<Usuario> filtroNome(String nome) {
        return usuarioRepository.filtroNome(nome);
    }
     public List<Usuario> filtroCpf(String cpf) {
        return usuarioRepository.filtroCpf(cpf);
    }
    
    
    
}
