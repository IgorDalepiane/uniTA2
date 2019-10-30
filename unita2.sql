-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: 30-Out-2019 às 02:12
-- Versão do servidor: 5.7.26
-- versão do PHP: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `unita2`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `aptidao`
--

DROP TABLE IF EXISTS `aptidao`;
CREATE TABLE IF NOT EXISTS `aptidao` (
  `idServ` int(11) NOT NULL,
  `idFunc` int(11) NOT NULL,
  PRIMARY KEY (`idServ`,`idFunc`),
  KEY `FK_Aptidao_2` (`idFunc`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cargo`
--

DROP TABLE IF EXISTS `cargo`;
CREATE TABLE IF NOT EXISTS `cargo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cargo` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `idPessoa` int(11) NOT NULL,
  `idEmp` int(11) NOT NULL,
  PRIMARY KEY (`idPessoa`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `empresa`
--

DROP TABLE IF EXISTS `empresa`;
CREATE TABLE IF NOT EXISTS `empresa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `senha` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

DROP TABLE IF EXISTS `endereco`;
CREATE TABLE IF NOT EXISTS `endereco` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `logradouro` varchar(30) NOT NULL,
  `numero` int(20) NOT NULL,
  `complemento` varchar(30) DEFAULT NULL,
  `bairro` varchar(30) NOT NULL,
  `cidade` varchar(30) NOT NULL,
  `estado` varchar(30) NOT NULL,
  `CEP` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `estoque`
--

DROP TABLE IF EXISTS `estoque`;
CREATE TABLE IF NOT EXISTS `estoque` (
  `idEmp` int(11) NOT NULL,
  `idProd` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  PRIMARY KEY (`idEmp`,`idProd`),
  KEY `FK_Estoque_2` (`idProd`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
CREATE TABLE IF NOT EXISTS `funcionario` (
  `idPessoa` int(5) NOT NULL,
  `valorHora` float NOT NULL,
  `idCargo` int(10) NOT NULL,
  `idEmp` int(11) NOT NULL,
  PRIMARY KEY (`idPessoa`),
  KEY `FK_Funcionario_3` (`idEmp`),
  KEY `idPessoa` (`idPessoa`),
  KEY `idCargo` (`idCargo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
CREATE TABLE IF NOT EXISTS `pessoa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `RG` varchar(30) NOT NULL,
  `CPF` varchar(30) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `idEnd` int(5) NOT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `residencial` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Pessoa_2` (`idEnd`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `produto`
--

DROP TABLE IF EXISTS `produto`;
CREATE TABLE IF NOT EXISTS `produto` (
  `nome` varchar(30) NOT NULL,
  `descricao` varchar(30) DEFAULT NULL,
  `preco` float NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `servico`
--

DROP TABLE IF EXISTS `servico`;
CREATE TABLE IF NOT EXISTS `servico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) DEFAULT NULL,
  `descricao` varchar(30) DEFAULT NULL,
  `preco` float DEFAULT NULL,
  `idEmp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Servico_2` (`idEmp`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `servicoprestado`
--

DROP TABLE IF EXISTS `servicoprestado`;
CREATE TABLE IF NOT EXISTS `servicoprestado` (
  `idServ` int(11) NOT NULL,
  `idFunc` int(11) NOT NULL,
  `idEmp` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `data` date NOT NULL,
  `horaInicio` time NOT NULL,
  `horaFim` time NOT NULL,
  PRIMARY KEY (`idEmp`,`idCliente`,`data`,`horaInicio`),
  KEY `FK_ServicoPrestado_2` (`idServ`),
  KEY `FK_ServicoPrestado_3` (`idFunc`),
  KEY `FK_ServicoPrestado_4` (`idEmp`),
  KEY `FK_ServicoPrestado_5` (`idCliente`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `serv_prod`
--

DROP TABLE IF EXISTS `serv_prod`;
CREATE TABLE IF NOT EXISTS `serv_prod` (
  `idProd` int(11) NOT NULL,
  `idEmp` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `data` date NOT NULL,
  `quant` int(10) NOT NULL,
  `horaInicio` time NOT NULL,
  PRIMARY KEY (`idProd`,`idEmp`,`idCliente`,`data`,`horaInicio`),
  KEY `FK_Serv_Prod_2` (`idEmp`,`idCliente`,`data`),
  KEY `horaInicio` (`horaInicio`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
