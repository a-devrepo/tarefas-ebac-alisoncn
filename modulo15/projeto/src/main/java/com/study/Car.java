package com.study;

public abstract class Car {

    private String color;
    private String fuelTank;
    private int horsePower;

    public Car(String color, String fuelSource, int horsePower) {
        this.color = color;
        this.fuelTank = fuelSource;
        this.horsePower = horsePower;
    }

    public void startEngine() {
        System.out.println("The " + fuelTank + " engine has been started. " + horsePower + " HP is ready to use.");
    }

    public void wash() {
        System.out.println("The " + color.toLowerCase() + " body shell's color is shinning.");
    }

    public void mechanicInspect() {
        System.out.println("All car's engine is ok.");
    }

    public void fillUpTank() {
        System.out.println("The car tank is " + fuelTank+".");
    }
}
