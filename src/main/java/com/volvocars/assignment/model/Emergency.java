package com.volvocars.assignment.model;

import com.volvocars.assignment.model.interfaces.Vehicle;

public class Emergency implements Vehicle {
    @Override
    public String getVehicleType() {
        return "Emergency";
    }
}
