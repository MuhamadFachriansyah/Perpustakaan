package com.perpustakaan.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rak")
public class Rak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode_rak")
    private String kodeRak;

    private String lokasi;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Rak() {
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}