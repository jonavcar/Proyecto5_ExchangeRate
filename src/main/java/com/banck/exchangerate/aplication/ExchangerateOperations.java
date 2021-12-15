package com.banck.exchangerate.aplication;

import com.banck.exchangerate.domain.Exchangerate;
import com.banck.exchangerate.aplication.impl.ResponseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author jonavcar
 */
public interface ExchangerateOperations {

    public Flux<Exchangerate> list();

    public Mono<Exchangerate> get(String exchangerate);

    public Mono<ResponseService> create(Exchangerate exchangerate); 

    public Mono<Exchangerate> update(String id, Exchangerate exchangerate);

    public void delete(String id);

}
