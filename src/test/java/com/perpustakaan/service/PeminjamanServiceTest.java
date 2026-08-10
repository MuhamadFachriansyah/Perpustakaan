package com.perpustakaan.service;

import com.perpustakaan.constant.StatusPeminjaman;
import com.perpustakaan.dto.request.PeminjamanRequest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeminjamanServiceTest {

    @Mock
    private PeminjamanRepository peminjamanRepository;

    @Mock
    private AnggotaRepository anggotaRepository;

    @Mock
    private DetailPeminjamanRepository detailPeminjamanRepository;

    @Mock
    private BukuRepository bukuRepository;

    @InjectMocks
    private PeminjamanService peminjamanService;

    private Anggota anggota;
    private Peminjaman peminjaman;
    private Buku buku;

    @BeforeEach
    void setUp() {
        anggota = new Anggota();
        anggota.setNama("Budi Santoso");

        peminjaman = new Peminjaman();
        peminjaman.setAnggota(anggota);
        peminjaman.setStatus(StatusPeminjaman.DIPINJAM);

        buku = new Buku();
        buku.setJudul("Laskar Pelangi");
        buku.setStok(3); 
    }

    @Test
    void updatePeminjaman_statusJadiDikembalikan_stokHarusBertambah() {
        
        DetailPeminjaman detail = new DetailPeminjaman();
        detail.setBuku(buku);
        detail.setJumlah(2);

        PeminjamanRequest request = new PeminjamanRequest();
        request.setAnggotaId(1L);
        request.setTanggalPinjam(LocalDate.now());
        request.setBatasKembali(LocalDate.now().plusDays(7));
        request.setStatus(StatusPeminjaman.DIKEMBALIKAN);

        when(peminjamanRepository.findById(1L)).thenReturn(Optional.of(peminjaman));
        when(anggotaRepository.findById(1L)).thenReturn(Optional.of(anggota));
        when(detailPeminjamanRepository.findByPeminjamanId(1L)).thenReturn(List.of(detail));
        when(peminjamanRepository.save(any(Peminjaman.class))).thenAnswer(inv -> inv.getArgument(0));

        
        peminjamanService.updatePeminjaman(1L, request);

        
        assertEquals(5, buku.getStok());
        verify(bukuRepository).save(buku);
    }

    @Test
    void updatePeminjaman_dibukaLagiDenganStokCukup_stokHarusBerkurang() {
        
        peminjaman.setStatus(StatusPeminjaman.DIKEMBALIKAN);
        buku.setStok(5); 

        DetailPeminjaman detail = new DetailPeminjaman();
        detail.setBuku(buku);
        detail.setJumlah(2);

        PeminjamanRequest request = new PeminjamanRequest();
        request.setAnggotaId(1L);
        request.setTanggalPinjam(LocalDate.now());
        request.setBatasKembali(LocalDate.now().plusDays(7));
        request.setStatus(StatusPeminjaman.DIPINJAM);

        when(peminjamanRepository.findById(1L)).thenReturn(Optional.of(peminjaman));
        when(anggotaRepository.findById(1L)).thenReturn(Optional.of(anggota));
        when(detailPeminjamanRepository.findByPeminjamanId(1L)).thenReturn(List.of(detail));
        when(peminjamanRepository.save(any(Peminjaman.class))).thenAnswer(inv -> inv.getArgument(0));

        
        peminjamanService.updatePeminjaman(1L, request);

        
        assertEquals(3, buku.getStok());
    }

    @Test
    void updatePeminjaman_dibukaLagiTapiStokTidakCukup_harusLemparException() {
        
        peminjaman.setStatus(StatusPeminjaman.DIKEMBALIKAN);
        buku.setStok(1);

        DetailPeminjaman detail = new DetailPeminjaman();
        detail.setBuku(buku);
        detail.setJumlah(2);

        PeminjamanRequest request = new PeminjamanRequest();
        request.setAnggotaId(1L);
        request.setTanggalPinjam(LocalDate.now());
        request.setBatasKembali(LocalDate.now().plusDays(7));
        request.setStatus(StatusPeminjaman.DIPINJAM);

        when(peminjamanRepository.findById(1L)).thenReturn(Optional.of(peminjaman));
        when(anggotaRepository.findById(1L)).thenReturn(Optional.of(anggota));
        when(detailPeminjamanRepository.findByPeminjamanId(1L)).thenReturn(List.of(detail));

        
        assertThrows(InsufficientStockException.class,
                () -> peminjamanService.updatePeminjaman(1L, request));

        
        verify(peminjamanRepository, never()).save(any());
    }

    @Test
    void updatePeminjaman_peminjamanTidakDitemukan_harusLemparResourceNotFound() {
        PeminjamanRequest request = new PeminjamanRequest();
        request.setAnggotaId(1L);
        request.setStatus(StatusPeminjaman.DIPINJAM);

        when(peminjamanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> peminjamanService.updatePeminjaman(99L, request));
    }

    @Test
    void deletePeminjaman_tidakAdaDetail_harusBerhasil() {
        when(peminjamanRepository.findById(1L)).thenReturn(Optional.of(peminjaman));
        when(detailPeminjamanRepository.findByPeminjamanId(1L)).thenReturn(List.of());

        peminjamanService.deletePeminjaman(1L);

        verify(peminjamanRepository).deleteById(any());
    }

    @Test
    void deletePeminjaman_masihAdaDetail_harusDitolak() {
        DetailPeminjaman detail = new DetailPeminjaman();
        detail.setBuku(buku);
        detail.setJumlah(1);

        when(peminjamanRepository.findById(1L)).thenReturn(Optional.of(peminjaman));
        when(detailPeminjamanRepository.findByPeminjamanId(1L)).thenReturn(List.of(detail));

        InvalidOperationException ex = assertThrows(InvalidOperationException.class,
                () -> peminjamanService.deletePeminjaman(1L));
        assertTrue(ex.getMessage().contains("detail buku"));

        verify(peminjamanRepository, never()).deleteById(any());
    }
}