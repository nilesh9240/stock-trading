package com.stocksapp.springwebflux.stock_trading.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StockCreationException extends Throwable{
    private String message;
}
