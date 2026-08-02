package com.perpustakaan.dto.response;

public class RakResponse {

    private Long id;
    private String kodeRak;
    private String lokasi;

    public RakResponse() {
    }

    public RakResponse(Long id, String kodeRak, String lokasi) {
        this.id = id;
        this.kodeRak = kodeRak;
        this.lokasi = lokasi;
    }

    public Long getId() {
        return id;
    }

    public String getKodeRak() {
        return kodeRak;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKodeRak(String kodeRak) {
        this.kodeRak = kodeRak;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}