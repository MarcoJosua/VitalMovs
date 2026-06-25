package com.vitalmovs.dtos;
import lombok.Data;
import java.time.LocalDate;


@Data
public class EstadisticaGraficoDTO {
    // Constructor para resumen general del plan
    public EstadisticaGraficoDTO(Long planRehabilitacionId, Double promedioDolor, Double promedioDificultad, Long totalRepeticiones, Long totalDuracion, Long cantidadRegistros) {
        this.planRehabilitacionId = planRehabilitacionId;
        this.promedioDolor = promedioDolor;
        this.promedioDificultad = promedioDificultad;
        this.totalRepeticiones = totalRepeticiones;
        this.totalDuracion = totalDuracion;
        this.cantidadRegistros = cantidadRegistros;
    }

    // Constructor para evolución por fecha
    public EstadisticaGraficoDTO(Long planRehabilitacionId, LocalDate fecha, Double promedioDolor, Double promedioDificultad, Long totalRepeticiones, Long totalDuracion, Long cantidadRegistros) {
        this.planRehabilitacionId = planRehabilitacionId;
        this.fecha = fecha;
        this.promedioDolor = promedioDolor;
        this.promedioDificultad = promedioDificultad;
        this.totalRepeticiones = totalRepeticiones;
        this.totalDuracion = totalDuracion;
        this.cantidadRegistros = cantidadRegistros;
    }

    // Constructor para resumen por ejercicio
    public EstadisticaGraficoDTO(Long planRehabilitacionId, Long planEjercicioId, String nombreEjercicio, Double promedioDolor, Double promedioDificultad, Long totalRepeticiones, Long totalDuracion, Long cantidadRegistros) {
        this.planRehabilitacionId = planRehabilitacionId;
        this.planEjercicioId = planEjercicioId;
        this.nombreEjercicio = nombreEjercicio;
        this.promedioDolor = promedioDolor;
        this.promedioDificultad = promedioDificultad;
        this.totalRepeticiones = totalRepeticiones;
        this.totalDuracion = totalDuracion;
        this.cantidadRegistros = cantidadRegistros;
    }

    private Long planRehabilitacionId;
    private Long planEjercicioId;
    private String nombreEjercicio;
    private LocalDate fecha;
    private Double promedioDolor;
    private Double promedioDificultad;
    private Long totalRepeticiones;
    private Long totalDuracion;
    private Long cantidadRegistros;
}
