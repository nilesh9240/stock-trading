package com.stocksapp.springwebflux.stock_trading.service;

import com.stocksapp.springwebflux.stock_trading.dto.StockRequest;
import com.stocksapp.springwebflux.stock_trading.dto.StockResponse;
import com.stocksapp.springwebflux.stock_trading.exception.StockCreationException;
import com.stocksapp.springwebflux.stock_trading.exception.StockNotFoundException;
import com.stocksapp.springwebflux.stock_trading.model.Stock;
import com.stocksapp.springwebflux.stock_trading.repository.StocksRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class StocksService {

    @Autowired
    private StocksRepository stocksRepository;

    public Mono<StockResponse> getOneStock(String id){
        return stocksRepository.findById(id)
                .map(StockResponse::fromModel)
                .switchIfEmpty(Mono.error(new StockNotFoundException("Stock not found with id: " + id)));
    }

    public Flux<StockResponse> getAllStocks(BigDecimal priceGreaterThan) {
        return stocksRepository.findAll()
                .filter(stock -> stock.getPrice().compareTo(priceGreaterThan) > 0)
                .map(StockResponse::fromModel);

    }

    public Mono<StockResponse> createStock(StockRequest stockRequest){
        return Mono.just(stockRequest)
                .map(StockRequest::toModel)
                .flatMap(stock -> stocksRepository.save(stock))
                .map(StockResponse::fromModel)
//                .onErrorReturn(StockResponse.builder().build());
                .onErrorMap(ex -> new StockCreationException(ex.getMessage()));
    }
}
