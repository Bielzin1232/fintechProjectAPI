package com.fintechProject.fintechProject.repository;

import com.fintechProject.fintechProject.entity.ChavePix;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChavePixRepository extends JpaRepository<ChavePix, Long> {
    Optional<ChavePix> findByValor(String valor);
    int countByUsuarioId(Long usuarioId);
}
