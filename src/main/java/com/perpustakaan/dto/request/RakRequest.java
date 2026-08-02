package com.perpustakaan.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RakRequest {

    @NotBlank(message = "Kode rak wajib diisi")
    private String kodeRak;

    private String lokasi;

    public RakRequest() {
    }

    public String getKodeRak() {
        return kodeRak;
    }

    public void setKodeRak(String kodeRak) {
        this.kodeRak = kodeRak;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}