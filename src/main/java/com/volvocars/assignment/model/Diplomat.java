package com.volvocars.assignment.model;

import com.volvocars.assignment.model.interfaces.Vehicle;

public class Diplomat implements Vehicle {
    @Override
    public String getVehicleType() {
        return "Diplomat";
    }
}
