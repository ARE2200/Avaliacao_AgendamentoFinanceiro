package com.avaliacao.app.repository;

import com.avaliacao.app.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
}