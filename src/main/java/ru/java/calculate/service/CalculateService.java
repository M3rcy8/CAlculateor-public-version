package ru.java.calculate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.java.calculate.configuration.CalculateConfiguration;
import ru.java.calculate.dto.VacationPayResponse;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.math.RoundingMode.DOWN;
import static java.math.RoundingMode.HALF_UP;

@Service
@RequiredArgsConstructor
public class CalculateService {

    private final CalculateConfiguration calculateConfiguration;

    public VacationPayResponse vacationPay(BigDecimal averageMonthSalary, int vacationDuration, LocalDate startDate) {
        long numberOfDaysToBePayed = 0;
        LocalDate currentDate = startDate;
        while (numberOfDaysToBePayed < vacationDuration) {
            if (isWeekday(currentDate) && !isHoliday(currentDate)) {
                numberOfDaysToBePayed++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return vacationPay(averageMonthSalary, numberOfDaysToBePayed);
    }

    public VacationPayResponse vacationPay(BigDecimal averageMonthSalary, long numberOfDaysToBePayed) {
        averageMonthSalary = averageMonthSalary.setScale(12, HALF_UP);
        BigDecimal averageDaysInMonth = calculateConfiguration.getAverageDaysInMonth();
        BigDecimal averageDaySalary = averageMonthSalary.divide(averageDaysInMonth, HALF_UP);
        BigDecimal vacationPay = averageDaySalary.multiply(BigDecimal.valueOf(numberOfDaysToBePayed)).setScale(2, DOWN);
        BigDecimal tax = vacationPay.multiply(calculateConfiguration.getTax()).divide(new BigDecimal("100"), DOWN);
        return new VacationPayResponse(vacationPay.subtract(tax));
    }

    private boolean isHoliday(LocalDate date) {
        return calculateConfiguration.getUnpaidDays().stream()
                .anyMatch(holiday -> holiday.getMonthValue() == date.getMonthValue()
                        && holiday.getDayOfMonth() == date.getDayOfMonth());
    }

    private boolean isWeekday(LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY;
    }
}
