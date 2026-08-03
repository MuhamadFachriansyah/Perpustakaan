package com.perpustakaan.service;

import com.perpustakaan.dto.request.BukuRequest;
import com.perpustakaan.dto.response.BukuResponse;
import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.entity.Buku;
import com.perpustakaan.entity.Kategori;
import com.perpustakaan.entity.Penerbit;
import com.perpustakaan.entity.Penulis;
import com.perpustakaan.entity.Rak;
import com.perpustakaan.repository.BukuRepository;
import com.perpustakaan.repository.KategoriRepository;
import com.perpustakaan.repository.PenerbitRepository;
import com.perpustakaan.repository.PenulisRepository;
import com.perpustakaan.repository.RakRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BukuService {

    private final BukuRepository bukuRepository;
    private final KategoriRepository kategoriRepository;
    private final PenulisRepository penulisRepository;
    private final PenerbitRepository penerbitRepository;
    private final RakRepository rakRepository;

    public BukuService(
            BukuRepository bukuRepository,
            KategoriRepository kategoriRepository,
            PenulisRepository penulisRepository,
            PenerbitRepository penerbitRepository,
            RakRepository rakRepository) {

        this.bukuRepository = bukuRepository;
        this.kategoriRepository = kategoriRepository;
        this.penulisRepository = penulisRepository;
        this.penerbitRepository = penerbitRepository;
        this.rakRepository = rakRepository;
    }


    public List<BukuResponse> getAllBuku() {
        return bukuRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    public BukuResponse getBukuById(Long id) {

    Buku buku = bukuRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Buku tidak ditemukan"));

    return convertToResponse(buku);
}


    public BukuResponse saveBuku(BukuRequest request) {

        Kategori kategori = kategoriRepository.findById(request.getKategoriId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori tidak ditemukan"));

        Penulis penulis = penulisRepository.findById(request.getPenulisId())
                .orElseThrow(() -> new ResourceNotFoundException("Penulis tidak ditemukan"));

        Penerbit penerbit = penerbitRepository.findById(request.getPenerbitId())
                .orElseThrow(() -> new ResourceNotFoundException("Penerbit tidak ditemukan"));

        Rak rak = rakRepository.findById(request.getRakId())
                .orElseThrow(() -> new ResourceNotFoundException("Rak tidak ditemukan"));

        Buku buku = new Buku();

        buku.setIsbn(request.getIsbn());
        buku.setJudul(request.getJudul());
        buku.setKategori(kategori);
        buku.setPenulis(penulis);
        buku.setPenerbit(penerbit);
        buku.setRak(rak);
        buku.setTahunTerbit(request.getTahunTerbit());
        buku.setStok(request.getStok());
        buku.setCover(request.getCover());
        buku.setSinopsis(request.getSinopsis());

        buku = bukuRepository.save(buku);

        return convertToResponse(buku);
    }


    public BukuResponse updateBuku(Long id, BukuRequest request) {

        Buku buku = bukuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buku tidak ditemukan"));

        Kategori kategori = kategoriRepository.findById(request.getKategoriId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori tidak ditemukan"));

        Penulis penulis = penulisRepository.findById(request.getPenulisId())
                .orElseThrow(() -> new ResourceNotFoundException("Penulis tidak ditemukan"));

        Penerbit penerbit = penerbitRepository.findById(request.getPenerbitId())
                .orElseThrow(() -> new ResourceNotFoundException("Penerbit tidak ditemukan"));

        Rak rak = rakRepository.findById(request.getRakId())
                .orElseThrow(() -> new ResourceNotFoundException("Rak tidak ditemukan"));

        buku.setIsbn(request.getIsbn());
        buku.setJudul(request.getJudul());
        buku.setKategori(kategori);
        buku.setPenulis(penulis);
        buku.setPenerbit(penerbit);
        buku.setRak(rak);
        buku.setTahunTerbit(request.getTahunTerbit());
        buku.setStok(request.getStok());
        buku.setCover(request.getCover());
        buku.setSinopsis(request.getSinopsis());

        buku = bukuRepository.save(buku);

        return convertToResponse(buku);
    }


    public void deleteBuku(Long id) {

        if (!bukuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Buku tidak ditemukan");
        }

        bukuRepository.deleteById(id);
    }


    private BukuResponse convertToResponse(Buku buku) {

        BukuResponse response = new BukuResponse();

        response.setId(buku.getId());
        response.setIsbn(buku.getIsbn());
        response.setJudul(buku.getJudul());

        response.setKategori(buku.getKategori().getNamaKategori());
        response.setPenulis(buku.getPenulis().getNamaPenulis());
        response.setPenerbit(buku.getPenerbit().getNamaPenerbit());
        response.setRak(buku.getRak().getKodeRak());

        response.setTahunTerbit(buku.getTahunTerbit());
        response.setStok(buku.getStok());
        response.setCover(buku.getCover());
        response.setSinopsis(buku.getSinopsis());

        return response;
    }
}