package com.perpustakaan.dto.request;

import jakarta.validation.constraints.NotBlank;

public class KategoriRequest {

    @NotBlank(message = "Nama kategori wajib diisi")
    private String namaKategori;

    public KategoriRequest() {
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}