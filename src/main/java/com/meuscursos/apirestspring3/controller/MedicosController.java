package com.meuscursos.apirestspring3.controller;

import com.meuscursos.apirestspring3.dto.medico.*;
import com.meuscursos.apirestspring3.model.Medicos;
import com.meuscursos.apirestspring3.repository.MedicosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @Autowired
    private MedicosRepository medicosRepository;

    @GetMapping
    public ResponseEntity<Page<DadosMedicosResponse>> listarMedicosPage(@PageableDefault(size = 10,sort = "nome") Pageable paginacao) {
        var medicosList =medicosRepository.findAllByStatusTrue(paginacao).map(DadosMedicosResponse::new);
        return ResponseEntity.ok(medicosList);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarMedico(@PathVariable Long id){
        var medicoBuscado = medicosRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosMedicosResponseDetails(medicoBuscado));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosMedicosResponseDetails> cadastrar(@RequestBody @Valid DadosCadastroMedico dadosMed, UriComponentsBuilder uriBuilder) {
        var medicoCriado = new Medicos(dadosMed);
        medicosRepository.save(medicoCriado);
        var uri = uriBuilder.path("medicos/{id}").buildAndExpand(medicoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosMedicosResponseDetails(medicoCriado));
    }
    @PostMapping("/cadastrarList") //(Cadastro em lista)
    @Transactional
    public ResponseEntity<List<DadosMedicosResponseDetails>> cadastrarList(@RequestBody List<@Valid DadosCadastroMedico> dadosMedicos, UriComponentsBuilder uriBuilder) {
        List<DadosMedicosResponseDetails> responseDetailsList = new ArrayList<>();

        for (DadosCadastroMedico dadosMedico : dadosMedicos) {
            var medicoCriado = new Medicos(dadosMedico);
            medicosRepository.save(medicoCriado);

            var uri = uriBuilder.path("medicos/{id}").buildAndExpand(medicoCriado.getId()).toUri();
            responseDetailsList.add(new DadosMedicosResponseDetails(medicoCriado, uri));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDetailsList);
    }


    @PutMapping("/{id}") //Atualizar com a path variável ID e passando dados JSON
    @Transactional
    public ResponseEntity<DadosMedicosResponseDetails> atualizarMedicoId(@PathVariable Long id, @RequestBody @Valid DadosAtualizarMedico2 dados) {
        var medicoRecuperado = medicosRepository.getReferenceById(id);
        medicoRecuperado.atualizarCadastroPorId(dados);
        return ResponseEntity.ok(new DadosMedicosResponseDetails(medicoRecuperado));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteMedico(@PathVariable Long id){
        var medicoRecuperado = medicosRepository.getReferenceById(id);
        medicoRecuperado.deleteLogico(medicoRecuperado);
        return ResponseEntity.noContent().build();
    }






// EXEMPLOS Variados...

    @GetMapping("/all") //(Em lista)
    public List<DadosMedicosResponse> listarMedicos() {
        return medicosRepository.findAll().stream().map(DadosMedicosResponse::new).toList();
    }

    @GetMapping("/all/list") //(Em lista)
    public Page<DadosMedicosResponse> listarMedicosFiltrados(@PageableDefault(size = 50,sort = "id")Pageable paginacao) {
        return medicosRepository.findAllByStatusTrue(paginacao).map(DadosMedicosResponse::new);
    }

    @GetMapping("/filtrado") //(Em lista)
    public Page<DadosMedicosResponse> listarMedicosFiltrado(@PageableDefault(size = 10,sort = "nome") Pageable paginacao) {
        return medicosRepository.findAllByStatusTrue(paginacao).map(DadosMedicosResponse::new);
    }

    @GetMapping("/filtrado/model2") //(Em lista paginada)
    public ResponseEntity<Page<DadosMedicosResponse>> listarMedicosPageModel2(@PageableDefault(size = 10,sort = "nome") Pageable paginacao) {
        var medicosList =medicosRepository.findAll(paginacao).map(DadosMedicosResponse::new);

        return ResponseEntity.ok(medicosList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarMedico(@RequestBody @Valid DadosAtualizarMedico dadosAtualizarMedico) {
        var medicoRecuperado = medicosRepository.getReferenceById(dadosAtualizarMedico.id());
        medicoRecuperado.atualizarCadastro(dadosAtualizarMedico);
        return ResponseEntity.ok(new DadosMedicosResponseDetails(medicoRecuperado));
    }

}
