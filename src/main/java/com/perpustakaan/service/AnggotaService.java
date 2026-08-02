package com.perpustakaan.service;

import com.perpustakaan.dto.request.AnggotaRequest;
import com.perpustakaan.dto.response.AnggotaResponse;
import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.entity.Anggota;
import com.perpustakaan.repository.AnggotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnggotaService {

    private final AnggotaRepository anggotaRepository;

    public AnggotaService(AnggotaRepository anggotaRepository) {
        this.anggotaRepository = anggotaRepository;
    }

    public List<AnggotaResponse> getAllAnggota() {
        return anggotaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AnggotaResponse getAnggotaById(Long id) {

        Anggota anggota = anggotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anggota tidak ditemukan"));

        return mapToResponse(anggota);
    }

    public AnggotaResponse saveAnggota(AnggotaRequest request) {

        Anggota anggota = new Anggota();

        anggota.setNimNis(request.getNimNis());
        anggota.setNama(request.getNama());
        anggota.setJenisKelamin(request.getJenisKelamin());
        anggota.setAlamat(request.getAlamat());
        anggota.setNoHp(request.getNoHp());
        anggota.setFoto(request.getFoto());
        anggota.setStatus(request.getStatus());

        anggota = anggotaRepository.save(anggota);

        return mapToResponse(anggota);
    }

    public AnggotaResponse updateAnggota(Long id, AnggotaRequest request) {

        Anggota anggota = anggotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anggota tidak ditemukan"));

        anggota.setNimNis(request.getNimNis());
        anggota.setNama(request.getNama());
        anggota.setJenisKelamin(request.getJenisKelamin());
        anggota.setAlamat(request.getAlamat());
        anggota.setNoHp(request.getNoHp());
        anggota.setFoto(request.getFoto());
        anggota.setStatus(request.getStatus());

        anggota = anggotaRepository.save(anggota);

        return mapToResponse(anggota);
    }

    public void deleteAnggota(Long id) {
        anggotaRepository.deleteById(id);
    }

    private AnggotaResponse mapToResponse(Anggota anggota) {
        return new AnggotaResponse(
                anggota.getId(),
                anggota.getNimNis(),
                anggota.getNama(),
                anggota.getJenisKelamin(),
                anggota.getAlamat(),
                anggota.getNoHp(),
                anggota.getFoto(),
                anggota.getStatus()
        );
    }
}