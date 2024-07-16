package com.study;

public class Customer {

    private String carModel;
    private String carTransmission;

    public Customer(String carModel, String carTransmission) {
        this.carModel = carModel;
        this.carTransmission = carTransmission;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarTransmission() {
        return carTransmission;
    }

    public void setCarTransmission(String carTransmission) {
        this.carTransmission = carTransmission;
    }
}
