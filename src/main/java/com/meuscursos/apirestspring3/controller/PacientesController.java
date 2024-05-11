package com.meuscursos.apirestspring3.controller;

import com.meuscursos.apirestspring3.dto.pacientes.DadosAtualizarPaciente;
import com.meuscursos.apirestspring3.dto.pacientes.DadosPacientesResponse;
import com.meuscursos.apirestspring3.dto.pacientes.DadosCadastroPaciente;
import com.meuscursos.apirestspring3.dto.pacientes.DadosPacientesResponseDetails;
import com.meuscursos.apirestspring3.model.Pacientes;
import com.meuscursos.apirestspring3.repository.PacientesRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {

    @Autowired
    private PacientesRepository pacientesRepository;

    @GetMapping("/{id}")
    public ResponseEntity pacientesBusca (@PathVariable Long id) {
        var pacienteBuscado = pacientesRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosPacientesResponseDetails(pacienteBuscado));
    }


    @GetMapping //(Em lista paginada)
    public ResponseEntity<Page<DadosPacientesResponse>> listarPacientesPage(@PageableDefault(size = 10,sort = {"nome"}) Pageable paginacao) {

        var pacientesList = pacientesRepository.findAllByStatusTrue(paginacao).map(DadosPacientesResponse::new);

        return ResponseEntity.ok(pacientesList);
    }

    @PostMapping
    public ResponseEntity<DadosPacientesResponseDetails> cadastrar(@RequestBody @Valid DadosCadastroPaciente dadosPac, UriComponentsBuilder uriBuilder) {
        var pacienteCriado = new Pacientes(dadosPac);

        pacientesRepository.save(pacienteCriado);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(pacienteCriado.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosPacientesResponseDetails(pacienteCriado));
    }



    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarPaciente(@PathVariable Long id, @RequestBody @Valid DadosAtualizarPaciente dadosAtualizarPaciente){
        var pacienteRecuperado = pacientesRepository.getReferenceById(id);
        pacienteRecuperado.atualizarPaciente(dadosAtualizarPaciente);

        return ResponseEntity.ok(new DadosPacientesResponseDetails(pacienteRecuperado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarPaciente(@PathVariable Long id) {
        var pacienteRecuperado = pacientesRepository.getReferenceById(id);
        pacienteRecuperado.deletePaciente(id);

        return ResponseEntity.noContent().build();
    }


    // Variados

    @GetMapping("/all") //(Em lista)
    public List<DadosPacientesResponse> listarPacientes() {
        return pacientesRepository.findAll().stream().map(DadosPacientesResponse::new).toList();
    }



}
