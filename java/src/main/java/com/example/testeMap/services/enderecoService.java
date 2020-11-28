/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.services;

import com.example.testeMap.model.entidades.Endereco;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.repository.enderecoRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class enderecoService {
    
    private enderecoRepository enderecoRepository;

    public enderecoService(enderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }
    
    
    public Endereco cadastroEndereco(Endereco endereco) {
        enderecoRepository.save(endereco);
        return endereco;
    }
    
    public ResponseEntity<Endereco> filtroID(int id) {
        return enderecoRepository.findById(id).
                map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
    
       public List<Endereco> carregarEndereco() {
        return (List<Endereco>) enderecoRepository.findAll();
    }
    
    public ResponseEntity updateEndereco(int id,
             Endereco endereco) {
        return enderecoRepository.findById(id)
                .map(enderecoUpdate -> {
                    enderecoUpdate.setIdEndereco(id);
                    enderecoUpdate.setRua(endereco.getRua());
                    enderecoUpdate.setNumero(endereco.getNumero());
                    enderecoUpdate.setBairro(endereco.getBairro());
                    enderecoUpdate.setCep(endereco.getCep());
                    enderecoUpdate.setComplemento(endereco.getComplemento());
                    enderecoUpdate.setIdUsuario(endereco.getIdUsuario());
                    
                    Endereco updated = enderecoRepository.save(enderecoUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    
    
    public List<Endereco> filtroCep(String nome) {
        return enderecoRepository.filtroCEP(nome);
    }
     public List<Endereco> filtroTipo(String tipo) {
        return enderecoRepository.filtroTipo(tipo);
    }
      public Endereco filtroTipoAndId(String tipo, int id) {
        return enderecoRepository.filtroTipoAndId(tipo, id);
    }
      
        public Endereco filtroEndSelecionado(String tipo, int id) {
        return enderecoRepository.filtroEndSelecionado(tipo, id);
    }
      
      public List<Endereco> filtroTipoAndIdList(String tipo, int id) {
        return enderecoRepository.filtroTipoAndIdList(tipo, id);
    }
    
    
    
}
