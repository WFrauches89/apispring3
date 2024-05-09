package com.meuscursos.apirestspring3.controller;

import com.meuscursos.apirestspring3.dto.medico.DadosCadastroMedico;
import com.meuscursos.apirestspring3.dto.pacientes.DadosCadastroPaciente;
import com.meuscursos.apirestspring3.model.Pacientes;
import com.meuscursos.apirestspring3.repository.PacientesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {

    @Autowired
    private PacientesRepository pacientesRepository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosPac) {

        pacientesRepository.save(new Pacientes(dadosPac));

    }

}
