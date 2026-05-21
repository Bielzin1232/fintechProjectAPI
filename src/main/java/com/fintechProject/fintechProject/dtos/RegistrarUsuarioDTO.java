package com.fintechProject.fintechProject.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record RegistrarUsuarioDTO(

        @NotBlank(message = "O nome não pode ser em branco")
        @Size(max = 150,message = "Seu nome não pode exceder 150 caracteres")
        String nome,
        @CPF(message = "O cpf informado é inválido")
        @NotBlank(message = "O cpf não pode estar em branco!")
        @Size(min = 11,message = "O tamanho do cpf informado é inválido")
        String cpf,
        @Size(max = 255,message = "O email não podeo exceder o limite de 255 caracteres")
        @Email(message = "O email informado não é válido")
        @NotBlank(message = "O email não pode ser em branco!")
        String email,
        @Size(min = 11,message = "O número precisa ter 11 caracteres!")
        @NotBlank(message = "O número não pode ser em branco!")
        String numeroTelefone,
        @NotBlank(message = "A senha não pode ser em branco!")
        @Size(min = 8,max = 45,message = "A senha precisa estar entre 8 e 45 caracteres")
        String senha,
        @NotBlank(message = "A palavra passe não pode estar em branco!")
        @Size(min = 4,max = 6, message = "A palavra passe precisa estar entre 4 e 6 caracteres!")
        String palavraPasse



) {
}
