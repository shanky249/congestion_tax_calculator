package com.volvocars.assignment.model;

import com.volvocars.assignment.model.interfaces.Vehicle;

public class Military implements Vehicle {
    @Override
    public String getVehicleType() {
        return "Military";
    }
}
