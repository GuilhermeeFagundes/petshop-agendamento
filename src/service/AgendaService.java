package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.*;

public class AgendaService {
    List<Agenda> agendas;
    List<TipoPet> pets;
    List<Servico> servicos;

    public AgendaService(List<Agenda> agendas, List<TipoPet> pets, List<Servico> servicos) {
        this.agendas = agendas;
        this.pets = pets;
        this.servicos = servicos;
    }

    public PxS criarAgendamento(TipoPet pet, Servico servico, Horario escolhido) {
        PxS pxs = new PxS(pet, servico);

        // Marcar horario como ocupado e associar aquele horario ao agendamento feito
        escolhido.setPxs(pxs); // altera o Horario selecionado
        escolhido.setStatus("O");

        return pxs;
    }

    public Agenda verificarAgenda(int data) {
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

    public void inicializarHorarios(Agenda agenda) {
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

    public List<String> mostrarAgendamentos() {
        //movido para o controller System.out.println("\n====== Agendas ======");
        List<String> mostrarAgendamentos = new ArrayList<>();
        for (Agenda a : agendas) {
           mostrarAgendamentos.add("Data:" + a.getData()); //System.out.println("Data:" + a.getData());

            for (Horario h : a.getHorarios()) {
                if (h.getStatus().equals("O")) { // mostra os horarios que foram selecionados
                     mostrarAgendamentos.add(h.getHoraInicio() + "-" + h.getHoraFim() + "[" + h.getStatus() + "]" + h.getPxS());
                }
            }
        }
        return mostrarAgendamentos;
    }

    public void exibirAgendamentosOcupados(List<Horario> agendamentos, List<String> exibirAgendados) {
     
        int j = 0;
        for (Agenda a : agendas) {
            exibirAgendados.add("Data:" + a.getData());

            for (int i = 0; i < a.getHorarios().size(); i++) {

                if (a.getHorarios().get(i).getStatus().equals("O")) {
                    agendamentos.add(a.getHorarios().get(i));
                    exibirAgendados.add((j + 1) + "- " + a.getHorarios().get(i).getHoraInicio() + "-" + a.getHorarios().get(i).getHoraFim() + "[" + a.getHorarios().get(i).getStatus() + "]" + a.getHorarios().get(i).getPxS());

                    j++;

                }
            }
        } 
    }

    public void cancelarAgendamento(Horario novo) {

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
    public boolean gravarNoArquivo() {

        try {
            FileWriter fw = new FileWriter("data/agendamentos.txt", false); // false = Sobrepoe arquivo anterior
            BufferedWriter bw = new BufferedWriter(fw);
            if (agendas.size() < 1) {
                bw.close();
                return false;
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
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
