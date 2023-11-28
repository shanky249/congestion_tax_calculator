package com.volvocars.assignment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.volvocars.assignment.config.TaxConfig;
import com.volvocars.assignment.config.TaxConfig.TaxConfiguration;
import com.volvocars.assignment.config.TaxConfig.TaxConfiguration.TaxRules;
import com.volvocars.assignment.model.Diplomat;
import com.volvocars.assignment.model.interfaces.Vehicle;
import com.volvocars.assignment.service.CongestionTaxService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CongestionTaxServiceTest {

    @Mock
    private TaxConfig taxConfig;

    @Mock
    private TaxRules rules;

    @InjectMocks
    private CongestionTaxService congestionTaxService;

    @Test
    public void testGetTax_ValidInput_ReturnsTax() {
        try {
            String city = "Gothenburg";
            Vehicle vehicle = new Diplomat();
            Instant[] instants = { parseInstant("2013-01-01 21:00:00") };
            System.out.println(instants[0]);

            TaxConfiguration config = mock(TaxConfiguration.class);
            config.setCity(city);
            when(taxConfig.getCityConfig(eq(city))).thenReturn(config);

            TaxRules rules = mock(TaxRules.class);
            when(config.getRules()).thenReturn(rules);
            when(rules.getMaximumTaxAmountPerDay()).thenReturn(0);
            when(rules.getTollFreeDates()).thenReturn(anyList());

            assertNotNull("Config should not be null", taxConfig.getCityConfig(city));
            
            when(congestionTaxService.isTollFreeDate(anyString(), any())).thenReturn(false);
            when(congestionTaxService.isTollFreeVehicle(anyString(), vehicle)).thenReturn(true);
            when(congestionTaxService.getTollFee(city, any(), vehicle)).thenReturn(0);

            int result = congestionTaxService.getTax(city, vehicle, instants);

            assertEquals(0, result);
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception stack trace for debugging
            fail("Exception occurred: " + e.getMessage());
        }
    }

    private static Instant parseInstant(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }
}
