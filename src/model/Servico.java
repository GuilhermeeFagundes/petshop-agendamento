package model;

public class Servico {
    private String nome;
    private double valor;

    public Servico(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return nome + " | Valor base: " + valor;
    }
}
