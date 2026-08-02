package com.perpustakaan.dto.response;

import java.time.LocalDateTime;

public class KategoriResponse {

    private Long id;
    private String namaKategori;
    private LocalDateTime createdAt;

    public KategoriResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}