package com.fintechProject.fintechProject.repository;

import com.fintechProject.fintechProject.entity.Carteira;
import com.fintechProject.fintechProject.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    @Query("SELECT c.usuario FROM ChavePix cp JOIN cp.carteira c WHERE cp.valor = :chavePix")
    Usuario findByChavepix(@Param("chavePix") String chavePix);

}
