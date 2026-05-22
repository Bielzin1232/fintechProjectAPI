package com.fintechProject.fintechProject.repository;

import com.fintechProject.fintechProject.entity.TransacaoPix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoPixRepository extends JpaRepository<TransacaoPix,Long> {

    boolean existsByChaveIdempotencia(String chaveIdempotencia);
}
