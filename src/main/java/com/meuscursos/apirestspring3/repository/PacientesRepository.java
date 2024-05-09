package com.meuscursos.apirestspring3.repository;

import com.meuscursos.apirestspring3.model.Pacientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacientesRepository extends JpaRepository<Pacientes, Long> {
}
