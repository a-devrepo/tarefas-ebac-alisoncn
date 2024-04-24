package com.javabackend.projeto;

import java.util.Scanner;

public class Calculo {

    private double nota1;
    private double nota2;
    private double nota3;
    private double nota4;

    public double calcular() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a primeira nota: ");
        nota1 = scanner.nextDouble();
        System.out.print("Digite a segunda nota: ");
        nota2 = scanner.nextDouble();
        System.out.print("Digite a terceira nota: ");
        nota3 = scanner.nextDouble();
        System.out.print("Digite a quarta nota: ");
        nota4 = scanner.nextDouble();

        return (nota1 + nota2 + nota3 + nota4) / 4;
    }
}
