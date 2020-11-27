/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.services.enderecoService;
import com.example.testeMap.services.usuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Programming
 */
@RestController
@RequestMapping("/pagamento")
public class compraController {

    private usuarioService usuarioService;
    private enderecoService enderecoService;

    public compraController(usuarioService usuarioService, enderecoService enderecoService) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
    }

    @GetMapping("")
    public ModelAndView realizarPagamentoGET(
            RedirectAttributes redirAttr,
            @RequestAttribute(name = "encaminharLogin", required = false) boolean encaminharLogin
    
    ) {
        try{
            if(encaminharLogin==true){
                redirAttr.addFlashAttribute("encaminharLogin", true);
                return new ModelAndView("realizarPagamento");        
            }
        }catch(Exception e){
            return new ModelAndView("realizarPagamento");        
        }

        return new ModelAndView("realizarPagamento");
    }
}
