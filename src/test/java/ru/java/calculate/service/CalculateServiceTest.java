package ru.java.calculate.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.java.calculate.configuration.CalculateConfiguration;
import ru.java.calculate.dto.VacationPayResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CalculateServiceTest {

    @Autowired
    private CalculateService calculateService;

    @Mock
    private CalculateConfiguration calculateConfiguration;

    @Test
    void testVacationPay() {
        final var averageNumberOfDaysInMonth = "29.3";
        final var personalIncomeTaxValueAsPercentage = "13";
        final var daysThatNotPaid = List.of(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 1, 3)
        );

        when(calculateConfiguration.getAverageDaysInMonth()).thenReturn(new BigDecimal(averageNumberOfDaysInMonth));
        when(calculateConfiguration.getTax()).thenReturn(new BigDecimal(personalIncomeTaxValueAsPercentage));
        when(calculateConfiguration.getUnpaidDays()).thenReturn(daysThatNotPaid);

        var averageMonthSalary = new BigDecimal("3000");
        var numberOfDaysToBePaid = 5;
        var result = calculateService.vacationPay(averageMonthSalary, numberOfDaysToBePaid);
        var actual = new VacationPayResponse(new BigDecimal("445.39"));

        assertEquals(actual.getSumVacationPay(), result.getSumVacationPay());
    }
}
