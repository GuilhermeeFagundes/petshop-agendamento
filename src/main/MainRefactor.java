package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.AgendaController;
import controller.PetController;
import service.AgendaService;
import service.PetService;
import model.TipoPet;
import model.Agenda;
import model.Raca;
import model.Servico;
import util.RacaLoader;

public class MainRefactor {
    public static void main(String[] args) {
        Scanner leia = new Scanner(System.in);

        List<TipoPet> pets = new ArrayList<>();
        List<Agenda> agendas = new ArrayList<>();
        List<Servico> servicos = new ArrayList<>();
        List<Raca> racas = RacaLoader.carregarRacas("data/racas.txt"); // Carrega raças a partir de arquivo externo

        servicos.add(new Servico("banho", 100.0)); // servicos padrao
        servicos.add(new Servico("tosa", 200.0));

        PetService petService = new PetService(pets);
        PetController petController = new PetController(leia, petService, racas);

        AgendaService agendaService = new AgendaService(agendas, pets, servicos);
        AgendaController agendaController = new AgendaController(pets, leia, servicos, agendaService);

        int opcao = 0;

        do {

            System.out.println("\n======== MENU ========");
            System.out.println("1 - Cadastrar Pet");
            System.out.println("2 - Agendar Serviço");
            System.out.println("3 - Listar Agendamentos");
            System.out.println("4 - Cancelar Agendamento");
            System.out.println("5 - Gravar no Arquivo de Agendamentos");
            System.out.println("6 - Sair");
            System.out.print("Opção: ");

            try {
                opcao = leia.nextInt();
                leia.nextLine();
                System.out.println("");
            } catch (Exception erro) {
                System.out.println("Escolha um numero válido.");
                leia.nextLine(); // limpa o scanner
            }

            switch (opcao) {
                case 1:
                    petController.cadastrarPet();
                    break;

                case 2:
                    agendaController.agendarServico();
                    break;

                case 3:
                    agendaController.mostrarAgendamentos();
                    break;

                case 4:
                    agendaController.cancelarAgendamento();
                    break;

                case 5:
                    agendaController.gravarAgendamentos();
                    break;
            }

        } while (opcao != 6);

    }
}