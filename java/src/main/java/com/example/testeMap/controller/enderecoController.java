/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Endereco;
import com.example.testeMap.services.enderecoService;
import com.example.testeMap.services.usuarioService;
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
@RequestMapping("/endereco")
public class enderecoController {

    private enderecoService enderecoService;

    public enderecoController(enderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping("/cadastrar")
    public Endereco cadastrarEnderecoPOST(
                @RequestParam(name = "rua") String rua,
            @RequestParam(name = "numero", required = true) int numero,
            @RequestParam(name = "bairro", required = true) String bairro,
            @RequestParam(name = "cep", required = true) String cep,
            @RequestParam(name = "complemento") String complemento,
            @RequestParam(name = "tipo", required = true) String tipo,
            @RequestParam(name = "idUsuario", required = true) int idUsuario
    ) {
        String trataCEP = cep.replaceAll("-", "");
        
        Endereco endereco = new Endereco();

        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setCep(cep);
        endereco.setComplemento(complemento);
        endereco.setTipo(tipo);
        endereco.setIdUsuario(idUsuario);

        return enderecoService.cadastroEndereco(endereco);

    }

    @RequestMapping(method = RequestMethod.GET, path = "/lista")
    public List<Endereco> carregarUsuarios() {
        List<Endereco> endereco = enderecoService.carregarEndereco();

        return endereco;
    }

}
