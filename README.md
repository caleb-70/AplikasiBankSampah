Aplikasi Bank Sampah
====================

Aplikasi desktop Java Swing untuk mencatat data setoran sampah warga. Data disimpan ke database MySQL sehingga data tetap ada walaupun aplikasi ditutup.

Fitur
-----
- Input nomor anggota, nama, jenis sampah, berat, dan saldo.
- Hitung saldo otomatis berdasarkan jenis sampah dan berat.
- Simpan data setoran ke MySQL.
- Tampilkan data dari MySQL ke tabel.
- Ubah data setoran yang dipilih.
- Hapus data setoran yang dipilih.
- Validasi input kosong dan berat yang bukan angka.

Setup Database MySQL
--------------------
1. Jalankan MySQL, misalnya dari XAMPP.
2. Buka phpMyAdmin.
3. Import file:
   `database/db_bank_sampah.sql`
4. Database yang dibuat:
   `db_bank_sampah`
5. Tabel yang dibuat:
   `setoran_sampah`

Setup NetBeans
--------------
1. Buka project di NetBeans.
2. Tambahkan library MySQL Connector/J ke project.
   - Klik kanan project.
   - Pilih Properties.
   - Pilih Libraries.
   - Klik Add JAR/Folder.
   - Pilih file `mysql-connector-j-*.jar`.
3. Pastikan konfigurasi koneksi di `src/koneksi/Koneksi.java` sesuai:
   - host: `localhost`
   - database: `db_bank_sampah`
   - user: `root`
   - password: kosong

Jika MySQL memakai password, ubah bagian ini di `Koneksi.java`:

```java
private static final String PASSWORD = "";
```

Menjalankan Aplikasi
--------------------
Jalankan class utama:

```text
src/aplikasibanksampah/AplikasiBankSampah.java
```
