package com.perpustakaan.dto.response;

public class PenerbitResponse {

    private Long id;
    private String namaPenerbit;
    private String alamat;
    private String noHp;

    public PenerbitResponse() {
    }

    public PenerbitResponse(Long id, String namaPenerbit, String alamat, String noHp) {
        this.id = id;
        this.namaPenerbit = namaPenerbit;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    public Long getId() {
        return id;
    }

    public String getNamaPenerbit() {
        return namaPenerbit;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNamaPenerbit(String namaPenerbit) {
        this.namaPenerbit = namaPenerbit;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}