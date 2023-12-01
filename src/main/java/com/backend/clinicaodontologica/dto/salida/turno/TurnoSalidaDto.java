package com.backend.clinicaodontologica.dto.salida.turno;

import java.time.LocalDateTime;

public class TurnoSalidaDto {
    private Long id;
    private LocalDateTime fechaYHora;
    private Long odontologoId;
    private String odontologo_nombre;
    private Long pacienteId;
    private String paciente_nombre;

    public TurnoSalidaDto() {
    }

    public TurnoSalidaDto(Long id, LocalDateTime fechaYHora, Long odontologo_id, Long paciente_id) {
        this.id = id;
        this.fechaYHora = fechaYHora;
       this.odontologoId = odontologo_id;
       this.pacienteId = paciente_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getOdontologoId() {
      return odontologoId;
    }

    public void setOdontologoId(Long odontologo_id) {
        this.odontologoId = odontologo_id;
    }

    public Long getPacienteId() {
       return pacienteId;
    }

    public String getOdontologo_nombre() {
        return odontologo_nombre;
    }

    public void setOdontologo_nombre(String odontologo_nombre) {
        this.odontologo_nombre = odontologo_nombre;
    }

    public String getPaciente_nombre() {
        return paciente_nombre;
    }

    public void setPaciente_nombre(String paciente_nombre) {
        this.paciente_nombre = paciente_nombre;
    }

   public void setPacienteId(Long paciente_id) {
    this.pacienteId = paciente_id;
   }
}
