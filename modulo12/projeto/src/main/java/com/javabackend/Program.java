package com.javabackend;

import java.util.*;

public class Program {

    private Map<String, List<String>> dataMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public void readData() {
        dataMap.put("F", new ArrayList<>());
        dataMap.put("M", new ArrayList<>());

        System.out.print("Informe nome e gênero separados por \"-\", e cada par separado por \",\": Nome-M, : ");
        String input = scanner.nextLine();

        String[] data = input.split(",");

        for (String line : data) {
            String gender = line.split("-")[1];
            String name = line.split("-")[0];
            if (gender.equalsIgnoreCase("F")) {
                dataMap.get("F").add(name);
            } else {
                dataMap.get("M").add(name);
            }
        }
    }

    public void printData() {
        dataMap.forEach((k, v) -> System.out.printf("%-15s : %s%n", k, v));
        scanner.close();
    }
}
