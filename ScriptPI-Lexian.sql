create database dbLexian;

use dbLexian;
    
CREATE TABLE `produto` (
  `id_produto` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `qtde` int(11) NOT NULL,
  `marca` varchar(100) NOT NULL,
  `categoria` varchar(100) NOT NULL,                    
  `descricao` varchar(100) NOT NULL,
  `valor` decimal(15,2) not null,
  `ativo` boolean not NULL,
  PRIMARY KEY (`id_produto`)
);

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(250) NOT NULL,
  `cpf` varchar(13) NOT NULL UNIQUE,
  `perfil` varchar(25) NOT NULL,
  `status` boolean not NULL,
  /*`telefone` bigint(20) DEFAULT NULL,
  `sexo` varchar(10) NOT NULL,
  `data_nascimento` date DEFAULT NULL,*/
  PRIMARY KEY (`id_usuario`)
);

CREATE TABLE `endereco_Usuario` (
  `id_endereco` int  (250) NOT NULL AUTO_INCREMENT,  
  `rua` varchar (250) NOT NULL ,
  `numero` int(11) NOT NULL ,
  `cep` varchar (10) NOT NULL,
  `complemento` varchar (250),  
  `codigo` varchar (250),
  `id_usuario` int (250) NOT NULL ,
  PRIMARY KEY (`id_endereco`),
  CONSTRAINT `fk_id_endereco_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
);

CREATE TABLE `faq` (
  `id_faq` int(11) NOT NULL AUTO_INCREMENT,
  `id_produto` int(11) NOT NULL ,
  `pergunta` varchar (250),
  `resposta` varchar (250),  
  PRIMARY KEY (`id_faq`),
  KEY `fk_id_produto` (`id_produto`)/*,
  CONSTRAINT `fk_id_produto` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id_produto`)*/
);

/*CREATE TABLE `tb_imagem` (
  `id_imagem` int(11) NOT NULL AUTO_INCREMENT,
  `imagem_b64` varchar(65535),
  `id_produto` int (11) NOT NULL,
  `tamanho` varchar(2),
  `ordem` int(11) NOT NULL,
  PRIMARY KEY (`id_imagem`),
  KEY `fk_id_produto` (`id_produto`),
  CONSTRAINT `fk_id_produto` FOREIGN KEY (`id_produto`) REFERENCES `tb_produto` (`id_produto`)
);*/

