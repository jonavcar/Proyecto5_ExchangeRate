package com.banck.exchangerate.infraestructure.rest;

import com.banck.exchangerate.domain.Exchangerate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import com.banck.exchangerate.utils.Status;
import org.springframework.http.HttpStatus;
import com.banck.exchangerate.aplication.ExchangerateOperations;

/**
 *
 * @author jonavcar
 */
@RestController
@RequestMapping("/exchangerate")
@RequiredArgsConstructor
public class ExchangerateController {

    Logger logger = LoggerFactory.getLogger(ExchangerateController.class);
    private final ExchangerateOperations operations;

    @GetMapping
    public Flux<Exchangerate> listAll() {
        return operations.list();
    }

    @GetMapping("/{id}")
    public Mono<Exchangerate> get(@PathVariable("id") String id) {
        return operations.get(id);
    }

    @PostMapping
    public Mono<ResponseEntity> create(@RequestBody Exchangerate wallet) {
        return operations.create(wallet).flatMap(pR -> {
            if (pR.getStatus() == Status.OK) {
                return Mono.just(new ResponseEntity(pR.getData(), HttpStatus.OK));
            } else {
                return Mono.just(new ResponseEntity(pR.getMessage(), HttpStatus.BAD_REQUEST));
            }
        });
    }

    @PutMapping("/{id}")
    public Mono<Exchangerate> update(@PathVariable("id") String id, @RequestBody Exchangerate wallet) {
        return operations.update(id, wallet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        operations.delete(id);
    }

}
