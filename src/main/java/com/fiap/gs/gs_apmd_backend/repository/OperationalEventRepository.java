package com.fiap.gs.gs_apmd_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.gs.gs_apmd_backend.model.OperationalEvent;

@Repository
public interface OperationalEventRepository extends JpaRepository<OperationalEvent, Long> {
}