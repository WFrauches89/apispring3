package com.meuscursos.apirestspring3.model;

import com.meuscursos.apirestspring3.dto.medico.DadosCadastroMedico;
import com.meuscursos.apirestspring3.enums.Especialidades;
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
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidades especialidades;

    @Embedded
    private Endereco endereco;


    public Medicos(DadosCadastroMedico dadosMed) {
        this.nome = dadosMed.nome();
        this.email = dadosMed.email();
        this.telefone = dadosMed.telefone();
        this.crm = dadosMed.crm();
        this.especialidades = dadosMed.especialidades();
        this.endereco = new Endereco(dadosMed.endereco());
    }
}
