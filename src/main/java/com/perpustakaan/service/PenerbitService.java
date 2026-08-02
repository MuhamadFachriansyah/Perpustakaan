package com.perpustakaan.service;

import com.perpustakaan.dto.request.PenerbitRequest;
import com.perpustakaan.dto.response.PenerbitResponse;
import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.entity.Penerbit;
import com.perpustakaan.repository.PenerbitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PenerbitService {

    private final PenerbitRepository penerbitRepository;

    public PenerbitService(PenerbitRepository penerbitRepository) {
        this.penerbitRepository = penerbitRepository;
    }

    public List<PenerbitResponse> getAllPenerbit() {
        return penerbitRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PenerbitResponse getPenerbitById(Long id) {

        Penerbit penerbit = penerbitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Penerbit tidak ditemukan"));

        return mapToResponse(penerbit);
    }

    public PenerbitResponse savePenerbit(PenerbitRequest request) {

        Penerbit penerbit = new Penerbit();

        penerbit.setNamaPenerbit(request.getNamaPenerbit());
        penerbit.setAlamat(request.getAlamat());
        penerbit.setNoHp(request.getNoHp());

        penerbit = penerbitRepository.save(penerbit);

        return mapToResponse(penerbit);
    }

    public PenerbitResponse updatePenerbit(Long id, PenerbitRequest request) {

        Penerbit penerbit = penerbitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Penerbit tidak ditemukan"));

        penerbit.setNamaPenerbit(request.getNamaPenerbit());
        penerbit.setAlamat(request.getAlamat());
        penerbit.setNoHp(request.getNoHp());

        penerbit = penerbitRepository.save(penerbit);

        return mapToResponse(penerbit);
    }

    public void deletePenerbit(Long id) {
        penerbitRepository.deleteById(id);
    }

    private PenerbitResponse mapToResponse(Penerbit penerbit) {
        return new PenerbitResponse(
                penerbit.getId(),
                penerbit.getNamaPenerbit(),
                penerbit.getAlamat(),
                penerbit.getNoHp()
        );
    }
}