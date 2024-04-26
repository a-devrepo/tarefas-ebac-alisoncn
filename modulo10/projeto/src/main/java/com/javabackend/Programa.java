package com.javabackend;

import java.util.Scanner;

public class Programa {

    private double totalNotas;

    public void lerNota() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            System.out.printf("Digite a " + (i + 1) + "º nota: ");
            totalNotas += scanner.nextDouble();
        }
    }

    public void exibirResultado() {
        double resultado = totalNotas / 4;
        String mensagem;

        if (resultado >= 7) {
            mensagem = "APROVADO";
        } else if (resultado >= 5) {
            mensagem = "RECUPERAÇÃO";
        } else {
            mensagem = "REPROVADO";
        }
        System.out.print(mensagem);
    }
}
