package com.perpustakaan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class BukuRequest {

    @NotBlank(message = "ISBN wajib diisi")
    private String isbn;

    @NotBlank(message = "Judul wajib diisi")
    private String judul;

    @NotNull(message = "Kategori wajib dipilih")
    private Long kategoriId;

    @NotNull(message = "Penulis wajib dipilih")
    private Long penulisId;

    @NotNull(message = "Penerbit wajib dipilih")
    private Long penerbitId;

    @NotNull(message = "Rak wajib dipilih")
    private Long rakId;

    private Integer tahunTerbit;

    @NotNull(message = "Stok wajib diisi")
    @PositiveOrZero(message = "Stok tidak boleh negatif")
    private Integer stok;

    private String cover;
    private String sinopsis;

    public BukuRequest() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Long kategoriId) {
        this.kategoriId = kategoriId;
    }

    public Long getPenulisId() {
        return penulisId;
    }

    public void setPenulisId(Long penulisId) {
        this.penulisId = penulisId;
    }

    public Long getPenerbitId() {
        return penerbitId;
    }

    public void setPenerbitId(Long penerbitId) {
        this.penerbitId = penerbitId;
    }

    public Long getRakId() {
        return rakId;
    }

    public void setRakId(Long rakId) {
        this.rakId = rakId;
    }

    public Integer getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(Integer tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}