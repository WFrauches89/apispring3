package com.meuscursos.apirestspring3.controller;

import com.meuscursos.apirestspring3.dto.medico.DadosAtualizarMedico;
import com.meuscursos.apirestspring3.dto.medico.DadosAtualizarMedico2;
import com.meuscursos.apirestspring3.dto.medico.DadosMedicosResponse;
import com.meuscursos.apirestspring3.dto.medico.DadosCadastroMedico;
import com.meuscursos.apirestspring3.model.Medicos;
import com.meuscursos.apirestspring3.repository.MedicosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @Autowired
    private MedicosRepository medicosRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dadosMed) {
        medicosRepository.save(new Medicos(dadosMed));
    }
    @PostMapping("/cadastrarList") //(Cadastro em lista)
    @Transactional
    public ResponseEntity<String> cadastrarList(@RequestBody List<@Valid DadosCadastroMedico> dadosMedicos) {
        for (DadosCadastroMedico dadosMedico : dadosMedicos) {
            medicosRepository.save(new Medicos(dadosMedico));
        }
        return ResponseEntity.ok("Lista de médicos cadastrada com sucesso.");
    }

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

    @GetMapping //(Em lista paginada)
    public Page<DadosMedicosResponse> listarMedicosPage(@PageableDefault(size = 10,sort = "nome") Pageable paginacao) {
        return medicosRepository.findAll(paginacao).map(DadosMedicosResponse::new);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizarMedico(@RequestBody @Valid DadosAtualizarMedico dadosAtualizarMedico) {
        var medicoRecuperado = medicosRepository.getReferenceById(dadosAtualizarMedico.id());
        medicoRecuperado.atualizarCadastro(dadosAtualizarMedico);
        return ResponseEntity.ok("Informações atualizadas com sucesso.");
    }

    @PutMapping("/{id}") //Atualizar com a path variável ID e passando dados JSON
    @Transactional
    public ResponseEntity<String> atualizarMedicoId(@PathVariable Long id, @RequestBody @Valid DadosAtualizarMedico2 dados) {
        var medicoRecuperado = medicosRepository.getReferenceById(id);
        medicoRecuperado.atualizarCadastroPorId(dados);
        return ResponseEntity.ok("Informações atualizadas com sucesso.");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteMedico(@PathVariable Long id){
        var medicoRecuperado = medicosRepository.getReferenceById(id);
        medicoRecuperado.deleteLogico(medicoRecuperado);
        return ResponseEntity.ok("Delete lógico com sucesso.");
    }
}
