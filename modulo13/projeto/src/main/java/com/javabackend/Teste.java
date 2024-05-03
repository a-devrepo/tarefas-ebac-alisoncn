package com.javabackend;

public class Teste {
    public static void main(String[] args) {
        Pessoa pessoa1 = new PessoaFisica("Mario", "Rua da Virtude" +
                ", 721, Parque Luz, Rio de Janeiro, RJ", "21987430935", "111.111.111-11");
        Pessoa pessoa2 = new PessoaJuridica("Constantino e Familia Comércio de Alimentos LTDA",
                "Rua das Flores" +
                        ", 387, Jardim da Cidade, Rio de Janeiro, RJ", "2127753209"
                , "21.873.045/3984-62");

        System.out.println(pessoa1);
        System.out.println(pessoa2);
    }
}
