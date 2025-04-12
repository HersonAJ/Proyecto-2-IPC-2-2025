/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalTime;

/**
 *
 * @author herson
 */
public class HorarioGeneral {
    
    private int diaSemana;
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    public int getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }
    
    @Override
    public String toString() {
        return "HorarioGeneral {" + 
                "diaSemana=" + diaSemana + 
                ", horaApertura=" + horaApertura +
                ", horaCierre=" + horaCierre +
                '}';
    }
}
