package com.example.stocksv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockDTO {
    String name;
    Double amount;
}
