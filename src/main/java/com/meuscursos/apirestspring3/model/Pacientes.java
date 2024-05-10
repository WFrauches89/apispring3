package com.meuscursos.apirestspring3.model;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;
import com.meuscursos.apirestspring3.dto.pacientes.DadosAtualizarPaciente;
import com.meuscursos.apirestspring3.dto.pacientes.DadosCadastroPaciente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pacientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Boolean status;

    private String cpf;
    @Embedded
    private Endereco endereco;


    public Pacientes(DadosCadastroPaciente dadosPac) {
        this.status = true;
        this.nome = dadosPac.nome();
        this.email = dadosPac.email();
        this.telefone = dadosPac.telefone();
        this.cpf = dadosPac.cpf();
        this.endereco = new Endereco(dadosPac.endereco());
    }

    public void atualizarPaciente(DadosAtualizarPaciente dadosAtualizarPaciente) {
        if(dadosAtualizarPaciente.nome() != null){
            this.nome = dadosAtualizarPaciente.nome();
        }
        if(dadosAtualizarPaciente.telefone() != null){
            this.nome = dadosAtualizarPaciente.telefone();
        }
        if(dadosAtualizarPaciente.endereco() != null){
            this.endereco.atualizarEndereco(dadosAtualizarPaciente.endereco());
        }

    }

    public void deletePaciente(Long id) {
        this.status = false;
    }
}