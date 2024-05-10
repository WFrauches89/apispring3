package com.meuscursos.apirestspring3.repository;

import com.meuscursos.apirestspring3.model.Medicos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface MedicosRepository extends JpaRepository<Medicos, Long> {

    Page<Medicos> findAllByStatusTrue(Pageable paginacao);
}
