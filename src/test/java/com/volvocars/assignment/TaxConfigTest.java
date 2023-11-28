package com.volvocars.assignment;

import org.junit.Test;

import com.volvocars.assignment.config.TaxConfig;
import com.volvocars.assignment.exceptions.ConfigurationNotFoundException;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TaxConfigTest {

    @Test
    public void testGetCityConfig_ValidCity_ReturnsConfig() {
        TaxConfig.TaxConfiguration config = new TaxConfig.TaxConfiguration();
        config.setCity("Stockholm");

        TaxConfig taxConfig = new TaxConfig();
        taxConfig.setConfigurations(Arrays.asList(config));

        assertEquals(config, taxConfig.getCityConfig("stockholm"));
    }

    @Test(expected = ConfigurationNotFoundException.class)
    public void testGetCityConfig_NonExistingCity_ThrowsException() {
        TaxConfig taxConfig = new TaxConfig();
        taxConfig.setConfigurations(Arrays.asList());

        taxConfig.getCityConfig("NonExistingCity");
    }

}
