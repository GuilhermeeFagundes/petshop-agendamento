package model;

public class Raca {
    private String nome;
    private String especie;

    public Raca(String nome, String especie) {
        this.nome = nome;
        this.especie = especie;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    @Override
    public String toString() {
        return nome + " (" + especie + ")";
    }
}
