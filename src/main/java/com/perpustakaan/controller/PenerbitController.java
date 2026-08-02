package com.perpustakaan.controller;

import com.perpustakaan.dto.request.PenerbitRequest;
import jakarta.validation.Valid;
import com.perpustakaan.dto.response.PenerbitResponse;
import com.perpustakaan.service.PenerbitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penerbit")
public class PenerbitController {

    private final PenerbitService penerbitService;

    public PenerbitController(PenerbitService penerbitService) {
        this.penerbitService = penerbitService;
    }

    @GetMapping
    public List<PenerbitResponse> getAllPenerbit() {
        return penerbitService.getAllPenerbit();
    }

    @GetMapping("/{id}")
    public PenerbitResponse getPenerbitById(@PathVariable Long id) {
        return penerbitService.getPenerbitById(id);
    }

    @PostMapping
    public PenerbitResponse tambahPenerbit(@Valid @RequestBody PenerbitRequest request) {
        return penerbitService.savePenerbit(request);
    }

    @PutMapping("/{id}")
    public PenerbitResponse updatePenerbit(@PathVariable Long id,
                                           @Valid @RequestBody PenerbitRequest request) {
        return penerbitService.updatePenerbit(id, request);
    }

    @DeleteMapping("/{id}")
    public String deletePenerbit(@PathVariable Long id) {
        penerbitService.deletePenerbit(id);
        return "Data penerbit berhasil dihapus";
    }
}