package com.perpustakaan.service;

import com.perpustakaan.constant.StatusPeminjaman;
import com.perpustakaan.dto.request.DetailPeminjamanRequest;
import com.perpustakaan.dto.response.DetailPeminjamanResponse;
import com.perpustakaan.entity.Buku;
import com.perpustakaan.entity.DetailPeminjaman;
import com.perpustakaan.entity.Peminjaman;
import com.perpustakaan.exception.InsufficientStockException;
import com.perpustakaan.exception.InvalidOperationException;
import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.repository.BukuRepository;
import com.perpustakaan.repository.DetailPeminjamanRepository;
import com.perpustakaan.repository.PeminjamanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailPeminjamanService {

    private final DetailPeminjamanRepository detailPeminjamanRepository;
    private final PeminjamanRepository peminjamanRepository;
    private final BukuRepository bukuRepository;

    public DetailPeminjamanService(
            DetailPeminjamanRepository detailPeminjamanRepository,
            PeminjamanRepository peminjamanRepository,
            BukuRepository bukuRepository) {

        this.detailPeminjamanRepository = detailPeminjamanRepository;
        this.peminjamanRepository = peminjamanRepository;
        this.bukuRepository = bukuRepository;
    }

    public List<DetailPeminjamanResponse> getAllDetailPeminjaman() {

        return detailPeminjamanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DetailPeminjamanResponse getDetailPeminjamanById(Long id) {

        DetailPeminjaman detail = detailPeminjamanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detail peminjaman tidak ditemukan"));

        return mapToResponse(detail);
    }

    /**
     * Menyimpan detail peminjaman baru sekaligus mengurangi stok buku terkait.
     * Buku hanya boleh dipinjam jika stoknya mencukupi.
     */
    @Transactional
    public DetailPeminjamanResponse saveDetailPeminjaman(DetailPeminjamanRequest request) {

        Peminjaman peminjaman = peminjamanRepository.findById(request.getPeminjamanId())
                .orElseThrow(() -> new ResourceNotFoundException("Peminjaman tidak ditemukan"));

        if (StatusPeminjaman.DIKEMBALIKAN.equalsIgnoreCase(peminjaman.getStatus())) {
            throw new InvalidOperationException(
                    "Tidak bisa menambah detail buku, peminjaman ini sudah berstatus Dikembalikan");
        }

        Buku buku = bukuRepository.findById(request.getBukuId())
                .orElseThrow(() -> new ResourceNotFoundException("Buku tidak ditemukan"));

        kurangiStok(buku, request.getJumlah());

        DetailPeminjaman detail = new DetailPeminjaman();
        detail.setPeminjaman(peminjaman);
        detail.setBuku(buku);
        detail.setJumlah(request.getJumlah());

        detail = detailPeminjamanRepository.save(detail);

        return mapToResponse(detail);
    }

    /**
     * Update detail peminjaman. Stok buku lama dikembalikan terlebih dahulu,
     * baru stok buku baru (bisa jadi buku/jumlah berbeda) dikurangi lagi.
     * Tidak diperbolehkan jika peminjaman induknya sudah berstatus Dikembalikan.
     */
    @Transactional
    public DetailPeminjamanResponse updateDetailPeminjaman(Long id, DetailPeminjamanRequest request) {

        DetailPeminjaman detail = detailPeminjamanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detail peminjaman tidak ditemukan"));

        if (StatusPeminjaman.DIKEMBALIKAN.equalsIgnoreCase(detail.getPeminjaman().getStatus())) {
            throw new InvalidOperationException(
                    "Tidak bisa mengubah detail buku, peminjaman ini sudah berstatus Dikembalikan");
        }

        Peminjaman peminjaman = peminjamanRepository.findById(request.getPeminjamanId())
                .orElseThrow(() -> new ResourceNotFoundException("Peminjaman tidak ditemukan"));

        Buku bukuBaru = bukuRepository.findById(request.getBukuId())
                .orElseThrow(() -> new ResourceNotFoundException("Buku tidak ditemukan"));

        // kembalikan stok buku lama dulu
        kembalikanStok(detail.getBuku(), detail.getJumlah());

        // baru kurangi stok buku yang baru (bisa saja buku sama, jumlah beda)
        kurangiStok(bukuBaru, request.getJumlah());

        detail.setPeminjaman(peminjaman);
        detail.setBuku(bukuBaru);
        detail.setJumlah(request.getJumlah());

        detail = detailPeminjamanRepository.save(detail);

        return mapToResponse(detail);
    }

    /**
     * Menghapus detail peminjaman (misalnya salah input) dan mengembalikan stok bukunya,
     * kecuali peminjaman induknya sudah berstatus Dikembalikan (stok sudah dikembalikan lewat proses itu).
     */
    @Transactional
    public void deleteDetailPeminjaman(Long id) {

        DetailPeminjaman detail = detailPeminjamanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detail peminjaman tidak ditemukan"));

        if (StatusPeminjaman.DIKEMBALIKAN.equalsIgnoreCase(detail.getPeminjaman().getStatus())) {
            throw new InvalidOperationException(
                    "Tidak bisa menghapus detail buku, peminjaman ini sudah berstatus Dikembalikan");
        }

        kembalikanStok(detail.getBuku(), detail.getJumlah());

        detailPeminjamanRepository.deleteById(id);
    }

    private void kurangiStok(Buku buku, Integer jumlah) {
        int stokTersedia = buku.getStok() == null ? 0 : buku.getStok();

        if (stokTersedia < jumlah) {
            throw new InsufficientStockException(
                    "Stok buku \"" + buku.getJudul() + "\" tidak mencukupi. Tersedia: "
                            + stokTersedia + ", diminta: " + jumlah);
        }

        buku.setStok(stokTersedia - jumlah);
        bukuRepository.save(buku);
    }

    private void kembalikanStok(Buku buku, Integer jumlah) {
        int stokSaatIni = buku.getStok() == null ? 0 : buku.getStok();
        buku.setStok(stokSaatIni + jumlah);
        bukuRepository.save(buku);
    }

    private DetailPeminjamanResponse mapToResponse(DetailPeminjaman detail) {

        return new DetailPeminjamanResponse(
                detail.getId(),
                detail.getPeminjaman().getId(),
                detail.getBuku().getId(),
                detail.getBuku().getJudul(),
                detail.getJumlah()
        );
    }
}
