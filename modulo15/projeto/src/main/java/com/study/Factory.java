package com.study;

public abstract class Factory {

    public Car create(String carModel) {
        Car car = retrieveCar(carModel);
        car = prepareCar(car);
        return car;
    }

    private Car prepareCar(Car car) {
        car.wash();
        car.mechanicInspect();
        car.fillUpTank();
        return car;
    }

    abstract Car retrieveCar(String carModel);

}
