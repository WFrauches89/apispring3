package com.meuscursos.apirestspring3.controller;

import com.meuscursos.apirestspring3.dto.medico.DadosCadastroMedico;
import com.meuscursos.apirestspring3.model.Medicos;
import com.meuscursos.apirestspring3.repository.MedicosRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
