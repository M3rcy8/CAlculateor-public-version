package ru.java.calculate.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "calculate")
public class CalculateConfiguration {

    private BigDecimal averageDaysInMonth;
    private BigDecimal tax;
    private List<String> unpaidDays;

    public List<LocalDate> getUnpaidDays() {
        List<LocalDate> dates = new ArrayList<>();
        for (String dateString : unpaidDays) {
            String[] parts = dateString.split("-");
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            dates.add(LocalDate.of(Year.now().getValue(), month, day));
        }
        return dates;
    }
}
