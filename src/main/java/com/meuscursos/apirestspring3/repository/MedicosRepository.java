package com.meuscursos.apirestspring3.repository;

import com.meuscursos.apirestspring3.enums.Especialidade;
import com.meuscursos.apirestspring3.model.Medicos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public interface MedicosRepository extends JpaRepository<Medicos, Long> {

    Page<Medicos> findAllByStatusTrue(Pageable paginacao);

    @Query("""
            select m from Medicos m
            where
            m.status = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medicos.id from Consulta c
                where
                c.data = :data
                and
                c.motivoCancelamento is null
            )
            order by rand()
            limit 1
        """)
    Medicos escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.status
            from Medicos m
            where
            m.id = :id
            """)
    Boolean findStatusById(Long id);
}
