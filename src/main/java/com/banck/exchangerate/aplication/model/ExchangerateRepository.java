package com.banck.exchangerate.aplication.model;

import com.banck.exchangerate.domain.Exchangerate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author jonavcar
 */
public interface ExchangerateRepository {

    public Flux<Exchangerate> list();

    public Mono<Exchangerate> get(String id);

    public Mono<Exchangerate> create(Exchangerate d);

    public Mono<Exchangerate> update(String id, Exchangerate d);

    public void delete(String id);
}
