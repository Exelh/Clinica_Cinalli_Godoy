package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;


    @Test

    void alIntentarEliminarUnTurnoInexistentedeberiaLanzarUnaResourceNotFoundException(){

        try{
            turnoService.eliminarTurno(1L);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, () -> turnoService.eliminarTurno(1L));
    }

    @Test

    void deberiaDevolverUnaListaVaciaDeTurnos(){

        List<TurnoSalidaDto> turnosDto = turnoService.listarTurnos();
        assertTrue(turnosDto.isEmpty());

    }
}
