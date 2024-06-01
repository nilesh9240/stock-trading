package com.stocksapp.springwebflux.stock_trading.controller;

import com.stocksapp.springwebflux.stock_trading.dto.StockRequest;
import com.stocksapp.springwebflux.stock_trading.dto.StockResponse;
import com.stocksapp.springwebflux.stock_trading.model.Stock;
import com.stocksapp.springwebflux.stock_trading.repository.StocksRepository;
import com.stocksapp.springwebflux.stock_trading.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StocksController {

    @Autowired
    private StocksService stocksService;

    @GetMapping("/{id}")
    public Mono<StockResponse> getOneStock(@PathVariable String id){
        return stocksService.getOneStock(id);
    }

//    @GetMapping("/{name}")
//    public Mono<Stock> getStockByName(@PathVariable String name){
//        return stocksService.getStockByName
//    }

    @GetMapping
    public Flux<StockResponse> getAllStocks(
            @RequestParam(required = false, defaultValue = "0")
            BigDecimal priceGreaterThan
    ){
        return stocksService.getAllStocks(priceGreaterThan);
    }

    @PostMapping
    public Mono<StockResponse> createStock(@RequestBody StockRequest stockRequest){

        return stocksService.createStock(stockRequest);
    }
}

