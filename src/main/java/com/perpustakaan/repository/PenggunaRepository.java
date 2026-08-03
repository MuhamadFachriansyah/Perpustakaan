package com.perpustakaan.repository;

import com.perpustakaan.entity.Pengguna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PenggunaRepository extends JpaRepository<Pengguna, Long> {

    Optional<Pengguna> findByUsername(String username);

    boolean existsByUsername(String username);
}