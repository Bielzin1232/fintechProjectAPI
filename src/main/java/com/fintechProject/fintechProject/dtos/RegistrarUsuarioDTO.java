package com.fintechProject.fintechProject.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record RegistrarUsuarioDTO(

        @NotBlank(message = "nome.em.branco")
        @Size(max = 150,message = "{nome.limite}")
        String nome,
        @CPF(message = "{cpf.invalido}")
        @NotBlank(message = "{cpf.em.branco}")
        @Size(min = 11,message = "{cpf.tamanho.invalido}")
        String cpf,
        @Size(max = 255,message = "{email.tamanho.limite}")
        @Email(message = "{email.invalido}")
        @NotBlank(message = "{email.em.branco}")
        String email,
        @Size(min = 11,message = "{numero.tamanho.limite}")
        @NotBlank(message = "{numero.em.branco}")
        String numeroTelefone,
        @NotBlank(message = "{senha.em.branco}")
        @Size(min = 8,max = 45,message = "{senha.tamanho.limite}")
        String senha



) {
}
