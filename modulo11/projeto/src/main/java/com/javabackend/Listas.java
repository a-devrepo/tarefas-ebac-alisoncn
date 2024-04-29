package com.javabackend;

import java.util.*;

public class Listas {


    public void listarNomes() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a lista de nomes separados por vírgula: ");
        String entrada = scanner.nextLine();
        String[] listaEntrada = entrada.split(",");
        List<String> listaNomes = new ArrayList<>(Arrays.asList(listaEntrada));
        Collections.sort(listaNomes);

        for (String nome : listaNomes) {
            System.out.println(nome);
        }
        scanner.close();
    }

    public void listarNomeGenero() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite nome e gênero(M/F) separados por traço, e cada par separado por vírgula: ");
        String entrada = scanner.nextLine();
        String[] listaEntrada = entrada.split(",");
        Map<String, List<String>> grupos = new HashMap<>();

        for (String dados : listaEntrada) {
            String nome = dados.split("-")[0];
            String genero = dados.split("-")[1];
            if (genero.equalsIgnoreCase("f")) {
                if (!grupos.containsKey("F")) {
                    grupos.put("F", new ArrayList<>(Arrays.asList(nome)));
                } else {
                    grupos.get("F").add(nome);
                }
            } else {
                if (!grupos.containsKey("M")) {
                    grupos.put("M", new ArrayList<>(Arrays.asList(nome)));
                } else {
                    grupos.get("M").add(nome);
                }
            }
        }

        System.out.println("Sexo Feminino");
        for (String nome : grupos.get("F")) {
            System.out.println(nome);
        }

        System.out.println("Sexo Masculino");
        for (String nome : grupos.get("M")) {
            System.out.println(nome);
        }

    }
}
