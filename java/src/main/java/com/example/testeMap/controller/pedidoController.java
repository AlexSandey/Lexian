/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Cartao;
import com.example.testeMap.model.entidades.Endereco;
import com.example.testeMap.model.entidades.Frete;
import com.example.testeMap.model.entidades.ItemPedido;
import com.example.testeMap.model.entidades.ItensCarrinho;
import com.example.testeMap.model.entidades.Pedido;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.services.ItemPedidoService;
import com.example.testeMap.services.PedidoService;
import com.example.testeMap.services.enderecoService;
import com.example.testeMap.services.usuarioService;
import com.example.testeMap.util.GerarBoleto;
import com.example.testeMap.util.autenticar;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author AlexSandey
 */
@RestController
@RequestMapping("/pedido")
public class pedidoController {

    private usuarioService usuarioService;
    private enderecoService enderecoService;
    private ItemPedidoService ItemPedidoService;
    private PedidoService PedidoService;

    public pedidoController(usuarioService usuarioService, enderecoService enderecoService, PedidoService PedidoService, ItemPedidoService ItemPedidoService) {
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.PedidoService = PedidoService;
        this.ItemPedidoService = ItemPedidoService;
    }

    @GetMapping("")
    public ModelAndView dadosPedidoGET(
            HttpSession session
    ) {
        try {
            if (autenticar.porSessao(session, "Cliente")) {
                Usuario usuarioSessao = (Usuario) session.getAttribute("usuario");

                Frete frete = new Frete();
                List<Frete> fretes = frete.fretes();

                List<ItensCarrinho> carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");
                float total = 0;

                for (ItensCarrinho produto : carrinho) {
                    total += produto.getValor() * produto.getQtde();
                }

                List<Endereco> enderecoEntrega = enderecoService.filtroTipoAndIdList("Entrega", usuarioSessao.getIdUsuario());

                session.setAttribute("fretes", fretes);
                session.setAttribute("enderecoEntrega", enderecoEntrega);

                return new ModelAndView("/realizarPagamento").addObject(session);
            } else {
                return new ModelAndView("redirect:/cliente");
            }

        } catch (Exception e) {

            return new ModelAndView("redirect:/cliente");

        }

    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarPedidoPOST(
            // ID USUARIO VEM DA SESSION >>>> @RequestParam(name = "id_usuario", required = true) int idUsuario,
            @RequestParam(name = "enderecoEscolhido", required = false) int idEndereco,
            // CALCULO SERA FEITO ATRAVES DO CARRINHO DA SESSION @RequestParam(name = "valor_total", required = true) float valorTotal,
            @RequestParam(name = "metodoPagamento",required=true) int metodoPagamento,
            @RequestParam(name = "nomeCartao", required = false) String nomeCartao,
            @RequestParam(name = "numeroCartao", required = false) String numCartao,
            @RequestParam(name = "parcelaCartao", required = false) int parcela,
            @RequestParam(name = "freteEscolhido", required = false) float frete,
            HttpSession session,
            RedirectAttributes redirAttr
    ) throws NoSuchAlgorithmException {

        GerarBoleto gerarBol = new GerarBoleto();
        String boleto= gerarBol.gerarBoleto();
        
        Usuario usuarioSessao = (Usuario) session.getAttribute("usuario");
        int idUsuario = usuarioSessao.getIdUsuario();
        Pedido CadPedido = new Pedido();
        
        
        Frete freteSessao = (Frete) session.getAttribute("freteEscolhido");

        List<ItensCarrinho> carrinho;
        carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");

        float valorTotal = 0;

        for (ItensCarrinho Produto : carrinho) {
            if (Produto.getQtde() > 1) {
                valorTotal = valorTotal + (Produto.getQtde() * Produto.getValor());
            } else {
                valorTotal += Produto.getValor();
            }
        }
        valorTotal = (float) (valorTotal + freteSessao.getValor());


        int idStatus = 1;

        if (metodoPagamento == 1) {

            Pedido novoPedido = new Pedido( idUsuario, idEndereco, valorTotal, "cartao", nomeCartao, numCartao, parcela, boleto, idStatus,LocalDate.now());
            CadPedido = PedidoService.CadastrarPedido(novoPedido);
        }
        if (metodoPagamento == 2) {
            Pedido novoPedido = new Pedido( idUsuario, idEndereco, valorTotal, "boleto", nomeCartao, numCartao, 1, boleto, idStatus,LocalDate.now());
            CadPedido = PedidoService.CadastrarPedido(novoPedido);
        }
        int idPedido = CadPedido.getIdPedido();

        for (ItensCarrinho Produto : carrinho) {
            ItemPedido itemPedido = new ItemPedido(idPedido, Produto.getIdProduto(), Produto.getQtde(), Produto.getValor());
            ItemPedido cadItem = ItemPedidoService.CadastrarItemPedido(itemPedido);
        }
        
        session.removeAttribute("carrinho");
        session.removeAttribute("metodoPagamento");
        session.removeAttribute("valorFinal");
        session.removeAttribute("freteEscolhido");
        session.removeAttribute("cartao");
        session.removeAttribute("endSelecionado");

        //redirAttr.addFlashAttribute("msgSucesso", "Cadastro realizado com sucesso! Realize o login Abaixo");
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/confirmarDados")
    public ModelAndView confirmarPedidoPOST(
            // ID USUARIO VEM DA SESSION >>>> @RequestParam(name = "id_usuario", required = true) int idUsuario,
            @RequestParam(name = "enderecoEscolhido", required = true) int idEndereco,
            // CALCULO SERA FEITO ATRAVES DO CARRINHO DA SESSION @RequestParam(name = "valor_total", required = true) float valorTotal,
            @RequestParam(name = "metodoPagamento") int metodoPagamento,
            @RequestParam(name = "nomeCartao") String nomeCartao,
            @RequestParam(name = "numeroCartao", required = true) String numCartao,
            @RequestParam(name = "parcelaCartao", required = true) int parcela,
            @RequestParam(name = "freteEscolhido", required = true) int frete,
            HttpSession session,
            RedirectAttributes redirAttr
    ) throws NoSuchAlgorithmException {

        Cartao cartao = new Cartao(nomeCartao, numCartao, parcela);
        Endereco endEntrega = enderecoService.filtroEndSelecionado("Entrega", idEndereco);

        List<ItensCarrinho> carrinho;
        carrinho = (List<ItensCarrinho>) session.getAttribute("carrinho");

        Frete freteEscolhido = new Frete();
        freteEscolhido = freteEscolhido.findById(frete);

        float totalProd = 0;
        for (ItensCarrinho item : carrinho) {
            if (item.getQtde() > 1) {
                totalProd = totalProd + (item.getQtde() * item.getValor());
            } else {
                totalProd = totalProd + item.getValor();
            }
        }

        float valorFinal = (float) (totalProd + freteEscolhido.getValor());

        session.setAttribute("metodoPagamento", metodoPagamento);
        session.setAttribute("valorFinal", valorFinal);
        session.setAttribute("freteEscolhido", freteEscolhido);
        session.setAttribute("cartao", cartao);
        session.setAttribute("endSelecionado", endEntrega);

        
        //redirAttr.addFlashAttribute("msgSucesso", "Cadastro realizado com sucesso! Realize o login Abaixo");
        return new ModelAndView("/confirmarPagamento");
    }

}
