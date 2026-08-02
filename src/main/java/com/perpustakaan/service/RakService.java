package com.perpustakaan.service;

import com.perpustakaan.dto.request.RakRequest;
import com.perpustakaan.dto.response.RakResponse;
import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.entity.Rak;
import com.perpustakaan.repository.RakRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RakService {

    private final RakRepository rakRepository;

    public RakService(RakRepository rakRepository) {
        this.rakRepository = rakRepository;
    }

    public List<RakResponse> getAllRak() {
        return rakRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RakResponse getRakById(Long id) {

        Rak rak = rakRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rak tidak ditemukan"));

        return mapToResponse(rak);
    }

    public RakResponse saveRak(RakRequest request) {

        Rak rak = new Rak();

        rak.setKodeRak(request.getKodeRak());
        rak.setLokasi(request.getLokasi());

        rak = rakRepository.save(rak);

        return mapToResponse(rak);
    }

    public RakResponse updateRak(Long id, RakRequest request) {

        Rak rak = rakRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rak tidak ditemukan"));

        rak.setKodeRak(request.getKodeRak());
        rak.setLokasi(request.getLokasi());

        rak = rakRepository.save(rak);

        return mapToResponse(rak);
    }

    public void deleteRak(Long id) {
        rakRepository.deleteById(id);
    }

    private RakResponse mapToResponse(Rak rak) {
        return new RakResponse(
                rak.getId(),
                rak.getKodeRak(),
                rak.getLokasi()
        );
    }
}