package com.fintechProject.fintechProject.repository;

import com.fintechProject.fintechProject.entity.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    boolean existsByChaveIdempotencia(String chaveIdempotencia);
    @Query("SELECT t FROM Transacao t WHERE t.remetente.usuario.id = :usuarioId OR t.destinatario.usuario.id = :usuarioId")
    Page<Transacao> buscarExtratoPorUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);
}
