package com.fintechProject.fintechProject.repository;

import com.fintechProject.fintechProject.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
