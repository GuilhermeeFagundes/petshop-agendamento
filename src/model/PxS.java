package model;

public class PxS {
    Pet pet;
    Servico servico;

    public PxS(Pet pet, Servico servico) {
        this.pet = pet;
        this.servico = servico;
    }

    public Pet getpet() {
        return pet;
    }

    public Servico getServico() {
        return servico;
    }

    @Override
    public String toString() {
        return " |Nome: " + pet.getNome() + " | Serviço: " + servico.getNome() + " | Valor: R$" + servico.getValor() + "|";
    }
}
