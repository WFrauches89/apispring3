package com.meuscursos.apirestspring3.repository;

import com.meuscursos.apirestspring3.model.Medicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicosRepository extends JpaRepository<Medicos, Long> {

}
