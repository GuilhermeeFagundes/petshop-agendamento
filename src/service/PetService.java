package service;

import model.*;
import java.util.*;

public class PetService {

    private final List<TipoPet> pets;

    public PetService(List<TipoPet> pets) {
        this.pets = pets;
    }

    public TipoPet criarPet(String nome, String data, double peso, char genero, char porte, Raca racaEscolhida) {

        TipoPet novoPet = new TipoPet(nome, data, peso, genero, porte, racaEscolhida);
        pets.add(novoPet);

        return novoPet;

    }

    public List<TipoPet> getPets() {
        return pets;
    }

}
