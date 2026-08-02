package com.perpustakaan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detail_peminjaman")
public class DetailPeminjaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "peminjaman_id")
    private Peminjaman peminjaman;

    @ManyToOne
    @JoinColumn(name = "buku_id")
    private Buku buku;

    private Integer jumlah;

    public DetailPeminjaman() {
    }

    public Long getId() {
        return id;
    }

    public Peminjaman getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
}