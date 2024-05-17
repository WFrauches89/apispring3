package com.meuscursos.apirestspring3.model.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacientesIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicosIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);
}
