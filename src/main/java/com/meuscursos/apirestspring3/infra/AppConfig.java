package com.meuscursos.apirestspring3.infra;

import com.meuscursos.apirestspring3.dto.usuarios.DadosUsuarioResponseDetails;
import com.meuscursos.apirestspring3.model.Usuarios;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
