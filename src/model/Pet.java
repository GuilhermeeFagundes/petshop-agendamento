package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Pet {
    private String nome;
    private LocalDate dataNascimento;
    private double peso;
    private char genero;

    private static final DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Pet(String nome, String dataNascimento, double peso, char genero) {
        this.nome = nome;
        setDataNascimento(dataNascimento);
        this.peso = peso;
        this.genero = Character.toUpperCase(genero);
    }

    public void setDataNascimento(String dataStr) {
        try {
            this.dataNascimento = LocalDate.parse(dataStr, formatoBR);
        } catch (DateTimeParseException e) {
            System.out.println("Data de nascimento inválida. Use o formato dd/MM/yyyy.");
        }
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public double getPeso() {
        return peso;
    }

    public char getGenero() {
        return genero;
    }

    public String getDataFormatada() {
        return dataNascimento != null ? dataNascimento.format(formatoBR) : "Data inválida";
    }

    @Override
    public String toString() {
        return nome + " | Nasc: " + getDataFormatada() + " | Peso: " + peso + "kg | Gênero: " + genero;
    }
}
