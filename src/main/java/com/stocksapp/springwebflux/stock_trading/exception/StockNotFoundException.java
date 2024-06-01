package com.stocksapp.springwebflux.stock_trading.exception;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockNotFoundException extends Throwable{
    private String message;
}
