package com.study;

import java.lang.annotation.Annotation;

public class Demo {
    public static void main(String[] args) {

        Cliente cliente = new Cliente("José", "Maria", 'M', 30);

        if (cliente.getClass().isAnnotationPresent(Tabela.class)) {
            Tabela tabela = cliente.getClass().getAnnotation(Tabela.class);
            System.out.println("Nome da anotação: " + tabela.annotationType().getSimpleName());
            System.out.println("Valor da anotação: " + tabela.value());
        }
    }
}
