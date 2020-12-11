/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.testeMap.controller;

import com.example.testeMap.model.entidades.Faq;
import com.example.testeMap.model.entidades.Imagem;
import com.example.testeMap.model.entidades.Produto;
import com.example.testeMap.model.entidades.Usuario;
import com.example.testeMap.services.ServiceExc;
import com.example.testeMap.services.faqService;
import com.example.testeMap.services.imagemService;
import com.example.testeMap.services.produtoService;
import com.example.testeMap.services.usuarioService;
import com.example.testeMap.util.autenticar;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/admin")
public class adminController {

    private static String UPLOADED_FOLDER = "c://imagens//";

    private usuarioService usuarioService;
    private produtoService produtoService;
    private imagemService imagemService;
    private faqService faqService;

    public adminController(usuarioService usuarioService, produtoService produtoService, imagemService imagemService, faqService faqService) {
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
        this.imagemService = imagemService;
        this.faqService = faqService;
    }

    @GetMapping("")
    public ModelAndView loginAdminGET(HttpSession session) {

        if (autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin/cadastrar-estoquista");
        }

        return new ModelAndView("administrador/login");
    }

    @PostMapping("/login")
    public ModelAndView loginCliente(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "senha", required = true) String senha,
            HttpSession session,
            RedirectAttributes redirAttr
    ) throws ServiceExc, NoSuchAlgorithmException {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(senha.getBytes());

            BigInteger number = new BigInteger(1, messageDigest);

            String senha_hash = number.toString(16);

            Usuario usuario = usuarioService.logarUsuario(email, senha_hash);

            String verificaCliente = usuario.getPerfil();

            if (verificaCliente.equals("Administrador")) {
                session.setAttribute("usuario", usuario);

                return new ModelAndView("redirect:http://localhost:8080/admin/cadastrar-estoquista");
            }

        } catch (Exception e) {
            redirAttr.addFlashAttribute("msgErro", "erroLogin");

            return new ModelAndView("redirect:/admin");
        }

        redirAttr.addFlashAttribute("msgErro", "erroLogin");
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/cadastrar-estoquista")
    public ModelAndView cadastrarEstoquistaGET(HttpSession session) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        return new ModelAndView("administrador/cadastrarEstoquista");
    }

    @GetMapping("/cadastrar-produto")
    public ModelAndView cadastrarProdutoGET(HttpSession session) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        return new ModelAndView("administrador/cadastrarProduto");
    }

    @GetMapping("/logout")
    public ModelAndView logoutAdminGET(
            HttpSession session
    ) {
        session.invalidate();
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/cadastrar-estoquista")
    public ModelAndView cadastrarEstoquistaPOST(
            @RequestParam(name = "nome_usuario", required = true) String nome,
            @RequestParam(name = "email_usuario", required = true) String email,
            @RequestParam(name = "senha_usuario", required = true) String senha,
            @RequestParam(name = "cpf_usuario", required = true) String cpf,
            HttpSession session
    ) throws NoSuchAlgorithmException {
        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setEmail(email);

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(senha.getBytes());

        BigInteger number = new BigInteger(1, messageDigest);

        String senha_hash = number.toString(16);

        usuario.setSenha(senha_hash);
        usuario.setCpf(cpf);
        usuario.setPerfil("Estoquista");
        usuario.setStatus(true);

        usuarioService.cadastroUsuario(usuario);

        return new ModelAndView("redirect:cadastrar-estoquista");

    }

    @PostMapping("/cadastrar-produto")
    public ModelAndView CadastrarProduto(
            @RequestParam(name = "nome_produto", required = true) String nomeProduto,
            @RequestParam(name = "qtde_produto", required = true) int qtde,
            @RequestParam(name = "marca_produto", required = true) String marca,
            @RequestParam(name = "categoria_produto", required = true) String categoria,
            @RequestParam(name = "descricao_produto", required = true) String descricao,
            @RequestParam(name = "valor_produto", required = true) String valor,
            @RequestParam(name = "cadastro_ativo", required = true) boolean ativo,
            @RequestParam(name = "pergunta1", required = true) String pergunta1,
            @RequestParam(name = "resposta1", required = true) String resposta1,
            @RequestParam(name = "pergunta2", required = true) String pergunta2,
            @RequestParam(name = "resposta2", required = true) String resposta2,
            @RequestParam("imagem") MultipartFile[] imagens,
            HttpSession session
    ) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }
        Produto produto = new Produto();

        Float valorConvertido = Float.parseFloat(valor.replace(".", "").replace(",", "."));

        produto.setNomeProduto(nomeProduto);
        produto.setQtde(qtde);
        produto.setMarca(marca);
        produto.setCategoria(categoria);
        produto.setDescricao(descricao);
        produto.setValor(valorConvertido);
        produto.setAtivo(ativo);

        Produto produtoCadastrado = produtoService.CadastrarProduto(produto);
        
        Faq faq1 = new Faq();
        faq1.setIdProduto(produtoCadastrado.getIdProduto());
        faq1.setPergunta(pergunta1);
        faq1.setResposta(resposta1);
        
        faqService.cadastroFaq(faq1);
        
        
        Faq faq2 = new Faq();
        faq2.setIdProduto(produtoCadastrado.getIdProduto());
        faq2.setPergunta(pergunta2);
        faq2.setResposta(resposta2);

        faqService.cadastroFaq(faq2);
        
        try {

            for (MultipartFile uploadedFile : imagens) {
                int idProduto = produtoCadastrado.getIdProduto();
                String folderIMG_DB = "IMAGESARCHIVE\\";
                String folderIMG = "src\\main\\resources\\static\\IMAGESARCHIVE\\" + idProduto + "\\";

                byte[] bytes = uploadedFile.getBytes();

                String nameImagem = uploadedFile.getOriginalFilename();
                Path path = Paths.get(folderIMG + nameImagem);

                File file = new File(folderIMG);

                file.mkdir();

                Files.write(path, bytes);

                Imagem imagem = new Imagem();

                imagem.setCaminho("http://localhost:8080/" + folderIMG_DB + "\\" + idProduto + "\\" + nameImagem);
                imagem.setIdProduto(idProduto);
                imagemService.cadastroImagem(imagem);
            }

        } catch (IOException e) {

        }

        return new ModelAndView("administrador/cadastrarProduto");

    }

    @GetMapping("/listar-estoquistas")
    public ModelAndView carregarUsuarios(HttpSession session) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        List<Usuario> estoquistas = usuarioService.carregarEstoquistas();

        return new ModelAndView("administrador/listaEstoquista").addObject("estoquistas", estoquistas);
    }

    /* ----- Metodo Utilizado anteriormente para efetuar a verificação e envio dos 
             dados para a pagina responsavel por imprimir previamente as informações do estoquista ----
    
    @RequestMapping(value = "/atualizar/{id}", method = RequestMethod.GET)
    public ModelAndView atualizarUser(@PathVariable int id, HttpSession session) {

        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        ResponseEntity<Usuario> usuarioResponse = usuarioService.filtroID(id);

        Usuario usuario = usuarioResponse.getBody();

        return new ModelAndView("produto/atualizarUsuario").addObject("usuarios", usuario);

    }
     */
    @PostMapping("/atualizar-estoquista")
    public String atualizarEstoquista(
            @RequestParam(name = "id_usuario", required = true) String id_usuario,
            @RequestParam(name = "nome_usuario", required = true) String nome,
            @RequestParam(name = "cpf_usuario", required = true) String cpf,
            @RequestParam(name = "senha_usuario", required = false) String senha,
            @RequestParam(name = "tipo_usuario", required = false) String perfil,
            HttpSession session
    ) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return "<script>window.location.href = 'http://localhost:8080/admin'</script>";
        }
        int id = Integer.parseInt(id_usuario);

        ResponseEntity<Usuario> usuarioResponse = usuarioService.filtroID(id);

        Usuario usuario = usuarioResponse.getBody();

        usuario.setNome(nome);
        usuario.setCpf(cpf);

        try {
            if (senha == null && "1".equals(perfil)) {
                ResponseEntity usuarioAtualizar = usuarioService.updateEstoquistaSimples(id, usuario);
            } else if (senha != null && "1".equals(perfil)) {

                MessageDigest md = MessageDigest.getInstance("MD5");

                byte[] messageDigest = md.digest(senha.getBytes());

                BigInteger number = new BigInteger(1, messageDigest);

                String senha_hash = number.toString(16);

                usuario.setSenha(senha_hash);

                ResponseEntity usuarioAtualizar = usuarioService.updateEstoquistaSenha(id, usuario);
            } else if (senha == null && !"1".equals(perfil)) {
                usuario.setPerfil("Cliente");
                ResponseEntity usuarioAtualizar = usuarioService.updateEstoquistaPerfil(id, usuario);
            } else if (senha != null && "2".equals(perfil)) {
                usuario.setPerfil("Cliente");
                MessageDigest md = MessageDigest.getInstance("MD5");

                byte[] messageDigest = md.digest(senha.getBytes());

                BigInteger number = new BigInteger(1, messageDigest);

                String senha_hash = number.toString(16);

                usuario.setSenha(senha_hash);
                ResponseEntity usuarioAtualizar = usuarioService.updateEstoquistaSenhaPerfil(id, usuario);
                String teste = "1";
            }
        } catch (Exception e) {
            return "<script>window.location.href = 'http://localhost:8080/admin/listar-estoquistas'</script>";
        }

        return "<script>window.location.href = 'http://localhost:8080/admin/listar-estoquistas'</script>";

    }

    @PostMapping("/remover-estoquista")
    public String removerEstoquista(
            @RequestParam(name = "id_usuario", required = true) String id_usuario,
            HttpSession session
    ) {
        if (!autenticar.porSessao(session, "Administrador")) {
            return "<script>window.location.href = 'http://localhost:8080/admin'</script>";
        }
        int id = Integer.parseInt(id_usuario);

        ResponseEntity<Usuario> usuarioResponse = usuarioService.removeEstoquista(id);

        return "<script>window.location.href = 'http://localhost:8080/admin/listar-estoquistas'</script>";

    }

    @GetMapping("/filtroNome/{nome}")
    public ModelAndView findByNome(@PathVariable String nome, HttpSession session) {

        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }
        List<Usuario> estoquistas = usuarioService.filtroNomeEstoquista(nome);

        return new ModelAndView("administrador/listaEstoquista").addObject("estoquistas", estoquistas);
    }

    @GetMapping("/filtroNome")
    public ModelAndView findByNomeVoid(HttpSession session) {

        if (!autenticar.porSessao(session, "Administrador")) {
            return new ModelAndView("redirect:/admin");
        }

        return new ModelAndView("redirect:/admin/listar-estoquistas");
    }

}
