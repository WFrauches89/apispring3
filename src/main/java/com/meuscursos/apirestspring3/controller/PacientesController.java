package com.meuscursos.apirestspring3.controller;

import com.meuscursos.apirestspring3.dto.pacientes.DadosPacientesResponse;
import com.meuscursos.apirestspring3.dto.pacientes.DadosCadastroPaciente;
import com.meuscursos.apirestspring3.model.Pacientes;
import com.meuscursos.apirestspring3.repository.PacientesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {

    @Autowired
    private PacientesRepository pacientesRepository;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosPac) {

        pacientesRepository.save(new Pacientes(dadosPac));

    }

    @GetMapping("/all") //(Em lista)
    public List<DadosPacientesResponse> listarPacientes() {
        return pacientesRepository.findAll().stream().map(DadosPacientesResponse::new).toList();
    }

    @GetMapping //(Em lista paginada)
    public Page<DadosPacientesResponse> listarPacientesPage(@PageableDefault(size = 10,sort = {"nome"}) Pageable paginacao) {
        return pacientesRepository.findAll(paginacao).map(DadosPacientesResponse::new);
    }

}
