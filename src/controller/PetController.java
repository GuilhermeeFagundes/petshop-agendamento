package controller;

import service.PetService;
import model.*;
import java.util.*;

public class PetController {
    private Scanner leia;
    private PetService petService;
    private List<Raca> racas;

    public PetController(Scanner scanner, PetService petService, List<Raca> racas) {
        this.leia = scanner;
        this.petService = petService;
        this.racas = racas;
    }

    public void cadastrarPet() {
        try {
            System.out.print("Nome do pet: ");
            String nome = leia.nextLine();
            if (nome == null || nome.isBlank()) {
                throw new IllegalArgumentException("Nome não pode ficar em branco.");
            }

            System.out.print("Data de nascimento (dd/MM/yyyy): ");
            String data = leia.nextLine();

            System.out.print("Peso (kg): ");
            double peso = leia.nextDouble();
            if (peso > 350 || peso < 1) {
                throw new IllegalArgumentException("O peso deve ser um valor positivo válido e maior que zero.");
            }

            System.out.print("Gênero (M/F): ");
            char genero = leia.next().charAt(0);
            if (Character.toUpperCase(genero) != 'M' && Character.toUpperCase(genero) != 'F') {
                throw new IllegalArgumentException("O gênero deve ser 'M' (macho) ou 'F' (fêmea).");
            }

            System.out.print("Porte (P/M/G): ");
            char porte = leia.next().charAt(0);
            leia.nextLine();
            if (Character.toUpperCase(porte) != 'P' && Character.toUpperCase(porte) != 'M'
                    && Character.toUpperCase(porte) != 'G') {
                throw new IllegalArgumentException(
                        "O porte deve ser 'P'(pequeno), 'M'(médio) ou 'G'(grande).");
            }

            System.out.print("Selecione a espécie: 1 - Gato | 2 - Cachorro: ");
            int especieOpcao = leia.nextInt();
            leia.nextLine();
            if (especieOpcao != 1 && especieOpcao != 2) {
                throw new IllegalArgumentException("Escolha uma espécie entre Gato ou Cachorro.");
            }

            String especie = (especieOpcao == 1) ? "Gato" : "Cachorro";
            List<Raca> racasFiltradas = new ArrayList<>();

            for (Raca r : racas) {
                if (r.getEspecie().equalsIgnoreCase(especie)) {
                    racasFiltradas.add(r);
                }
            }

            for (int i = 0; i < racasFiltradas.size(); i++) {
                System.out.println((i + 1) + " - " + racasFiltradas.get(i).getNome());
            }

            System.out.print("Escolha a raça: ");
            int escolhaRaca = leia.nextInt();
            leia.nextLine();
            Raca racaEscolhida = racasFiltradas.get(escolhaRaca - 1);
            if (escolhaRaca < 1 || escolhaRaca > racasFiltradas.size()) {
                throw new IllegalArgumentException("Escolha uma opção de raça válida.");
            }

            TipoPet novoPet = petService.criarPet(nome, data, peso, genero, porte, racaEscolhida);

            System.out.println("");
            System.out.println("Pet cadastrado com sucesso!\n" + novoPet);
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Use números onde for solicitado.");
            leia.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage() + " Tente novamente.");
        } catch (Exception e) {
            System.out.println("Erro inesperado. Tente novamente.");
        }

    }

}