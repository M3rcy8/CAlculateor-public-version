package ru.java.calculate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class VacationPayResponse {
    private BigDecimal sumVacationPay;
}
