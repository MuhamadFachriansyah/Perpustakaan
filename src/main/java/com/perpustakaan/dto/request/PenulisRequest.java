package com.perpustakaan.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PenulisRequest {

    @NotBlank(message = "Nama penulis wajib diisi")
    private String namaPenulis;

    public PenulisRequest() {
    }

    public String getNamaPenulis() {
        return namaPenulis;
    }

    public void setNamaPenulis(String namaPenulis) {
        this.namaPenulis = namaPenulis;
    }
}