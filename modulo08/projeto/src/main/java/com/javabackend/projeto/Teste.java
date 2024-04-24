package com.javabackend.projeto;

public class Teste {
    public static void main(String[] args) {
        Calculo calculo = new Calculo();
        double media = calculo.calcular();
        System.out.printf("Valor da média: %.2f", media);
    }
}
