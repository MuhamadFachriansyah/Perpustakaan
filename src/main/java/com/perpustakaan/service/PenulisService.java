package com.perpustakaan.service;

import com.perpustakaan.dto.request.PenulisRequest;
import com.perpustakaan.dto.response.PenulisResponse;
import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.entity.Penulis;
import com.perpustakaan.repository.PenulisRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PenulisService {

    private final PenulisRepository penulisRepository;

    public PenulisService(PenulisRepository penulisRepository) {
        this.penulisRepository = penulisRepository;
    }

    public List<PenulisResponse> getAllPenulis() {
        return penulisRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PenulisResponse getPenulisById(Long id) {

        Penulis penulis = penulisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Penulis tidak ditemukan"));

        return mapToResponse(penulis);
    }

    public PenulisResponse savePenulis(PenulisRequest request) {

        Penulis penulis = new Penulis();
        penulis.setNamaPenulis(request.getNamaPenulis());

        penulis = penulisRepository.save(penulis);

        return mapToResponse(penulis);
    }

    public PenulisResponse updatePenulis(Long id, PenulisRequest request) {

        Penulis penulis = penulisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Penulis tidak ditemukan"));

        penulis.setNamaPenulis(request.getNamaPenulis());

        penulis = penulisRepository.save(penulis);

        return mapToResponse(penulis);
    }

    public void deletePenulis(Long id) {
        penulisRepository.deleteById(id);
    }

    private PenulisResponse mapToResponse(Penulis penulis) {
        return new PenulisResponse(
                penulis.getId(),
                penulis.getNamaPenulis()
        );
    }
}