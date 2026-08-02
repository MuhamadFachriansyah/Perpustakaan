package com.perpustakaan.controller;

import com.perpustakaan.dto.request.RakRequest;
import jakarta.validation.Valid;
import com.perpustakaan.dto.response.RakResponse;
import com.perpustakaan.service.RakService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rak")
public class RakController {

    private final RakService rakService;

    public RakController(RakService rakService) {
        this.rakService = rakService;
    }

    @GetMapping
    public List<RakResponse> getAllRak() {
        return rakService.getAllRak();
    }

    @GetMapping("/{id}")
    public RakResponse getRakById(@PathVariable Long id) {
        return rakService.getRakById(id);
    }

    @PostMapping
    public RakResponse tambahRak(@Valid @RequestBody RakRequest request) {
        return rakService.saveRak(request);
    }

    @PutMapping("/{id}")
    public RakResponse updateRak(@PathVariable Long id,
                                 @Valid @RequestBody RakRequest request) {
        return rakService.updateRak(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteRak(@PathVariable Long id) {
        rakService.deleteRak(id);
        return "Data rak berhasil dihapus";
    }
}