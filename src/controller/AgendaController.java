package controller;

import java.time.LocalDate;
import java.util.*; //Scanner, List e ArrayList

import model.*;
import service.AgendaService;

public class AgendaController {
    Scanner leia;

    List<TipoPet> pets;
    List<Servico> servicos;

    AgendaService agendaService;

    public AgendaController(List<TipoPet> pets, Scanner scanner, List<Servico> servicos, AgendaService agendaService) {
        this.pets = pets;
        this.leia = scanner;
        this.servicos = servicos;
        this.agendaService = agendaService;
    }

    public void agendarServico() {
        // verifica se ja tem pet e servicos cadastrados
        if (pets.isEmpty()) {
            System.out.println("Cadastre um pet antes de agendar");
            return;
        }

        // Selecionar Pet
        int tempPet = 0;
        System.out.println("Selecione um Pet: ");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + "-" + pets.get(i).getNome());
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
        Agenda agenda = agendaService.verificarAgenda(data);

        // Verifica agenda e coloca horarios que nao tem
        agendaService.inicializarHorarios(agenda);

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
        PxS pxs = agendaService.criarAgendamento(pets.get(petSelecionado), servicos.get(servicoSelecionado), escolhido);

        System.out.println("Agendado:" + pxs);

    }

    public void mostrarAgendamentos() {
        //armazena valores separados no Service
        List<String> agendamentos = agendaService.mostrarAgendamentos();
        if(agendamentos.isEmpty()) {
            System.out.print("Faça pelo menos um agendamento antes de listar os agendamentos.");
            return;
        }
        //Exibe no console
        System.out.println("\n====== Agendas ======");
        for (String linha : agendamentos) {
            System.out.println(linha);
        }

    }

    public void cancelarAgendamento() {

        List<Horario> agendados = new ArrayList<>();
        List<String> exibirAgendados = new ArrayList<>();

        agendaService.exibirAgendamentosOcupados(agendados, exibirAgendados);

        if (agendados.isEmpty()) {
            System.out.println("Faça pelo menos um agendamento antes de cancelar.");
            return;
        }

        System.out.println("Escolha o agendamento que quer cancelar:");
        for (String d : exibirAgendados) {
            System.out.println(d);
        }

        int cancelar;
        // valida a entrada
        while (true) {
            System.out.print("Opção: ");
            try {
                cancelar = leia.nextInt() - 1;
                leia.nextLine();
                if (cancelar >= 0 && cancelar < agendados.size()) {
                    break;
                } else {
                    System.out.println("Digite uma opção válida");
                }
            } catch (Exception erro) {
                System.out.println("Apenas numeros são válidos");
                leia.nextLine();
            }

        }
        Horario novo = agendados.get(cancelar);
        
        System.out.println("Agendamento:" + novo.getPxS() + " [X]cancelado"); // antes de cancelar, usa a referencia para exibir o que esta cancelando
                                                                             
        agendaService.cancelarAgendamento(novo);
    }

    public void gravarAgendamentos() {
        agendaService.gravarNoArquivo();
        if (!agendaService.gravarNoArquivo()) {
            System.out.println("Erro ao gravar agendamentos ou nenhum agendamento disponível.");
        } else {
            System.out.println("Gravado no Arquivo.");
        }
    }
}
