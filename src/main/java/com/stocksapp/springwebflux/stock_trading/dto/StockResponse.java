package com.stocksapp.springwebflux.stock_trading.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stocksapp.springwebflux.stock_trading.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private String id;

    @JsonProperty("stockName")
    private String name;

    private BigDecimal price;

    private String currency;

    public static StockResponse fromModel(Stock stock){
        return StockResponse.builder()
                .id(stock.getId())
                .name(stock.getName())
                .price(stock.getPrice())
                .currency(stock.getCurrency())
                .build();
    }
}
