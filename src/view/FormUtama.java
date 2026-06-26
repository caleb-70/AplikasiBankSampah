package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import koneksi.Koneksi;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FormUtama extends JFrame {

    private JTextField txtNoAnggota;
    private JTextField txtNamaAnggota;
    private JTextField txtBerat;
    private JTextField txtSaldo;
    private JComboBox<String> cmbJenisSampah;
    private JTable tabelSetoran;
    private DefaultTableModel model;
    private DecimalFormat formatAngka = new DecimalFormat("#,###");

    public FormUtama() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Aplikasi Bank Sampah");
        setSize(760, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelAtas = new JPanel();
        panelAtas.setBackground(new Color(36, 93, 60));
        panelAtas.setLayout(null);

        JLabel judul = new JLabel("BANK SAMPAH");
        judul.setFont(new Font("Arial", Font.BOLD, 24));
        judul.setForeground(Color.WHITE);
        judul.setBounds(25, 15, 250, 30);
        panelAtas.add(judul);

        JLabel subJudul = new JLabel("Data Setoran Sampah Warga");
        subJudul.setForeground(Color.WHITE);
        subJudul.setBounds(27, 45, 250, 25);
        panelAtas.add(subJudul);

        panelAtas.setPreferredSize(new java.awt.Dimension(760, 85));
        add(panelAtas, BorderLayout.NORTH);

        JPanel panelIsi = new JPanel();
        panelIsi.setLayout(null);
        panelIsi.setBackground(new Color(246, 248, 244));

        JLabel lblNo = new JLabel("No. Anggota");
        lblNo.setBounds(25, 25, 110, 25);
        panelIsi.add(lblNo);

        txtNoAnggota = new JTextField();
        txtNoAnggota.setBounds(140, 25, 190, 28);
        panelIsi.add(txtNoAnggota);

        JLabel lblNama = new JLabel("Nama");
        lblNama.setBounds(25, 65, 110, 25);
        panelIsi.add(lblNama);

        txtNamaAnggota = new JTextField();
        txtNamaAnggota.setBounds(140, 65, 190, 28);
        panelIsi.add(txtNamaAnggota);

        JLabel lblJenis = new JLabel("Jenis Sampah");
        lblJenis.setBounds(25, 105, 110, 25);
        panelIsi.add(lblJenis);

        cmbJenisSampah = new JComboBox<>();
        cmbJenisSampah.addItem("Plastik");
        cmbJenisSampah.addItem("Kertas");
        cmbJenisSampah.addItem("Botol");
        cmbJenisSampah.addItem("Kardus");
        cmbJenisSampah.addItem("Kaleng");
        cmbJenisSampah.setBounds(140, 105, 190, 28);
        panelIsi.add(cmbJenisSampah);

        JLabel lblBerat = new JLabel("Berat (Kg)");
        lblBerat.setBounds(385, 25, 100, 25);
        panelIsi.add(lblBerat);

        txtBerat = new JTextField();
        txtBerat.setBounds(495, 25, 180, 28);
        panelIsi.add(txtBerat);

        JLabel lblSaldo = new JLabel("Saldo");
        lblSaldo.setBounds(385, 65, 100, 25);
        panelIsi.add(lblSaldo);

        txtSaldo = new JTextField();
        txtSaldo.setEditable(false);
        txtSaldo.setBounds(495, 65, 180, 28);
        panelIsi.add(txtSaldo);

        JButton btnHitung = new JButton("Hitung");
        btnHitung.setBounds(385, 105, 85, 32);
        panelIsi.add(btnHitung);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(485, 105, 90, 32);
        panelIsi.add(btnSimpan);

        JButton btnUbah = new JButton("Ubah");
        btnUbah.setBounds(590, 105, 85, 32);
        panelIsi.add(btnUbah);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBounds(25, 155, 90, 32);
        panelIsi.add(btnHapus);

        JButton btnKeluar = new JButton("Keluar");
        btnKeluar.setBounds(125, 155, 90, 32);
        panelIsi.add(btnKeluar);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("No. Anggota");
        model.addColumn("Nama");
        model.addColumn("Jenis Sampah");
        model.addColumn("Berat");
        model.addColumn("Saldo");

        tabelSetoran = new JTable(model);
        sembunyikanKolomId();
        JScrollPane scrollPane = new JScrollPane(tabelSetoran);
        scrollPane.setBounds(25, 205, 690, 185);
        panelIsi.add(scrollPane);

        add(panelIsi, BorderLayout.CENTER);
        setJMenuBar(buatMenu());

        btnHitung.addActionListener(e -> hitungSaldo());
        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnKeluar.addActionListener(e -> dispose());

        tabelSetoran.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabel();
            }
        });

        tampilData();
    }

    private JMenuBar buatMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuAplikasi = new JMenu("Aplikasi");
        JMenuItem menuBersih = new JMenuItem("Bersihkan Form");
        JMenuItem menuKeluar = new JMenuItem("Keluar");
        menuBersih.addActionListener(e -> bersihkan());
        menuKeluar.addActionListener(e -> dispose());
        menuAplikasi.add(menuBersih);
        menuAplikasi.add(menuKeluar);

        JMenu menuData = new JMenu("Data");
        JMenuItem menuSetoran = new JMenuItem("Setoran Sampah");
        menuSetoran.addActionListener(e -> JOptionPane.showMessageDialog(this, "Form setoran sudah aktif."));
        menuData.add(menuSetoran);

        JMenu menuBantuan = new JMenu("Bantuan");
        JMenuItem menuInfo = new JMenuItem("Tentang");
        menuInfo.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Aplikasi Bank Sampah\nData setoran: no anggota, nama, jenis sampah, berat, saldo."));
        menuBantuan.add(menuInfo);

        menuBar.add(menuAplikasi);
        menuBar.add(menuData);
        menuBar.add(menuBantuan);
        return menuBar;
    }

    private void hitungSaldo() {
        try {
            double berat = Double.parseDouble(txtBerat.getText());
            double harga = hargaSampah(cmbJenisSampah.getSelectedItem().toString());
            double saldo = berat * harga;
            txtSaldo.setText("Rp " + formatAngka.format(saldo));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Berat sampah harus diisi angka.");
            txtBerat.requestFocus();
        }
    }

    private double hargaSampah(String jenis) {
        if (jenis.equals("Plastik")) {
            return 2500;
        } else if (jenis.equals("Kertas")) {
            return 2000;
        } else if (jenis.equals("Botol")) {
            return 3000;
        } else if (jenis.equals("Kardus")) {
            return 2200;
        } else {
            return 1800;
        }
    }

    private boolean cekInput() {
        if (txtNoAnggota.getText().trim().isEmpty()
                || txtNamaAnggota.getText().trim().isEmpty()
                || txtBerat.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data masih ada yang kosong.");
            return false;
        }

        try {
            Double.parseDouble(txtBerat.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Berat sampah harus berupa angka.");
            return false;
        }
        return true;
    }

    private void tampilData() {
        model.setRowCount(0);
        String sql = "SELECT * FROM setoran_sampah ORDER BY id_setoran DESC";

        try (Connection conn = Koneksi.getKoneksi();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_setoran"),
                    rs.getString("no_anggota"),
                    rs.getString("nama"),
                    rs.getString("jenis_sampah"),
                    rs.getDouble("berat"),
                    "Rp " + formatAngka.format(rs.getDouble("saldo"))
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menampilkan data dari MySQL.\n"
                    + "Pastikan database db_bank_sampah sudah dibuat dan MySQL berjalan.\n\n"
                    + ex.getMessage());
        }
    }

    private void sembunyikanKolomId() {
        tabelSetoran.getColumnModel().getColumn(0).setMinWidth(0);
        tabelSetoran.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelSetoran.getColumnModel().getColumn(0).setWidth(0);
    }

    private double saldoAngka() {
        double berat = Double.parseDouble(txtBerat.getText());
        double harga = hargaSampah(cmbJenisSampah.getSelectedItem().toString());
        return berat * harga;
    }

    private void simpanData() {
        if (!cekInput()) {
            return;
        }

        String sql = "INSERT INTO setoran_sampah (no_anggota, nama, jenis_sampah, berat, saldo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getKoneksi();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            double saldo = saldoAngka();
            ps.setString(1, txtNoAnggota.getText());
            ps.setString(2, txtNamaAnggota.getText());
            ps.setString(3, cmbJenisSampah.getSelectedItem().toString());
            ps.setDouble(4, Double.parseDouble(txtBerat.getText()));
            ps.setDouble(5, saldo);
            ps.executeUpdate();

            txtSaldo.setText("Rp " + formatAngka.format(saldo));
            JOptionPane.showMessageDialog(this, "Data setoran berhasil disimpan ke MySQL.");
            tampilData();
            bersihkan();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data.\n" + ex.getMessage());
        }
    }

    private void ubahData() {
        int baris = tabelSetoran.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang mau diubah dulu.");
            return;
        }
        if (!cekInput()) {
            return;
        }

        String sql = "UPDATE setoran_sampah SET no_anggota=?, nama=?, jenis_sampah=?, berat=?, saldo=? WHERE id_setoran=?";
        try (Connection conn = Koneksi.getKoneksi();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            double saldo = saldoAngka();
            int idSetoran = Integer.parseInt(model.getValueAt(baris, 0).toString());
            ps.setString(1, txtNoAnggota.getText());
            ps.setString(2, txtNamaAnggota.getText());
            ps.setString(3, cmbJenisSampah.getSelectedItem().toString());
            ps.setDouble(4, Double.parseDouble(txtBerat.getText()));
            ps.setDouble(5, saldo);
            ps.setInt(6, idSetoran);
            ps.executeUpdate();

            txtSaldo.setText("Rp " + formatAngka.format(saldo));
            JOptionPane.showMessageDialog(this, "Data berhasil diubah di MySQL.");
            tampilData();
            bersihkan();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data.\n" + ex.getMessage());
        }
    }

    private void hapusData() {
        int baris = tabelSetoran.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus dulu.");
            return;
        }

        int jawab = JOptionPane.showConfirmDialog(this, "Hapus data ini?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
        if (jawab == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM setoran_sampah WHERE id_setoran=?";
            try (Connection conn = Koneksi.getKoneksi();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                int idSetoran = Integer.parseInt(model.getValueAt(baris, 0).toString());
                ps.setInt(1, idSetoran);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus dari MySQL.");
                tampilData();
                bersihkan();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.\n" + ex.getMessage());
            }
        }
    }

    private void isiFormDariTabel() {
        int baris = tabelSetoran.getSelectedRow();
        if (baris >= 0) {
            txtNoAnggota.setText(model.getValueAt(baris, 1).toString());
            txtNamaAnggota.setText(model.getValueAt(baris, 2).toString());
            cmbJenisSampah.setSelectedItem(model.getValueAt(baris, 3).toString());
            txtBerat.setText(model.getValueAt(baris, 4).toString());
            txtSaldo.setText(model.getValueAt(baris, 5).toString());
        }
    }

    private void bersihkan() {
        txtNoAnggota.setText("");
        txtNamaAnggota.setText("");
        txtBerat.setText("");
        txtSaldo.setText("");
        cmbJenisSampah.setSelectedIndex(0);
        tabelSetoran.clearSelection();
        txtNoAnggota.requestFocus();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FormUtama().setVisible(true));
    }
}
