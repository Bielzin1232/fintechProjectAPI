package com.fintechProject.fintechProject.repository;

import com.fintechProject.fintechProject.entity.TransacaoPix;
import com.fintechProject.fintechProject.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoPixRepository extends JpaRepository<TransacaoPix,Long> {

    boolean existsByChaveIdempotencia(String chaveIdempotencia);
    Page<TransacaoPix> findByRemetenteUsuarioIdOrDestinatarioUsuarioId(Long remetenteUsuarioId, Long destinatarioUsuarioId, Pageable pageable);
}
