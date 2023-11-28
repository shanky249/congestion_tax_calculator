package com.volvocars.assignment.model;

import com.volvocars.assignment.model.interfaces.Vehicle;

public class Motorcycle implements Vehicle {
    @Override
    public String getVehicleType() {
        return "Motorcycle";
    }
}
