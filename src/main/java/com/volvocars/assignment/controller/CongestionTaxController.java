package com.volvocars.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.volvocars.assignment.exceptions.InvalidInputException;
import com.volvocars.assignment.model.interfaces.Vehicle;
import com.volvocars.assignment.model.requests.CalculationRequest;
import com.volvocars.assignment.service.CongestionTaxService;

import java.time.Instant;

@RestController
public class CongestionTaxController {

    private final CongestionTaxService congestionTaxService;

    @Autowired
    public CongestionTaxController(CongestionTaxService congestionTaxService) {
        this.congestionTaxService = congestionTaxService;
    }

    @PostMapping("/calculate-tax/{city}")
    public int calculateTax(@PathVariable String city, @RequestBody CalculationRequest request) {
        Vehicle vehicle;
        Instant[] instants;
        try {
            vehicle = request.getVehicle();
            instants = request.getDates();
        } catch (Exception e) {
            throw new InvalidInputException("Invalid input parameters for tax calculation.");
        }
        return congestionTaxService.getTax(city, vehicle, instants);
    }
}
