package com.perpustakaan.controller;

import com.perpustakaan.dto.request.PenulisRequest;
import jakarta.validation.Valid;
import com.perpustakaan.dto.response.PenulisResponse;
import com.perpustakaan.service.PenulisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penulis")
public class PenulisController {

    private final PenulisService penulisService;

    public PenulisController(PenulisService penulisService) {
        this.penulisService = penulisService;
    }

    @GetMapping
    public List<PenulisResponse> getAllPenulis() {
        return penulisService.getAllPenulis();
    }

    @GetMapping("/{id}")
    public PenulisResponse getPenulisById(@PathVariable Long id) {
        return penulisService.getPenulisById(id);
    }

    @PostMapping
    public PenulisResponse tambahPenulis(@Valid @RequestBody PenulisRequest request) {
        return penulisService.savePenulis(request);
    }

    @PutMapping("/{id}")
    public PenulisResponse updatePenulis(@PathVariable Long id,
                                         @Valid @RequestBody PenulisRequest request) {
        return penulisService.updatePenulis(id, request);
    }

    @DeleteMapping("/{id}")
    public String deletePenulis(@PathVariable Long id) {
        penulisService.deletePenulis(id);
        return "Data penulis berhasil dihapus";
    }
}