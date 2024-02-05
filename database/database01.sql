-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 05/02/2024 às 06:15
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `database01`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `conta`
--

CREATE TABLE `conta` (
  `id` bigint(20) NOT NULL,
  `numero` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estrutura para tabela `conta_seq`
--

CREATE TABLE `conta_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `conta_seq`
--

INSERT INTO `conta_seq` VALUES(1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `transferencia`
--

CREATE TABLE `transferencia` (
  `id` bigint(20) NOT NULL,
  `data_agendamento` date DEFAULT NULL,
  `data_transferencia` date DEFAULT NULL,
  `taxa` decimal(38,2) DEFAULT NULL,
  `valor` decimal(38,2) DEFAULT NULL,
  `conta_destino_id` bigint(20) DEFAULT NULL,
  `conta_origem_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estrutura para tabela `transferencia_seq`
--

CREATE TABLE `transferencia_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `transferencia_seq`
--

INSERT INTO `transferencia_seq` VALUES(1);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `conta`
--
ALTER TABLE `conta`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `transferencia`
--
ALTER TABLE `transferencia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3vh2c90fn9yn7l4gbvrl32mif` (`conta_destino_id`),
  ADD KEY `FKt6v19jub8ij9eh1u98hpbiwbk` (`conta_origem_id`);

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `transferencia`
--
ALTER TABLE `transferencia`
  ADD CONSTRAINT `FK3vh2c90fn9yn7l4gbvrl32mif` FOREIGN KEY (`conta_destino_id`) REFERENCES `conta` (`id`),
  ADD CONSTRAINT `FKt6v19jub8ij9eh1u98hpbiwbk` FOREIGN KEY (`conta_origem_id`) REFERENCES `conta` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
