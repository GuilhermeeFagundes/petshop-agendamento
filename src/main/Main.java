
package main;

import java.time.LocalDate;
import model.*;
import java.util.*;

import java.io.BufferedWriter;
import java.io.FileWriter; //teste

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
            System.out.println("4 - Gravar no Arquivo de Agendamentos");
            System.out.println("5 - Sair");
            System.out.print("Opção: ");

            try {
                opcao = leia.nextInt();
                System.out.println("");
            } catch (Exception erro) {
                System.out.println("Escolha um numero válido.");
                leia.nextLine(); // limpa o scanner
            }

            switch (opcao) {

                case 1:
                    // verifica se ja tem pet e servicos cadastrados
                    if (pets.isEmpty() || servicos.isEmpty()) {
                        System.out.println("Cadastre um pet e um serviço antes.");
                        return;
                    }

                    // Selecionar Pet
                    int tempPet = 0;
                    System.out.println("Selecione um Pet: ");
                    for (int i = 0; i < pets.size(); i++) {
                        System.out.println((i + 1) + "-" + pets.get(i));
                        tempPet = i;
                    }

                    int petSelecionado = 0;
                    // valida a entrada
                    while (true) {
                        System.out.print("Opção: ");
                        try {
                            petSelecionado = leia.nextInt() - 1;
                            leia.nextLine(); // limpar scanner
                            if (petSelecionado >= 0 && petSelecionado <= tempPet) {
                                break;
                            } else {
                                System.out.println("Digite uma opção válida.");
                            }
                        } catch (Exception erro) {
                            System.out.println("Apenas numeros são válidos.");
                            leia.nextLine();
                        }
                    }
                    System.out.println("");

                    // escolher o Serviço
                    int tempServ = 0;
                    System.out.println("Selecione o serviço: ");
                    for (int i = 0; i < servicos.size(); i++) {
                        System.out.println((i + 1) + "-" + servicos.get(i));
                        tempServ = i + 1;
                    }
                    
                    int servicoSelecionado = 0;
                    // valida a entrada
                    while (true) {
                        System.out.print("Opção: ");
                        try {
                            servicoSelecionado = leia.nextInt() - 1;
                            leia.nextLine();
                            if (servicoSelecionado >= 0 && servicoSelecionado < tempServ) {
                                break;
                            } else {
                                System.out.println("Digite uma opção válida.");
                            }
                        } catch (Exception erro) {
                            System.out.println("Apenas numeros são válidos.");
                            leia.nextLine();
                        }
                    }
                    System.out.println("");

                    // escolher a data do agendamento
                    int data = 0;
                    String dataString = "";
                    int dia, mes, ano;

                    // valida entrada
                    while (true) {
                        System.out.println("Escolha a data do agendamento: ");
                        System.out.print("Data (DDMMAAAA): ");
                        dataString = leia.nextLine();

                        if (!dataString.matches("\\d{8}")) {
                            System.out.println("Formato inválido. Digite 8 números no formato DDMMAAAA.");
                            continue; // volta para o inicio do while
                        }

                        // separar dia, mes e ano para validacao
                        dia = Integer.parseInt(dataString.substring(0, 2));
                        mes = Integer.parseInt(dataString.substring(2, 4));
                        ano = Integer.parseInt(dataString.substring(4, 8));

                        try {
                            LocalDate dataValidar = LocalDate.of(ano, mes, dia);

                            if (dataValidar.isBefore(LocalDate.now())) {
                                System.out.println("Escolha uma data a partir de hoje.");
                            } else {
                                data = Integer.parseInt(dataString);
                                break;
                            }

                        } catch (Exception erro) {
                            System.out.println("Data inválida.");
                            leia.nextLine();
                        }

                    }
                    System.out.println("");

                    // cria ou chama uma agenda para a data escolhida
                    Agenda agenda = verificarAgenda(data);

                    // Verifica agenda e coloca horarios que nao tem
                    InicializarHorarios(agenda);

                    // exibe horarios para serem escolhidos
                    List<Horario> disponiveis = new ArrayList<>();
                    System.out.println("Horários disponíveis:");
                    int tempHorario = 0;
                    for (Horario h : agenda.getHorarios()) {
                        if (h.getStatus().equals("D")) { // seleciona somente horarios com status disponivel
                            disponiveis.add(h); //
                            System.out.println(disponiveis.size() + " - " + h);
                            tempHorario++;
                        }
                    }

                    // escolher horarios
                    int escolha;
                    // valida a entrada
                    while (true) {
                        System.out.print("Opção: ");
                        try {
                            escolha = leia.nextInt() - 1;
                            leia.nextLine();
                            if (escolha >= 0 && escolha <= tempHorario - 1) {
                                break;
                            } else {
                                System.out.println("Digite uma opção válida.");
                            }
                        } catch (Exception erro) {
                            System.out.println("Apenas numeros são válidos.");
                        }
                    }

                    System.out.println(""); // pula linha

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
                    gravarNoArquivo();
                    break;

                case 5:
                    System.out.println("Programa Encerrado.");
                    break;

                default:
                    System.out.println("Selecione uma opção válida. ");
            }
        } while (opcao != 5);
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

        System.out.println("Escolha o agendamento que quer cancelar: ");
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

        int cancelar;
        // valida a entrada
        while (true) {
            System.out.print("Opção: ");
            try {

                cancelar = leia.nextInt() - 1;
                leia.nextLine();
                if (cancelar >= 0 && cancelar < j) {
                    break;
                } else {
                    System.out.println("Digite uma opção válida");
                }
            } catch (Exception erro) {
                System.out.println("Apenas numeros são válidos");
                leia.nextLine();
            }
        }

        Horario novo = agendamentos.get(cancelar);

        System.out.println("Agendamento:" + novo.getPxS() + " cancelado X"); // antes de cancelar, usa a referencia para exibir o que esta cancelando
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

    // testando, alterar depois
    public static void gravarNoArquivo() {
        
        try {
            FileWriter fw = new FileWriter("data/agendamentos.txt", false); // false = Sobrepoe arquivo anterior
            BufferedWriter bw = new BufferedWriter(fw);
            if (agendas.size() < 1) {
                System.out.println("Faça pelo menos um agendamento para gravar no arquivo!");
                bw.close();
                return;
            }
            bw.write("--------------|Agendamentos|--------------");
            bw.newLine();
            bw.newLine();

            for (Agenda a : agendas) {
                bw.write("Data:" + a.getData());
                bw.newLine();

                for (Horario h : a.getHorarios()) {
                    if (h.getStatus().equals("O")) {
                        bw.write(h.getHoraInicio() + "-" + h.getHoraFim() + " -" + h.getPxS());
                    }

                }
                bw.newLine();
            }
            bw.close();
            System.out.println("Gravado no Arquivo.");
        } catch (Exception e) {
            System.out.println("Erro ao gravar agendamentos");
        }

    }
}
