package com.perpustakaan.controller;

import com.perpustakaan.dto.request.BukuRequest;
import jakarta.validation.Valid;
import com.perpustakaan.dto.response.BukuResponse;
import com.perpustakaan.service.BukuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buku")
public class BukuController {

    private final BukuService bukuService;

    public BukuController(BukuService bukuService) {
        this.bukuService = bukuService;
    }


    @GetMapping
    public List<BukuResponse> getAllBuku() {
        return bukuService.getAllBuku();
    }


    @GetMapping("/{id}")
    public BukuResponse getBukuById(@PathVariable Long id) {
        return bukuService.getBukuById(id);
    }

    @PostMapping
    public BukuResponse tambahBuku(@Valid @RequestBody BukuRequest request) {
        return bukuService.saveBuku(request);
    }


    @PutMapping("/{id}")
    public BukuResponse updateBuku(@PathVariable Long id,
                           @Valid @RequestBody BukuRequest request) {
        return bukuService.updateBuku(id, request);
    }


    @DeleteMapping("/{id}")
    public String deleteBuku(@PathVariable Long id) {
        bukuService.deleteBuku(id);
        return "Data buku berhasil dihapus";
    }
}