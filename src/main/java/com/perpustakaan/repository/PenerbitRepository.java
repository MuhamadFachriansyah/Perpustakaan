package com.perpustakaan.repository;

import com.perpustakaan.entity.Penerbit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenerbitRepository extends JpaRepository<Penerbit, Long> {
}