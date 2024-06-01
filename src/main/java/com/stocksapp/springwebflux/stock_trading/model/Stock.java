package com.stocksapp.springwebflux.stock_trading.model;


import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {
    @Id
    private String id;
    private String name;
    @NonNull
    private BigDecimal price;
    private String currency;
}