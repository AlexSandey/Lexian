/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.repository;


import com.example.testeMap.model.entidades.Faq;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author AlexSandey, Devaky
 */

public interface faqRepository extends CrudRepository<Faq, Integer>{
    
    @Query(value="SELECT caminho FROM imagem WHERE id_imagem = ( Select Min(id_imagem) from imagem where id_produto like %:id% )",nativeQuery=true)
    public String filtroMinImagem(int id);
    
    @Query(value="SELECT * FROM imagem WHERE id_produto like %:id%",nativeQuery=true)
    public List<Faq> filtroImagemById(int id);

}
