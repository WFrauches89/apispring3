package com.meuscursos.apirestspring3.model;

import com.meuscursos.apirestspring3.dto.medico.DadosAtualizarMedico;
import com.meuscursos.apirestspring3.dto.medico.DadosAtualizarMedico2;
import com.meuscursos.apirestspring3.dto.medico.DadosCadastroMedico;
import com.meuscursos.apirestspring3.enums.Especialidade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= "id")
public class Medicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(unique=true)
    private String email;
    private String telefone;
    @Column(unique=true)
    private String crm;

    private Boolean status;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;


    public Medicos(DadosCadastroMedico dadosMed) {
        this.status = true;
        this.nome = dadosMed.nome();
        this.email = dadosMed.email();
        this.telefone = dadosMed.telefone();
        this.crm = dadosMed.crm();
        this.especialidade = dadosMed.especialidade();
        this.endereco = new Endereco(dadosMed.endereco());
    }

    public void atualizarCadastro() {

    }

    public void atualizarCadastro(DadosAtualizarMedico dadosAtualizarMedico) {
        if(dadosAtualizarMedico.id() != null){
            if(dadosAtualizarMedico.nome() != null){
                this.nome = dadosAtualizarMedico.nome();
            }
            if(dadosAtualizarMedico.telefone() != null){
                this.telefone = dadosAtualizarMedico.telefone();
            }
            if(dadosAtualizarMedico.endereco() != null){
                this.endereco.atualizarEndereco(dadosAtualizarMedico.endereco());
            }
        }


    }

    public void deleteLogico(Medicos medicoRecuperado) {
        this.status = false;
    }

    public void atualizarCadastroPorId(DadosAtualizarMedico2 dadosAtualizarMedico2) {

            if(dadosAtualizarMedico2.nome() != null){
                this.nome = dadosAtualizarMedico2.nome();
            }
            if(dadosAtualizarMedico2.telefone() != null){
                this.nome = dadosAtualizarMedico2.telefone();
            }
            if(dadosAtualizarMedico2.endereco() != null){
                this.endereco.atualizarEndereco(dadosAtualizarMedico2.endereco());
            }

    }
}
