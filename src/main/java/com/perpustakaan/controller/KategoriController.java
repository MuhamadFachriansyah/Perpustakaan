package com.perpustakaan.controller;

import com.perpustakaan.dto.request.KategoriRequest;
import jakarta.validation.Valid;
import com.perpustakaan.dto.response.KategoriResponse;
import com.perpustakaan.service.KategoriService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategori")
public class KategoriController {

    private final KategoriService kategoriService;

    public KategoriController(KategoriService kategoriService) {
        this.kategoriService = kategoriService;
    }

    @GetMapping
    public List<KategoriResponse> getAllKategori() {
        return kategoriService.getAllKategori();
    }
    
    @GetMapping("/{id}")
    public KategoriResponse getKategoriById(@PathVariable Long id) {
        return kategoriService.getKategoriById(id);
    }

    @PostMapping
    public KategoriResponse tambahKategori(@Valid @RequestBody KategoriRequest request) {
        return kategoriService.saveKategori(request);
    }

    @PutMapping("/{id}")
    public KategoriResponse updateKategori(
            @PathVariable Long id,
            @Valid @RequestBody KategoriRequest request) {

        return kategoriService.updateKategori(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteKategori(@PathVariable Long id) {

        kategoriService.deleteKategori(id);

        return "Kategori berhasil dihapus";
    }

}