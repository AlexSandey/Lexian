/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.repository;


import com.example.testeMap.model.entidades.Imagem;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author AlexSandey, Devaky
 */

public interface imagemRepository extends CrudRepository<Imagem, Integer>{
    
    @Query(value="SELECT caminho FROM imagem WHERE id_imagem = ( Select Min(id_imagem) from imagem where id_produto like %:id% )",nativeQuery=true)
    public String filtroMinImagem(int id);
    
    @Query(value="SELECT * FROM imagem WHERE id_produto like %:id%",nativeQuery=true)
    public List<Imagem> filtroImagemById(int id);

}
