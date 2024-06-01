package com.stocksapp.springwebflux.stock_trading.repository;

import com.stocksapp.springwebflux.stock_trading.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StocksRepository extends ReactiveMongoRepository<Stock, String> {
    public Mono<Stock> getStockByName(String name);
}
