package com.study;

public class AutomaticTransmissionCarFactory extends Factory {

    @Override
    Car retrieveCar(String carModel) {
        switch (carModel) {
            case "A":
                return new LifanX60("blue", "full", 1000);
            case "B":
                return new CheryTiggo2("grey", "full", 1500);
            case "C":
                return new NissanKicksS("red","full",1200);
            default:
                System.out.println("Invalid model option");
                return null;
        }
    }
}
