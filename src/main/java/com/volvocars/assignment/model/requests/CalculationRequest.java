package com.volvocars.assignment.model.requests;

import java.time.Instant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.volvocars.assignment.model.interfaces.Vehicle;
import com.volvocars.assignment.model.requests.deserializer.CalculationRequestDeserializer;

import lombok.Data;

@Data
@JsonDeserialize(using = CalculationRequestDeserializer.class)
public class CalculationRequest {

    private Vehicle vehicle;
    private Instant[] dates;
}
