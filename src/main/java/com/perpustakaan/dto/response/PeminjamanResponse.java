package com.perpustakaan.dto.response;

import java.time.LocalDate;

public class PeminjamanResponse {

    private Long id;
    private Long anggotaId;
    private String namaAnggota;
    private LocalDate tanggalPinjam;
    private LocalDate batasKembali;
    private String status;

    public PeminjamanResponse() {
    }

    public PeminjamanResponse(Long id,
                              Long anggotaId,
                              String namaAnggota,
                              LocalDate tanggalPinjam,
                              LocalDate batasKembali,
                              String status) {
        this.id = id;
        this.anggotaId = anggotaId;
        this.namaAnggota = namaAnggota;
        this.tanggalPinjam = tanggalPinjam;
        this.batasKembali = batasKembali;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getAnggotaId() {
        return anggotaId;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public LocalDate getBatasKembali() {
        return batasKembali;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnggotaId(Long anggotaId) {
        this.anggotaId = anggotaId;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public void setBatasKembali(LocalDate batasKembali) {
        this.batasKembali = batasKembali;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}