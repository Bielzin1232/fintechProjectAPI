package com.fintechProject.fintechProject.repository;

import com.fintechProject.fintechProject.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    boolean existsByChaveIdempotencia(String chaveIdempotencia);
}
