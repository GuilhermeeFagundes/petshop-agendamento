package controller;

import java.util.Scanner;


public class MenuController {

    Scanner leia;
    PetController petController;
    AgendaController agendaController;
 

    public MenuController(Scanner scanner, PetController petController, AgendaController agendaController) {
        this.leia = scanner;
        this.petController = petController;
        this.agendaController = agendaController;
    }

    public void exibirMenu() {

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

                 case 6:
                    System.out.println("Finalizando..");
                    break;

                default:
                    System.out.println("Opção Inválida.");
            }

        } while (opcao != 6); 

    }
}
