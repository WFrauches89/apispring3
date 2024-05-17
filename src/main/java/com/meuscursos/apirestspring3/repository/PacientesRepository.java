package com.meuscursos.apirestspring3.repository;

import com.meuscursos.apirestspring3.model.Pacientes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacientesRepository extends JpaRepository<Pacientes, Long> {


    Page<Pacientes> findAllByStatusTrue(Pageable paginacao);

    @Query("""
            select p.status
            from Pacientes p
            where
            p.id = :id
            """)
    Boolean findStatusById(Long id);
}
