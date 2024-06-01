package com.stocksapp.springwebflux.stock_trading.exception;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorMessage {
    private String message;
}
