package com.perpustakaan.service;

import com.perpustakaan.constant.StatusPeminjaman;
import com.perpustakaan.dto.request.DetailPeminjamanRequest;
import com.perpustakaan.entity.Buku;
import com.perpustakaan.entity.DetailPeminjaman;
import com.perpustakaan.entity.Peminjaman;
import com.perpustakaan.exception.InsufficientStockException;
import com.perpustakaan.exception.InvalidOperationException;
import com.perpustakaan.exception.ResourceNotFoundException;
import com.perpustakaan.repository.BukuRepository;
import com.perpustakaan.repository.DetailPeminjamanRepository;
import com.perpustakaan.repository.PeminjamanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetailPeminjamanServiceTest {

    @Mock
    private DetailPeminjamanRepository detailPeminjamanRepository;

    @Mock
    private PeminjamanRepository peminjamanRepository;

    @Mock
    private BukuRepository bukuRepository;

    @InjectMocks
    private DetailPeminjamanService detailPeminjamanService;

    private Buku buku;
    private Peminjaman peminjaman;

    @BeforeEach
    void setUp() {
        buku = new Buku();
        buku.setJudul("Laskar Pelangi");
        buku.setStok(5);

        peminjaman = new Peminjaman();
        peminjaman.setStatus(StatusPeminjaman.DIPINJAM);
    }

    @Test
    void saveDetailPeminjaman_stokCukup_stokHarusBerkurang() {
        DetailPeminjamanRequest request = new DetailPeminjamanRequest();
        request.setPeminjamanId(1L);
        request.setBukuId(1L);
        request.setJumlah(2);

        when(peminjamanRepository.findById(1L)).thenReturn(Optional.of(peminjaman));
        when(bukuRepository.findById(1L)).thenReturn(Optional.of(buku));
        when(detailPeminjamanRepository.save(any(DetailPeminjaman.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        detailPeminjamanService.saveDetailPeminjaman(request);

        assertEquals(3, buku.getStok());
        verify(bukuRepository).save(buku);
    }

    @Test
    void saveDetailPeminjaman_stokTidakCukup_harusLemparException() {
        DetailPeminjamanRequest request = new DetailPeminjamanRequest();
        request.setPeminjamanId(1L);
        request.setBukuId(1L);
        request.setJumlah(100);

        when(peminjamanRepository.findById(1L)).thenReturn(Optional.of(peminjaman));
        when(bukuRepository.findById(1L)).thenReturn(Optional.of(buku));

        InsufficientStockException ex = assertThrows(InsufficientStockException.class,
                () -> detailPeminjamanService.saveDetailPeminjaman(request));
        assertTrue(ex.getMessage().contains("tidak mencukupi"));

        assertEquals(5, buku.getStok());
        verify(detailPeminjamanRepository, never()).save(any());
    }

    @Test
    void saveDetailPeminjaman_peminjamanSudahDikembalikan_harusDitolak() {
        peminjaman.setStatus(StatusPeminjaman.DIKEMBALIKAN);

        DetailPeminjamanRequest request = new DetailPeminjamanRequest();
        request.setPeminjamanId(1L);
        request.setBukuId(1L);
        request.setJumlah(1);

        when(peminjamanRepository.findById(1L)).thenReturn(Optional.of(peminjaman));

        InvalidOperationException ex = assertThrows(InvalidOperationException.class,
                () -> detailPeminjamanService.saveDetailPeminjaman(request));
        assertTrue(ex.getMessage().contains("Dikembalikan"));

        verify(bukuRepository, never()).findById(any());
    }

    @Test
    void saveDetailPeminjaman_peminjamanTidakDitemukan_harusLemparResourceNotFound() {
        DetailPeminjamanRequest request = new DetailPeminjamanRequest();
        request.setPeminjamanId(99L);
        request.setBukuId(1L);
        request.setJumlah(1);

        when(peminjamanRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> detailPeminjamanService.saveDetailPeminjaman(request));
    }

    @Test
    void deleteDetailPeminjaman_peminjamanMasihDipinjam_stokHarusKembali() {
        buku.setStok(3);

        DetailPeminjaman detail = new DetailPeminjaman();
        detail.setBuku(buku);
        detail.setPeminjaman(peminjaman);
        detail.setJumlah(2);

        when(detailPeminjamanRepository.findById(1L)).thenReturn(Optional.of(detail));

        detailPeminjamanService.deleteDetailPeminjaman(1L);

        assertEquals(5, buku.getStok());
        verify(detailPeminjamanRepository).deleteById(1L);
    }

    @Test
    void deleteDetailPeminjaman_peminjamanSudahDikembalikan_harusDitolak() {
        peminjaman.setStatus(StatusPeminjaman.DIKEMBALIKAN);

        DetailPeminjaman detail = new DetailPeminjaman();
        detail.setBuku(buku);
        detail.setPeminjaman(peminjaman);
        detail.setJumlah(2);

        when(detailPeminjamanRepository.findById(1L)).thenReturn(Optional.of(detail));

        InvalidOperationException ex = assertThrows(InvalidOperationException.class,
                () -> detailPeminjamanService.deleteDetailPeminjaman(1L));
        assertTrue(ex.getMessage().contains("Dikembalikan"));

        verify(detailPeminjamanRepository, never()).deleteById(any());
    }
}   