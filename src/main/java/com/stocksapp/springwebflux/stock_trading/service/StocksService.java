package com.stocksapp.springwebflux.stock_trading.service;

import com.stocksapp.springwebflux.stock_trading.dto.StockRequest;
import com.stocksapp.springwebflux.stock_trading.dto.StockResponse;
import com.stocksapp.springwebflux.stock_trading.exception.StockCreationException;
import com.stocksapp.springwebflux.stock_trading.exception.StockNotFoundException;
import com.stocksapp.springwebflux.stock_trading.model.Stock;
import com.stocksapp.springwebflux.stock_trading.repository.StocksRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class StocksService {

    private static final Logger log = LoggerFactory.getLogger(StocksService.class);

    @Autowired
    private StocksRepository stocksRepository;

    public Mono<StockResponse> getOneStock(String id){
        return stocksRepository.findById(id)
                .map(StockResponse::fromModel)
                .switchIfEmpty(Mono.error(
                        new StockNotFoundException(
                                "Stock not found with id: " + id)))
                                .doFirst(() -> log.info("Retrieving Stock with id: {}", id))
                                .doOnNext(stock -> log.info("Stock found: {}", stock))
                                .doOnError(ex -> log.error("Something went wrong happened while retrieving stock with id: {}", id, ex))
                                .doOnTerminate(() -> log.info("Finalized retrieving stock"))
                                .doFinally(signalType -> log.info("Finished stock retrieval with signal type: {}", signalType));
    }

    public Flux<StockResponse> getAllStocks(BigDecimal priceGreaterThan) {
        return stocksRepository.findAll()
                .filter(stock -> stock.getPrice().compareTo(priceGreaterThan) > 0)
                .map(StockResponse::fromModel)
                .doFirst(() -> log.info("Retrieving all stocks"))
                .doOnNext(stock -> log.info("Stock found: {}", stock))
                .doOnError(ex -> log.error("Something went wrong happened while retrieving stocks", ex))
                .doOnTerminate(() -> log.info("Finalized retrieving stock"))
                .doFinally(signalType -> log.info("Finished stock retrieval with signal type: {}", signalType));


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
