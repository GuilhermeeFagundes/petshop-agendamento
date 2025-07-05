package model;


public class Horario {
    private String horaInicio;
    private String horaFim;
    private String status; // D e O
    private PxS pxs; //petxservico para aquele horario

    public Horario(String horaInicio, String horaFim, String status) {
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.status = status;
        this.pxs = null;
    }

    //Setter's
    public void setStatus (String status) {
        this.status = status;
    }

    public void setPxs(PxS pxs) {
        this.pxs = pxs;
    }

    //Getter's
    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public String getStatus() {
        return status;
    }

    public PxS getPxS() {
        return pxs;
    }

    @Override
    public String toString() {
       return horaInicio + "-" + horaFim + "-" + status;
    }
}


