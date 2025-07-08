package main;

import model.*;
import java.util.*;

public class Main {
    static List<Agenda> agendas = new ArrayList<>();
    static List<Pet> pets = new ArrayList<>();
    static List<Servico> servicos = new ArrayList<>();

    public static void main(String[] args) {
        pets.add(new Pet("bolinha")); // para teste
        pets.add(new Pet("trovao"));
        servicos.add(new Servico("banho", 100.0)); // servicos padrao
        servicos.add(new Servico("tosa", 200.0));
        Scanner leia = new Scanner(System.in);
        int opcao = 0;

        do {

            System.out.println("\n======== MENU ========");
            System.out.println("1 - Agendar Serviço");
            System.out.println("2 - Listar Agendamentos");
            System.out.println("3 - Cancelar Agendamento");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");

            try {
                opcao = leia.nextInt();
                System.out.println("");
            } catch (Exception erro) {
                System.out.println("Escolha um numero válido");
                leia.nextLine(); // limpa o scanner
            }

            switch (opcao) {

                case 1:
                    // verifica se ja tem pet e servicos cadastrados
                    if (pets.isEmpty() || servicos.isEmpty()) {
                        System.out.println("Cadastre um pet e um serviço antes..");
                        return;
                    }

                    // Selecionar Pet
                    System.out.println("Selecione um Pet: ");
                    for (int i = 0; i < pets.size(); i++) {
                        System.out.println((i + 1) + "-" + pets.get(i));
                    }
                    System.out.print("Opção: ");
                    int petSelecionado = leia.nextInt() - 1; //adicionar validacao de indices
                    leia.nextLine(); // para nao bugar..
                    System.out.println("");

                    // escolher o Serviço
                    System.out.println("Selecione o serviço: ");
                    for (int i = 0; i < servicos.size(); i++) {
                        System.out.println((i + 1) + "-" + servicos.get(i));
                    }
                    System.out.print("Opção: ");
                    int servicoSelecionado = leia.nextInt() - 1; //adicionar validacao de indices
                    leia.nextLine();
                    System.out.println("");
                    
                    // escolher a data do agendamento
                    System.out.println("Escolha a data do agendamento: ");
                    System.out.print("Data (DDMMAAAA): ");
                    int data = leia.nextInt(); //possivelmente mudar para String e fazer validação
                    leia.nextLine();
                    System.out.println("");

                    // cria ou chama uma agenda para a data escolhida
                    Agenda agenda = verificarAgenda(data);

                    // Verifica agenda e coloca horarios que nao tem
                    InicializarHorarios(agenda);

                    // exibe horarios para serem escolhidos
                    List<Horario> disponiveis = new ArrayList<>();
                    System.out.println("Horários disponíveis:");
                    for (Horario h : agenda.getHorarios()) {
                        if (h.getStatus().equals("D")) { // seleciona somente horarios com status disponivel
                            disponiveis.add(h); //
                            System.out.println(disponiveis.size() + " - " + h);
                        }
                    }
                    //escolher horarios
                    System.out.print("Opção: ");
                    int escolha = leia.nextInt() - 1;
                    leia.nextLine();
                    System.out.println(""); //pula linha

                    Horario escolhido = disponiveis.get(escolha); // referencia ao objeto Horario selecionado

                    // Cria o agendamento pet-servico
                    PxS pxs = new PxS(pets.get(petSelecionado), servicos.get(servicoSelecionado));

                    // Marcar horario como ocupado e associar aquele horario ao agendamento feito
                    escolhido.setPxs(pxs); // altera o Horario selecionado
                    escolhido.setStatus("O");

                    System.out.println("Agendado:" + pxs);

                    break;

                case 2:
                    mostrarAgendamentos();
                    break;

                case 3:
                    cancelarAgendamento(leia);
                    break;

                case 4:

                    break;

                default:
                    System.out.println("Selecione uma opção válida. ");
            }
        } while (opcao != 4);
        leia.close();
    }

    static Agenda verificarAgenda(int data) {
        // verificar se existe uma agenda para a data escolhida
        for (Agenda a : agendas) { 
            if (a.getData() == data) {
                return a; // retorna a agenda escolhida
            }
        }

        // Criar nova agenda vazia
        List<Horario> listaHorarios = new ArrayList<Horario>();
        Agenda nova = new Agenda(data, listaHorarios);
        agendas.add(nova);
        return nova;
    }

    static void InicializarHorarios(Agenda agenda) {
        List<Horario> Horariospadrao = Arrays.asList(
                new Horario("8:00", "8:30", "D"),
                new Horario("9:00", "9:30", "D"),
                new Horario("10:00", "10:30", "D"),
                new Horario("11:00", "11:30", "D"),
                new Horario("14:00", "14:30", "D"),
                new Horario("15:00", "15:30", "D"),
                new Horario("16:00", "16:30", "D"));

        for (Horario objetoHorario : Horariospadrao) {
            boolean existe = false; // para saber se horario ja existe

            for (Horario h : agenda.getHorarios()) { // retorna a lista de horarios daquele dia..

                if (h.getHoraInicio().equals(objetoHorario.getHoraInicio())) {
                    existe = true;
                    break; // sai do loop
                }
            }
            if (!existe) {
                agenda.adicionarHorario(objetoHorario); // se nao encontrar, adiciona o horario a lista
            }

        }
    }

    static void mostrarAgendamentos() {
        System.out.println("---Agendas---");

        for (Agenda a : agendas) {
            System.out.println("Data:" + a.getData());

            for (Horario h : a.getHorarios()) {
                if (h.getStatus().equals("O")) { // mostra os horarios que foram selecionados
                    System.out.println(h.getHoraInicio() + "-" + h.getHoraFim() + "[" + h.getStatus() + "]" + h.getPxS());
                }
            }

        }

    }

    static void cancelarAgendamento(Scanner leia) {
        
        List<Horario> agendamentos = new ArrayList<>(); // para modificar o horario escolhido.
        int j = 0;

        for (Agenda a : agendas) {
            System.out.println("Data:" + a.getData());

            for (int i = 0; i < a.getHorarios().size(); i++) {

                if (a.getHorarios().get(i).getStatus().equals("O")) {
                    agendamentos.add(a.getHorarios().get(i));
                    System.out.println(
                            (j + 1) + "- " + a.getHorarios().get(i).getHoraInicio() + "-" + a.getHorarios().get(i).getHoraFim() + "[" + a.getHorarios().get(i).getStatus() + "]" + a.getHorarios().get(i).getPxS());
                    j++;
                }
            }

        }

        System.out.println("Escolha o agendamento que quer cancelar: ");
        int cancelar = leia.nextInt() - 1;
        leia.nextLine();

        Horario novo = agendamentos.get(cancelar);

        System.out.println("Agendamento " + novo.getPxS() + "cancelado"); // antes de cancelar, usa a referencia para exibir o que esta cancelando
        novo.setPxs(null);
        novo.setStatus("D");

        // remove a agenda da lista caso esteja sem nenhum horario agendado(para nao exibir agenda vazia)
        int verificador = 0;
        for (int i = 0; i < agendas.size(); i++) {
            for (int k = 0; k < agendas.get(i).getHorarios().size(); k++) {
                if (agendas.get(i).getHorarios().get(k).getStatus().equals("O")) {
                    verificador++;
                }

            }
            if (verificador == 0) {
                agendas.remove(i);
            }
            verificador = 0;
        }
        
    }
}
