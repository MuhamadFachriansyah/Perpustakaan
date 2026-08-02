package com.perpustakaan.controller;

import com.perpustakaan.dto.request.DetailPeminjamanRequest;
import jakarta.validation.Valid;
import com.perpustakaan.dto.response.DetailPeminjamanResponse;
import com.perpustakaan.service.DetailPeminjamanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detail-peminjaman")
public class DetailPeminjamanController {

    private final DetailPeminjamanService detailPeminjamanService;

    public DetailPeminjamanController(DetailPeminjamanService detailPeminjamanService) {
        this.detailPeminjamanService = detailPeminjamanService;
    }

    @GetMapping
    public List<DetailPeminjamanResponse> getAllDetailPeminjaman() {
        return detailPeminjamanService.getAllDetailPeminjaman();
    }

    @GetMapping("/{id}")
    public DetailPeminjamanResponse getDetailPeminjamanById(@PathVariable Long id) {
        return detailPeminjamanService.getDetailPeminjamanById(id);
    }

    @PostMapping
    public DetailPeminjamanResponse tambahDetailPeminjaman(
            @Valid @RequestBody DetailPeminjamanRequest request) {

        return detailPeminjamanService.saveDetailPeminjaman(request);
    }

    @PutMapping("/{id}")
    public DetailPeminjamanResponse updateDetailPeminjaman(
            @PathVariable Long id,
            @Valid @RequestBody DetailPeminjamanRequest request) {

        return detailPeminjamanService.updateDetailPeminjaman(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteDetailPeminjaman(@PathVariable Long id) {

        detailPeminjamanService.deleteDetailPeminjaman(id);
        return "Detail peminjaman berhasil dihapus";
    }
}