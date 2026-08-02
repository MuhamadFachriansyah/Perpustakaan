package com.perpustakaan.controller;

import com.perpustakaan.dto.request.AnggotaRequest;
import jakarta.validation.Valid;
import com.perpustakaan.dto.response.AnggotaResponse;
import com.perpustakaan.service.AnggotaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anggota")
public class AnggotaController {

    private final AnggotaService anggotaService;

    public AnggotaController(AnggotaService anggotaService) {
        this.anggotaService = anggotaService;
    }

    @GetMapping
    public List<AnggotaResponse> getAllAnggota() {
        return anggotaService.getAllAnggota();
    }

    @GetMapping("/{id}")
    public AnggotaResponse getAnggotaById(@PathVariable Long id) {
        return anggotaService.getAnggotaById(id);
    }

    @PostMapping
    public AnggotaResponse tambahAnggota(@Valid @RequestBody AnggotaRequest request) {
        return anggotaService.saveAnggota(request);
    }

    @PutMapping("/{id}")
    public AnggotaResponse updateAnggota(@PathVariable Long id,
                                         @Valid @RequestBody AnggotaRequest request) {
        return anggotaService.updateAnggota(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteAnggota(@PathVariable Long id) {
        anggotaService.deleteAnggota(id);
        return "Data anggota berhasil dihapus";
    }
}