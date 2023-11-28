package com.volvocars.assignment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.volvocars.assignment.exceptions.ConfigurationNotFoundException;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "tax-configurations")
@Data
public class TaxConfig {

    private List<TaxConfiguration> configurations;

    @Data
    public static class TaxConfiguration {
        private String city;
        private TaxRules rules;

        @Data
        public static class TaxRules {
            private int maximumTaxAmountPerDay;
            private int singleChargePeriod;
            private List<TimeFeeMapping> timeFeeMapping;
            private List<String> taxExemptVehicles;
            private List<String> tollFreeDates;

            @Data
            public static class TimeFeeMapping {
                private int startHour;
                private int startMinute;
                private int endHour;
                private int endMinute;
                private int rate;
            }
        }
    }

    public TaxConfiguration getCityConfig(String city) {
        return configurations.stream()
                .filter(config -> config.getCity().equalsIgnoreCase(city))
                .findFirst()
                .orElseThrow(() -> new ConfigurationNotFoundException("Configuration not found for city: " + city));
    }
}
