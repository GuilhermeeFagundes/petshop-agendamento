package model;

import java.util.List;
public class Agenda {
    private int data;
    private List<Horario> horarios;

    public Agenda (int data, List<Horario> horarios) {
        this.data = data;
        this.horarios = horarios;
    }

    //Getter`s
    public int getData() {
        return data;
    }
    public List<Horario> getHorarios() {
        return horarios;
    }

    public void adicionarHorario(Horario h) {
        horarios.add(h);
    }

    /*@Override
    public String toString(){
        
    }*/
}
