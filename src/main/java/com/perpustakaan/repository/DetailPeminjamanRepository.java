package com.perpustakaan.repository;

import com.perpustakaan.entity.DetailPeminjaman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailPeminjamanRepository extends JpaRepository<DetailPeminjaman, Long> {

    List<DetailPeminjaman> findByPeminjamanId(Long peminjamanId);
}