package com.perpustakaan.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PenerbitRequest {

    @NotBlank(message = "Nama penerbit wajib diisi")
    private String namaPenerbit;

    private String alamat;
    private String noHp;

    public PenerbitRequest() {
    }

    public String getNamaPenerbit() {
        return namaPenerbit;
    }

    public void setNamaPenerbit(String namaPenerbit) {
        this.namaPenerbit = namaPenerbit;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}