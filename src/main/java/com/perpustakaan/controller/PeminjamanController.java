package com.perpustakaan.controller;

import com.perpustakaan.dto.request.PeminjamanRequest;
import jakarta.validation.Valid;
import com.perpustakaan.dto.response.PeminjamanResponse;
import com.perpustakaan.service.PeminjamanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peminjaman")
public class PeminjamanController {

    private final PeminjamanService peminjamanService;

    public PeminjamanController(PeminjamanService peminjamanService) {
        this.peminjamanService = peminjamanService;
    }

    @GetMapping
    public List<PeminjamanResponse> getAllPeminjaman() {
        return peminjamanService.getAllPeminjaman();
    }

    @GetMapping("/{id}")
    public PeminjamanResponse getPeminjamanById(@PathVariable Long id) {
        return peminjamanService.getPeminjamanById(id);
    }

    @PostMapping
    public PeminjamanResponse tambahPeminjaman(@Valid @RequestBody PeminjamanRequest request) {
        return peminjamanService.savePeminjaman(request);
    }

    @PutMapping("/{id}")
    public PeminjamanResponse updatePeminjaman(@PathVariable Long id,
                                               @Valid @RequestBody PeminjamanRequest request) {
        return peminjamanService.updatePeminjaman(id, request);
    }

    @DeleteMapping("/{id}")
    public String deletePeminjaman(@PathVariable Long id) {
        peminjamanService.deletePeminjaman(id);
        return "Data peminjaman berhasil dihapus";
    }
}