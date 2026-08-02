package com.perpustakaan.dto.response;

public class PenulisResponse {

    private Long id;
    private String namaPenulis;

    public PenulisResponse() {
    }

    public PenulisResponse(Long id, String namaPenulis) {
        this.id = id;
        this.namaPenulis = namaPenulis;
    }

    public Long getId() {
        return id;
    }

    public String getNamaPenulis() {
        return namaPenulis;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNamaPenulis(String namaPenulis) {
        this.namaPenulis = namaPenulis;
    }
}