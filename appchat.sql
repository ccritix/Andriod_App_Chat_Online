-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 28, 2020 at 02:10 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `appchat`
--

-- --------------------------------------------------------

--
-- Table structure for table `anh`
--

CREATE DATABASE appchat;

use appchat;

CREATE TABLE `anh` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `anh`
--

INSERT INTO `anh` (`id`, `user1`, `user2`, `message1`, `message2`, `time`) VALUES
(1, 'Anh', 'HieuThu4', '', 'helo', '15:58'),
(2, 'Anh', 'HieuThu4', 'hi', '', '16:06'),
(3, 'Anh', 'HieuThu4', 'what problem ?', '', '16:07'),
(4, 'Anh', 'HieuThu4', '', 'where are you from ?', '16:08'),
(5, 'Anh', 'HieuThu4', 'i\'m from VN', '', '16:08'),
(6, 'Anh', 'HieuThu4', '', 'oh', '20:05');

-- --------------------------------------------------------

--
-- Table structure for table `anna`
--

CREATE TABLE `anna` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `dang`
--

CREATE TABLE `dang` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `dinh`
--

CREATE TABLE `dinh` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `hieuthu4`
--

CREATE TABLE `hieuthu4` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hieuthu4`
--

INSERT INTO `hieuthu4` (`id`, `user1`, `user2`, `message1`, `message2`, `time`) VALUES
(1, 'HieuThu4', 'Anh', 'helo', '', '15:58'),
(2, 'HieuThu4', 'Anh', '', 'hi', '16:06'),
(3, 'HieuThu4', 'Anh', '', 'what problem ?', '16:07'),
(4, 'HieuThu4', 'Anh', 'where are you from ?', '', '16:08'),
(5, 'HieuThu4', 'Anh', '', 'i\'m from VN', '16:08'),
(6, 'HieuThu4', 'Jenni', 'alo', '', '20:04'),
(7, 'HieuThu4', 'Jame', 'hey', '', '20:04'),
(8, 'HieuThu4', 'Anh', 'oh', '', '20:05'),
(9, 'HieuThu4', 'Thanh', 'hi', '', '20:05'),
(10, 'HieuThu4', 'Jame', '', 'hello', '20:08'),
(11, 'HieuThu4', 'Jame', 'how old are you?', '', '20:08'),
(12, 'HieuThu4', 'Jame', 'where are you from?', '', '20:09');

-- --------------------------------------------------------

--
-- Table structure for table `jame`
--

CREATE TABLE `jame` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jame`
--

INSERT INTO `jame` (`id`, `user1`, `user2`, `message1`, `message2`, `time`) VALUES
(1, 'Jame', 'HieuThu4', '', 'hey', '20:04'),
(2, 'Jame', 'HieuThu4', 'hello', '', '20:08'),
(3, 'Jame', 'HieuThu4', '', 'how old are you?', '20:08'),
(4, 'Jame', 'HieuThu4', '', 'where are you from?', '20:09');

-- --------------------------------------------------------

--
-- Table structure for table `jenni`
--

CREATE TABLE `jenni` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jenni`
--

INSERT INTO `jenni` (`id`, `user1`, `user2`, `message1`, `message2`, `time`) VALUES
(1, 'Jenni', 'HieuThu4', '', 'alo', '20:04');

-- --------------------------------------------------------

--
-- Table structure for table `thanh`
--

CREATE TABLE `thanh` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `thanh`
--

INSERT INTO `thanh` (`id`, `user1`, `user2`, `message1`, `message2`, `time`) VALUES
(1, 'Thanh', 'HieuThu4', '', 'hi', '20:05');

-- --------------------------------------------------------

--
-- Table structure for table `thuong`
--

CREATE TABLE `thuong` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tram`
--

CREATE TABLE `tram` (
  `id` int(11) NOT NULL,
  `user1` varchar(50) DEFAULT NULL,
  `user2` varchar(50) DEFAULT NULL,
  `message1` varchar(200) DEFAULT NULL,
  `message2` varchar(200) DEFAULT NULL,
  `time` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `phone` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `phone`) VALUES
(21, 'Jenni', '123456', '0976242184'),
(22, 'HieuThu4', '123456', '0976274601'),
(23, 'Jame', '123456', '0928174821'),
(24, 'Anh', '123456', '0928174342'),
(25, 'Thanh', '123456', '0812437274'),
(26, 'Dang', '123456', '0883274284'),
(27, 'Anna', '123456', '0847537537'),
(28, 'Thuong', '123456', '0915873253'),
(29, 'Tram', '123456', '0926347651'),
(30, 'Dinh', '123456', '0922736473');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anh`
--
ALTER TABLE `anh`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `anna`
--
ALTER TABLE `anna`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dang`
--
ALTER TABLE `dang`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dinh`
--
ALTER TABLE `dinh`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `hieuthu4`
--
ALTER TABLE `hieuthu4`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jame`
--
ALTER TABLE `jame`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jenni`
--
ALTER TABLE `jenni`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `thanh`
--
ALTER TABLE `thanh`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `thuong`
--
ALTER TABLE `thuong`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tram`
--
ALTER TABLE `tram`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user` (`username`) USING HASH;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `anh`
--
ALTER TABLE `anh`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `anna`
--
ALTER TABLE `anna`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `dang`
--
ALTER TABLE `dang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `dinh`
--
ALTER TABLE `dinh`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `hieuthu4`
--
ALTER TABLE `hieuthu4`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `jame`
--
ALTER TABLE `jame`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `jenni`
--
ALTER TABLE `jenni`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `thanh`
--
ALTER TABLE `thanh`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `thuong`
--
ALTER TABLE `thuong`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tram`
--
ALTER TABLE `tram`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
