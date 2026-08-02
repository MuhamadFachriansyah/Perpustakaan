package com.perpustakaan.dto.response;

public class DetailPeminjamanResponse {

    private Long id;
    private Long peminjamanId;
    private Long bukuId;
    private String judulBuku;
    private Integer jumlah;

    public DetailPeminjamanResponse() {
    }

    public DetailPeminjamanResponse(Long id,
                                    Long peminjamanId,
                                    Long bukuId,
                                    String judulBuku,
                                    Integer jumlah) {
        this.id = id;
        this.peminjamanId = peminjamanId;
        this.bukuId = bukuId;
        this.judulBuku = judulBuku;
        this.jumlah = jumlah;
    }

    public Long getId() {
        return id;
    }

    public Long getPeminjamanId() {
        return peminjamanId;
    }

    public Long getBukuId() {
        return bukuId;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPeminjamanId(Long peminjamanId) {
        this.peminjamanId = peminjamanId;
    }

    public void setBukuId(Long bukuId) {
        this.bukuId = bukuId;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
}