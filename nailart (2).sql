-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 15 Des 2024 pada 19.37
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nailart`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `poster` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `description`, `poster`) VALUES
(1, 'Menicure', 'A manicure is a pampering treatment that beautifies and revitalizes your hands and nails. It involves cleaning, shaping, and polishing the nails, along with cuticle care and soothing hand massages. With various colors and styles available, manicures offer a chance to express personal style while promoting relaxation and nail health.', '\"C:\\Users\\Aspire 7\\Downloads\\menicure.png\"'),
(2, 'Nail Art 3D', 'Nail art is a creative way to decorate nails with unique designs, from simple patterns to intricate 3D embellishments. It’s a popular service for self-expression and style, attracting customers who want to personalize their look.', 'C:\\Users\\Aspire 7\\Downloads\\Nailart.png'),
(3, 'Nail Extension', 'Nail extensions are a beauty treatment that enhances the length and appearance of natural nails using materials like acrylic or gel. They can be customized in various shapes and designs, providing a durable and polished look. Ideal for those with short or weak nails, nail extensions offer a glamorous touch and last several weeks with proper care.', '\"C:\\Users\\Aspire 7\\Downloads\\nailextension.jpg\"'),
(311, 'Nail Extension', 'Nail extensions are a beauty treatment that enhances the length and appearance of natural nails using materials like acrylic or gel. They can be customized in various shapes and designs, providing a durable and polished look. Ideal for those with short or weak nails, nail extensions offer a glamorous touch and last several weeks with proper care.', '\"C:\\Users\\Aspire 7\\Downloads\\nailextension.jpg\"');

-- --------------------------------------------------------

--
-- Struktur dari tabel `location`
--

CREATE TABLE `location` (
  `id` int(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `branch location` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `location`
--

INSERT INTO `location` (`id`, `city`, `branch location`) VALUES
(1, 'Jakarta', 'Lechy Licious Senopati'),
(2, 'Jakarta', 'Lechy Licious Pondok Indah'),
(3, 'Jakarta', 'Lechy Licious Kemang'),
(4, 'Bekasi', 'Lechy Licious Galaxy'),
(5, 'Bandung', 'Lechy Licious Braga\r\n'),
(6, 'Jogja', 'Lechy Licious Malioboro'),
(370, 'Depok', 'Lechy Licious Margonda');

-- --------------------------------------------------------

--
-- Struktur dari tabel `login`
--

CREATE TABLE `login` (
  `id` int(3) NOT NULL,
  `email` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `password` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `role` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT 'admin'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `login`
--

INSERT INTO `login` (`id`, `email`, `password`, `role`) VALUES
(1, 'leann@gmail.com', 'leeee', 'Admin'),
(2, 'parhan@gmail.com', 'hannnn', 'Admin'),
(3, 'teci@gmail.com', 'ciiii', 'Admin'),
(270, 'aoll@gmail.com', 'ul', 'Admin');

-- --------------------------------------------------------

--
-- Struktur dari tabel `reservation`
--

CREATE TABLE `reservation` (
  `id` int(5) NOT NULL,
  `first_name` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `last_name` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `phone` varchar(13) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `email` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `workshop` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `reservation`
--

INSERT INTO `reservation` (`id`, `first_name`, `last_name`, `phone`, `email`, `workshop`) VALUES
(1, 'Techi', 'Kariska', '081298765432', 'sari@gmail.com', 'Menicure - [9:00 AM - 10:00 AM]'),
(2, 'Leann', 'Nataly', '081234587654', 'kenan@gmail.com', 'Menicure - [9:00 AM - 10:00 AM]'),
(171, 'diya', 'uak', '12345678', 'uak@gmail.com', 'Nail Art - [10:00 AM - 12:00 AM]');

-- --------------------------------------------------------

--
-- Struktur dari tabel `schedule`
--

CREATE TABLE `schedule` (
  `id` int(255) NOT NULL,
  `time` varchar(255) DEFAULT NULL,
  `services` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `schedule`
--

INSERT INTO `schedule` (`id`, `time`, `services`, `type`) VALUES
(1, '09:00 - 10:00', 'Menicure', NULL),
(2, '10:00 - 12:00', 'Nail Art 3D', NULL),
(3, '13:00 - 15:00', 'Nail Extension', NULL),
(4, '15:00 - 16:00', 'Menicure', NULL),
(5, '16:00 - 18:00', 'Nail Art 3D', NULL),
(6, '19:00 - 20:00', 'Nail Extension', NULL),
(380, '20:00 - 21:00', 'Menicure', NULL);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indeks untuk tabel `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=312;

--
-- AUTO_INCREMENT untuk tabel `location`
--
ALTER TABLE `location`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=371;

--
-- AUTO_INCREMENT untuk tabel `login`
--
ALTER TABLE `login`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=271;

--
-- AUTO_INCREMENT untuk tabel `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=172;

--
-- AUTO_INCREMENT untuk tabel `schedule`
--
ALTER TABLE `schedule`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=381;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
