package ru.java.calculate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.java.calculate.dto.VacationPayResponse;
import ru.java.calculate.service.CalculateService;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CalculateController {

    private final CalculateService calculateService;

    @GetMapping("/calculate")
    public VacationPayResponse calculateVacationPay(@RequestParam(name = "averageSalary") BigDecimal averageSalary,
                                                    @RequestParam(name = "vacationDuration") int vacationDuration,
                                                    @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return calculateService.vacationPay(averageSalary, vacationDuration, startDate);
    }
}
