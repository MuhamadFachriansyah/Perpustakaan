package com.perpustakaan.repository;

import com.perpustakaan.entity.Buku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BukuRepository extends JpaRepository<Buku, Long> {

}