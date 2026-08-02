package com.perpustakaan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PeminjamanRequest {

    @NotNull(message = "Anggota wajib dipilih")
    private Long anggotaId;

    @NotNull(message = "Tanggal pinjam wajib diisi")
    private LocalDate tanggalPinjam;

    @NotNull(message = "Batas kembali wajib diisi")
    private LocalDate batasKembali;

    @NotBlank(message = "Status wajib diisi")
    private String status;

    public PeminjamanRequest() {
    }

    public Long getAnggotaId() {
        return anggotaId;
    }

    public void setAnggotaId(Long anggotaId) {
        this.anggotaId = anggotaId;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public LocalDate getBatasKembali() {
        return batasKembali;
    }

    public void setBatasKembali(LocalDate batasKembali) {
        this.batasKembali = batasKembali;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}