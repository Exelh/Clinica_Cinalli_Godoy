package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;//
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;//
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;//
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;//
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;//
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;//
import com.backend.clinicaodontologica.entity.Turno;//

import com.backend.clinicaodontologica.service.ITurnoService;//
import com.backend.clinicaodontologica.utils.JsonPrinter;//
import org.modelmapper.ModelMapper;//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private TurnoRepository turnoRepository;
    private ModelMapper modelMapper;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;


    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws BadRequestException {
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turno.getOdontologo_id());
        PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turno.getPaciente_id());

        if (odontologo == null && paciente == null) {
            throw new BadRequestException("Odontologo y paciente no registrados.");
        } else if (odontologo == null) {
            throw new BadRequestException("No se encuentra un odontólogo");
        } else if (paciente == null) {
            throw new BadRequestException("No se encuentra un paciente");
        }

        Turno turnoEntidad = modelMapper.map(turno, Turno.class);


        LOGGER.info("Entidad: " + JsonPrinter.toString(turnoEntidad));

        Turno turnoAPersistir = turnoRepository.save(turnoEntidad);
        LOGGER.info("Turno a persistir: " + JsonPrinter.toString(turnoAPersistir));

        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
        // Asignar el nombre del odontólogo y del paciente al DTO de salida
        turnoSalidaDto.setOdontologo_nombre(odontologo.getNombreCompleto());
        turnoSalidaDto.setPaciente_nombre(paciente.getNombreCompleto());

        LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {

        List<TurnoSalidaDto> turnoSalidaDto = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class)).toList();
        if (LOGGER.isInfoEnabled())
            LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnoSalidaDto));

        return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if(turnoBuscado != null){
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
        }else{
            LOGGER.error("El id no se encuentra registrado en la base de datos");
        }
        return turnoEncontrado;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if(turnoRepository.findById(id).orElse(null) !=null){
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id: {}", id);
        }
        else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
            throw new ResourceNotFoundException("No se ha turno el turno con id " + id);
        }
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) {
        Turno turnoRecibido = modelMapper.map(turno, Turno.class);
        Turno turnoAActualizar = turnoRepository.findById(turnoRecibido.getId()).orElse(null);

        TurnoSalidaDto turnoSalidaDto = null;

        if(turnoAActualizar != null){
            turnoAActualizar = turnoRecibido;
            turnoRepository .save(turnoAActualizar);

            turnoSalidaDto = modelMapper.map(turnoAActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoSalidaDto));
        }else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
        }

        return turnoSalidaDto;
    }
    private void configureMapping(){
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getOdontologo_id, Turno::setOdontologo))
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getPaciente_id, Turno::setPaciente));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologo_id))
                .addMappings(modelMapper -> modelMapper.map(Turno::getPaciente, TurnoSalidaDto::setPaciente_id));

        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoModificacionEntradaDto::getOdontologoEntradaDto, Turno::setOdontologo))
                .addMappings(modelMapper -> modelMapper.map(TurnoModificacionEntradaDto::getPacienteEntradaDto, Turno::setPaciente));
    }

}
