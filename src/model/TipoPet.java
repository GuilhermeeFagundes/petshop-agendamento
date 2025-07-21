package model;

public class TipoPet extends Pet {
    private char porte;
    private Raca raca; // Ligação direta com a classe Raca

    public TipoPet(String nome, String data, double peso, char genero, char porte, Raca raca) {
        super(nome, data, peso, genero);
        this.porte = Character.toUpperCase(porte);
        this.raca = raca;
    }

    public char getPorte() {
        return porte;
    }

    public Raca getRaca() {
        return raca;
    }

    @Override
    public String toString() {
        return super.toString() + " | Porte: " + porte + " | Raça: " + raca.getNome() + " (" + raca.getEspecie() + ")";
    }
}
