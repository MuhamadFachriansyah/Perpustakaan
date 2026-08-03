package com.perpustakaan.service;

import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.entity.Kategori;
import com.perpustakaan.repository.KategoriRepository;
import org.springframework.stereotype.Service;
import com.perpustakaan.dto.request.KategoriRequest;
import com.perpustakaan.dto.response.KategoriResponse;

import java.util.List;

@Service
public class KategoriService {

    private final KategoriRepository kategoriRepository;

    public KategoriService(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    public List<KategoriResponse> getAllKategori() {

        return kategoriRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }
    public KategoriResponse saveKategori(KategoriRequest request) {

        Kategori kategori = new Kategori();

        kategori.setNamaKategori(request.getNamaKategori());

        kategori = kategoriRepository.save(kategori);

        return convertToResponse(kategori);
    }
    public KategoriResponse getKategoriById(Long id) {

        Kategori kategori = kategoriRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori tidak ditemukan"));

        return convertToResponse(kategori);
    }
    private KategoriResponse convertToResponse(Kategori kategori) {

        KategoriResponse response = new KategoriResponse();

        response.setId(kategori.getId());
        response.setNamaKategori(kategori.getNamaKategori());
        response.setCreatedAt(kategori.getCreatedAt());

        return response;
    }

    public KategoriResponse updateKategori(Long id, KategoriRequest request) {

        Kategori kategori = kategoriRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori tidak ditemukan"));

        kategori.setNamaKategori(request.getNamaKategori());

        kategori = kategoriRepository.save(kategori);

        return convertToResponse(kategori);
    }

    public void deleteKategori(Long id) {

        if (!kategoriRepository.existsById(id)) {
            throw new ResourceNotFoundException("Kategori tidak ditemukan");
        }

        kategoriRepository.deleteById(id);
    }

}