package model;

public class PxS {
    private TipoPet pet;
    private Servico servico;

    public PxS(TipoPet pet, Servico servico) {
        this.pet = pet;
        this.servico = servico;
    }

    public TipoPet getPet() {
        return pet;
    }

    public Servico getServico() {
        return servico;
    }

    public double calcularValorComPorte() {
        double acrescimo = switch (pet.getPorte()) {
            case 'M' -> 0.2;
            case 'G' -> 0.4;
            default -> 0.0;
        };
        return servico.getValor() * (1 + acrescimo);
    }

    @Override
    public String toString() {
        return " |Pet: " + pet.getNome() + " | Serviço: " + servico.getNome() + " | Valor Final: R$" + String.format("%.2f", calcularValorComPorte());
    } 
}
