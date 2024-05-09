package com.meuscursos.apirestspring3.dto.enderecos;

import com.meuscursos.apirestspring3.enums.Especialidades;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO(
        @NotBlank
        String logradouro,

        String numero,

        String complemento,
        @NotBlank
        String bairro,
        @NotBlank @Pattern(regexp = "//d{2}")
        String uf,
        @NotBlank @Pattern(regexp = "//d{8}")
        String cep,
        @NotBlank
        String cidade
        ) {
}
