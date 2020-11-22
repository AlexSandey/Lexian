/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.services;

import com.example.testeMap.model.entidades.Endereco;
import com.example.testeMap.model.entidades.Imagem;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.repository.enderecoRepository;
import com.example.testeMap.repository.imagemRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class imagemService {

    private imagemRepository imagemRepository;

    public imagemService(imagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }


    public Imagem cadastroImagem(Imagem imagem) {
        imagemRepository.save(imagem);
        return imagem;
    }

    public String caminhoImagemMin(int id_produto){
        return imagemRepository.filtroMinImagem(id_produto);
    }
    
    public Imagem deletaImagem(Imagem imagem) {
        imagemRepository.delete(imagem);
        return imagem;
    }
    
    public List<Imagem> imagemProdutosPorID(int id) {
        return imagemRepository.filtroImagemById(id);
    }
     

}
