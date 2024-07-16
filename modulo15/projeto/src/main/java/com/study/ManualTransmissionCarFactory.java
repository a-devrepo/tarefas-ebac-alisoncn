package com.study;

public class ManualTransmissionCarFactory extends Factory {

    @Override
    Car retrieveCar(String carModel) {
        switch (carModel) {
            case "A":
                return new HyundaiHB20("grey", "full", 1000);
            case "B":
                return new NissanVersa("black", "full", 1500);
            case "C":
                return new OnixPlus("white","full",1200);
            default:
                System.out.println("Invalid model option");
                return null;
        }
    }
}
