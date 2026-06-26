CREATE DATABASE IF NOT EXISTS db_bank_sampah;

USE db_bank_sampah;

CREATE TABLE IF NOT EXISTS setoran_sampah (
    id_setoran INT AUTO_INCREMENT PRIMARY KEY,
    no_anggota VARCHAR(20) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    jenis_sampah VARCHAR(30) NOT NULL,
    berat DOUBLE NOT NULL,
    saldo DOUBLE NOT NULL
);
