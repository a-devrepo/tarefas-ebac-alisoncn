package com.javabackend;

import java.util.Scanner;

public class Programa {

    private int valor;

    public void lerNumero(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um valor inteiro: ");
        valor = scanner.nextInt();
    }

    public void exibirNumero(){
        Integer boxing = valor;
        System.out.printf("Valor informado: %d", boxing);
    }
}
