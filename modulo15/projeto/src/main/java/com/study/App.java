package com.study;

public class App {
    public static void main(String[] args) {
        Customer customer = new Customer("A", "manual");
        Factory factory = getFactory(customer);
        Car car = factory.create(customer.getCarModel());
        car.startEngine();

    }

    private static Factory getFactory(Customer customer) {
        if (customer.getCarTransmission().equalsIgnoreCase("manual")) {
            return new ManualTransmissionCarFactory();
        } else {
            return new AutomaticTransmissionCarFactory();
        }
    }
}
