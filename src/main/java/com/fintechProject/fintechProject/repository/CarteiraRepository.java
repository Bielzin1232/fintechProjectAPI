package com.fintechProject.fintechProject.repository;

import com.fintechProject.fintechProject.entity.Carteira;
import com.fintechProject.fintechProject.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    @Query("SELECT cp.usuario FROM ChavePix cp WHERE cp.valor = :chavePix")
    Usuario findUsuarioByChavePix(@Param("chavePix") String chavePix);

    @Query("SELECT c.usuario FROM Carteira c WHERE c.usuario.cpf = :cpf")
    Usuario findUsuarioByCpf(@Param("cpf") @NotBlank(message = "Você precisa informar o cpf do usuário") @CPF(message = "CPF inválido!") @Size(min = 11, max = 11, message = "O CPF deve conter 11 dígitos") String cpf);
}
