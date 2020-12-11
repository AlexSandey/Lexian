/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.services;

import com.example.testeMap.model.entidades.Endereco;
import com.example.testeMap.model.entidades.Faq;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.repository.enderecoRepository;
import com.example.testeMap.repository.faqRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class faqService {

    private faqRepository faqRepository;

    public faqService(faqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }


    public Faq cadastroFaq(Faq faq) {
        faqRepository.save(faq);
        return faq;
    }
  

}
