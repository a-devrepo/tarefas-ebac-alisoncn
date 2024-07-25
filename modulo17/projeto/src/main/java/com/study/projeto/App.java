package com.study.projeto;

public class App {

    public static void main(String[] args) {

        IListaCarros carros = new ListaCarros();

        Carro carro1 = new Onix("Onix 1.0", "Chevrolet", false);
        Carro carro2 = new CitroenC3("Citroën C3 Live 1.0", "Citroën", false);
        Carro carro3 = new FiatArgo("Fiat Argo 1.0", "Fiat", false);
        Carro carro4 = new HB20ComfortPlus("HB20 Comfort Plus", "Hyundai", true);
        Carro carro5 = new NissanVersa16("Nissan Versa 1.6 Sense CVT", "Nissan", true);
        Carro carro6 = new OnixPlusATTurbo("Onix Plus AT Turbo", "Chevrolet", true);

        Carro cadastrado = carros.cadastrar(carro1);
        carros.cadastrar(carro2);
        carros.cadastrar(carro3);
        carros.cadastrar(carro4);
        carros.cadastrar(carro5);
        carros.cadastrar(carro6);

        carros.listar().forEach(System.out::println);

        System.out.println();
        System.out.println(carros.consultar(cadastrado.getCodigo()));
    }
}
