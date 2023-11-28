package com.volvocars.assignment.service;

import org.springframework.stereotype.Service;

import com.volvocars.assignment.config.TaxConfig;
import com.volvocars.assignment.model.interfaces.Vehicle;

import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CongestionTaxService {

    public final TaxConfig cityTaxConfig;

    public int getTax(String city, Vehicle vehicle, Instant[] instants) {
        try {
            Instant intervalStart = instants[0];
            int totalFee = 0;
    
            for (Instant instant : instants) {
                int nextFee = getTollFee(city, instant, vehicle);
                int tempFee = getTollFee(city, intervalStart, vehicle);
    
                long minutes = Duration.between(intervalStart, instant).toMinutes();
    
                if (minutes <= 60) {
                    totalFee = Math.max(totalFee - tempFee, nextFee);
                } else {
                    totalFee += nextFee;
                }
            }
    
            int maxDailyTax = cityTaxConfig.getCityConfig(city).getRules().getMaximumTaxAmountPerDay();
            return Math.min(totalFee, maxDailyTax);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating tax.", e);
        }
    }    

    public boolean isTollFreeVehicle(String city, Vehicle vehicle) {
        try {
            if (vehicle == null) return false;
            String vehicleType = vehicle.getVehicleType();
            List<String> taxExemptVehicles = cityTaxConfig.getCityConfig(city).getRules().getTaxExemptVehicles();
            return taxExemptVehicles.contains(vehicleType);
        } catch (Exception e) {
            throw new RuntimeException("Error checking toll-free vehicle.", e);
        }
    }

    public int getTollFee(String city, Instant instant, Vehicle vehicle) {
        try {
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            if (isTollFreeDate(city, localDate) || isTollFreeVehicle(city, vehicle)) return 0;

            int hour = instant.atZone(ZoneId.systemDefault()).getHour();
            int minute = instant.atZone(ZoneId.systemDefault()).getMinute();

            List<TaxConfig.TaxConfiguration.TaxRules.TimeFeeMapping> timeFeeMapping =
                    cityTaxConfig.getCityConfig(city).getRules().getTimeFeeMapping();

            for (TaxConfig.TaxConfiguration.TaxRules.TimeFeeMapping mapping : timeFeeMapping) {
                if (hour > mapping.getStartHour() || (hour == mapping.getStartHour() && minute >= mapping.getStartMinute())) {
                    if (hour < mapping.getEndHour() || (hour == mapping.getEndHour() && minute <= mapping.getEndMinute())) {
                        return mapping.getRate();
                    }
                }
            }

            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error getting toll fee.", e);
        }
    }

    public boolean isTollFreeDate(String city, LocalDate localDate) {
        try {
            List<String> tollFreeDates = cityTaxConfig.getCityConfig(city).getRules().getTollFreeDates();

            int month = localDate.getMonthValue();

            if (month == 7) { //July month
                return true;
            }

            DayOfWeek day = localDate.getDayOfWeek();

            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) { // Weekends
                return true;
            }

            int dayOfMonth = localDate.getDayOfMonth();

            for (String tollFreeDate : tollFreeDates) {
                LocalDate tollFreeLocalDate = LocalDate.parse(tollFreeDate);
                if (isDayBeforePublicHoliday(localDate, tollFreeLocalDate)) {
                    return true;
                }
                String[] dateParts = tollFreeDate.split("-");
                int tollFreeMonth = Integer.parseInt(dateParts[0]);
                int tollFreeDay = Integer.parseInt(dateParts[1]);

                if (month == tollFreeMonth && dayOfMonth == tollFreeDay) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error checking toll-free date.", e);
        }
    }

    private boolean isDayBeforePublicHoliday(LocalDate date, LocalDate publicHoliday) {
        return date.equals(publicHoliday.minusDays(1));
    }
    
}
