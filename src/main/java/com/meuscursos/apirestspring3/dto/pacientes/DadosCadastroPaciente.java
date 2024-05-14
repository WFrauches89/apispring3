package com.meuscursos.apirestspring3.dto.pacientes;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCadastroPaciente(
        @NotBlank(message = "{nome.obrigatorio}")
        String nome,
        @NotBlank @Email
        String email,
        @NotBlank @Pattern(regexp = "\\d{10,15}")
        String telefone,
        @CPF
        String cpf,
        @NotNull @Valid
        EnderecoDTO endereco) {
}

