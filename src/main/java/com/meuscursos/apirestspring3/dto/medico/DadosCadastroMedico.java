package com.meuscursos.apirestspring3.dto.medico;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;
import com.meuscursos.apirestspring3.enums.Especialidades;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(

        @NotBlank
        String nome,
        @NotBlank @Email
        String email,
        @NotBlank @Pattern(regexp = "//d{15}")
        String telefone,
        @NotBlank @Pattern(regexp = "//d{4,7}")
        String crm,
        @NotNull
        Especialidades especialidades,
        @NotNull @Valid
        EnderecoDTO endereco ) {
}
