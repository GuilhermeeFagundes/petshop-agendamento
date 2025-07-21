package util;

import model.Raca;
import java.util.*;
import java.io.*;

public class RacaLoader {
    public static List<Raca> carregarRacas(String caminho) {
        List<Raca> racas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(":");
                if (dados.length == 2) {
                    racas.add(new Raca(dados[1].trim(), dados[0].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de raças: " + e.getMessage());
        }

        return racas;
    }
}
