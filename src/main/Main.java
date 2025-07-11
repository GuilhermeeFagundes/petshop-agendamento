package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.AgendaController;
import controller.MenuController;
import controller.PetController;
import service.AgendaService;
import service.PetService;
import model.TipoPet;
import model.Agenda;
import model.Raca;
import model.Servico;
import util.RacaLoader;

public class Main {
    public static void main(String[] args) {
        Scanner leia = new Scanner(System.in);

        List<TipoPet> pets = new ArrayList<>();
        List<Agenda> agendas = new ArrayList<>();
        List<Servico> servicos = new ArrayList<>();
        List<Raca> racas = RacaLoader.carregarRacas("data/racas.txt"); // Carrega raças a partir de arquivo externo

        // serviços padrão
        servicos.add(new Servico("banho", 100.0)); 
        servicos.add(new Servico("tosa", 200.0));

        PetService petService = new PetService(pets);
        PetController petController = new PetController(leia, petService, racas);

        AgendaService agendaService = new AgendaService(agendas, pets, servicos);
        AgendaController agendaController = new AgendaController(pets, leia, servicos, agendaService);

        MenuController menuController = new MenuController(leia, petController, agendaController);

        menuController.exibirMenu(); //Exibe o MENU
    }
}