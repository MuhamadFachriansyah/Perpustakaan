package com.perpustakaan.service;

import com.perpustakaan.constant.StatusPeminjaman;
import com.perpustakaan.dto.request.PeminjamanRequest;
import com.perpustakaan.dto.response.PeminjamanResponse;
import com.perpustakaan.entity.Anggota;
import com.perpustakaan.entity.Buku;
import com.perpustakaan.entity.DetailPeminjaman;
import com.perpustakaan.entity.Peminjaman;
import com.perpustakaan.exception.InsufficientStockException;
import com.perpustakaan.exception.InvalidOperationException;
import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.repository.AnggotaRepository;
import com.perpustakaan.repository.BukuRepository;
import com.perpustakaan.repository.DetailPeminjamanRepository;
import com.perpustakaan.repository.PeminjamanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeminjamanService {

    private final PeminjamanRepository peminjamanRepository;
    private final AnggotaRepository anggotaRepository;
    private final DetailPeminjamanRepository detailPeminjamanRepository;
    private final BukuRepository bukuRepository;

    public PeminjamanService(PeminjamanRepository peminjamanRepository,
                             AnggotaRepository anggotaRepository,
                             DetailPeminjamanRepository detailPeminjamanRepository,
                             BukuRepository bukuRepository) {
        this.peminjamanRepository = peminjamanRepository;
        this.anggotaRepository = anggotaRepository;
        this.detailPeminjamanRepository = detailPeminjamanRepository;
        this.bukuRepository = bukuRepository;
    }

    public List<PeminjamanResponse> getAllPeminjaman() {
        return peminjamanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PeminjamanResponse getPeminjamanById(Long id) {

        Peminjaman peminjaman = peminjamanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data peminjaman tidak ditemukan"));

        return mapToResponse(peminjaman);
    }

    public PeminjamanResponse savePeminjaman(PeminjamanRequest request) {

        Anggota anggota = anggotaRepository.findById(request.getAnggotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Anggota tidak ditemukan"));

        Peminjaman peminjaman = new Peminjaman();

        peminjaman.setAnggota(anggota);
        peminjaman.setTanggalPinjam(request.getTanggalPinjam());
        peminjaman.setBatasKembali(request.getBatasKembali());
        peminjaman.setStatus(request.getStatus());

        peminjaman = peminjamanRepository.save(peminjaman);

        return mapToResponse(peminjaman);
    }

    /**
     * Update peminjaman. Jika status berubah dari selain "Dikembalikan" menjadi "Dikembalikan",
     * stok seluruh buku pada detail peminjaman ini otomatis dikembalikan.
     * Jika sebaliknya (dibuka lagi dari "Dikembalikan"), stok dikurangi lagi (dengan pengecekan ketersediaan).
     */
    @Transactional
    public PeminjamanResponse updatePeminjaman(Long id, PeminjamanRequest request) {

        Peminjaman peminjaman = peminjamanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data peminjaman tidak ditemukan"));

        Anggota anggota = anggotaRepository.findById(request.getAnggotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Anggota tidak ditemukan"));

        String statusLama = peminjaman.getStatus();
        String statusBaru = request.getStatus();

        boolean jadiDikembalikan = !StatusPeminjaman.DIKEMBALIKAN.equalsIgnoreCase(statusLama)
                && StatusPeminjaman.DIKEMBALIKAN.equalsIgnoreCase(statusBaru);

        boolean dibukaLagi = StatusPeminjaman.DIKEMBALIKAN.equalsIgnoreCase(statusLama)
                && !StatusPeminjaman.DIKEMBALIKAN.equalsIgnoreCase(statusBaru);

        List<DetailPeminjaman> details = detailPeminjamanRepository.findByPeminjamanId(id);

        if (jadiDikembalikan) {
            for (DetailPeminjaman detail : details) {
                Buku buku = detail.getBuku();
                int stokSaatIni = buku.getStok() == null ? 0 : buku.getStok();
                buku.setStok(stokSaatIni + detail.getJumlah());
                bukuRepository.save(buku);
            }
        } else if (dibukaLagi) {
            for (DetailPeminjaman detail : details) {
                Buku buku = detail.getBuku();
                int stokSaatIni = buku.getStok() == null ? 0 : buku.getStok();

                if (stokSaatIni < detail.getJumlah()) {
                    throw new InsufficientStockException(
                            "Tidak bisa membuka kembali peminjaman ini, stok buku \""
                                    + buku.getJudul() + "\" sudah tidak mencukupi");
                }

                buku.setStok(stokSaatIni - detail.getJumlah());
                bukuRepository.save(buku);
            }
        }

        peminjaman.setAnggota(anggota);
        peminjaman.setTanggalPinjam(request.getTanggalPinjam());
        peminjaman.setBatasKembali(request.getBatasKembali());
        peminjaman.setStatus(statusBaru);

        peminjaman = peminjamanRepository.save(peminjaman);

        return mapToResponse(peminjaman);
    }

    /**
     * Menghapus peminjaman hanya diperbolehkan jika belum ada detail buku yang tercatat,
     * supaya tidak ada stok buku yang "hilang" karena catatannya terhapus begitu saja.
     */
    @Transactional
    public void deletePeminjaman(Long id) {

        Peminjaman peminjaman = peminjamanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data peminjaman tidak ditemukan"));

        List<DetailPeminjaman> details = detailPeminjamanRepository.findByPeminjamanId(id);

        if (!details.isEmpty()) {
            throw new InvalidOperationException(
                    "Tidak bisa menghapus peminjaman yang masih memiliki detail buku. "
                            + "Hapus detail peminjamannya terlebih dahulu.");
        }

        peminjamanRepository.deleteById(peminjaman.getId());
    }

    private PeminjamanResponse mapToResponse(Peminjaman peminjaman) {

        return new PeminjamanResponse(
                peminjaman.getId(),
                peminjaman.getAnggota().getId(),
                peminjaman.getAnggota().getNama(),
                peminjaman.getTanggalPinjam(),
                peminjaman.getBatasKembali(),
                peminjaman.getStatus()
        );
    }
}
