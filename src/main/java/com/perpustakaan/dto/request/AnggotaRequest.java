package com.perpustakaan.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AnggotaRequest {

    @NotBlank(message = "NIM/NIS wajib diisi")
    private String nimNis;

    @NotBlank(message = "Nama wajib diisi")
    private String nama;

    @NotBlank(message = "Jenis kelamin wajib diisi")
    private String jenisKelamin;

    private String alamat;
    private String noHp;
    private String foto;

    @NotBlank(message = "Status wajib diisi")
    private String status;

    public AnggotaRequest() {
    }

    public String getNimNis() {
        return nimNis;
    }

    public void setNimNis(String nimNis) {
        this.nimNis = nimNis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}