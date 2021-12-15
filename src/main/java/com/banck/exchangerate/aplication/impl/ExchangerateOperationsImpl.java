package com.banck.exchangerate.aplication.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.banck.exchangerate.domain.Exchangerate;
import com.banck.exchangerate.utils.EmailValidation;
import com.banck.exchangerate.utils.Status;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.banck.exchangerate.aplication.ExchangerateOperations;
import com.banck.exchangerate.aplication.ExchangerateKafkaOperations;
import com.banck.exchangerate.aplication.model.ExchangerateRepository;

/**
 *
 * @author jonavcar
 */
@Service
@RequiredArgsConstructor
public class ExchangerateOperationsImpl implements ExchangerateOperations {

    Logger logger = LoggerFactory.getLogger(ExchangerateOperationsImpl.class);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Bogota"));

    private final ExchangerateRepository exchangerateRepository;
    public ResponseService responseService;
    public final ExchangerateKafkaOperations kafkaOperations;

    @Override
    public Flux<Exchangerate> list() {
        return exchangerateRepository.list();
    }

    @Override
    public Mono<Exchangerate> get(String exchangerate) {
        return exchangerateRepository.get(exchangerate);
    }

    @Override
    public Mono<ResponseService> create(Exchangerate exchangerate) {
        return validateDataexchangerateToCreate(exchangerate).flatMap(RS -> {
            responseService = RS;
            if (responseService.getStatus() == Status.OK) {
                return insertExchangerate(exchangerate);
            } else {
                return Mono.just(responseService);
            }
        });
    }

    @Override
    public Mono<Exchangerate> update(String exchangerate, Exchangerate c) {
        return exchangerateRepository.update(exchangerate, c);
    }

    @Override
    public void delete(String exchangerate) {
        exchangerateRepository.delete(exchangerate);
    }

    public Mono<ResponseService> insertExchangerate(Exchangerate exchangerate) {
        responseService = new ResponseService();
        exchangerate.setDate(dateTime.format(formatDate));
        exchangerate.setDivisa("BootCoin");
        exchangerate.setPurchasePrice(exchangerate.getSalePrice());
        exchangerate.setExchangerate(exchangerate.getDate().replaceAll("-", "") + "-" + exchangerate.getDivisa());
        return exchangerateRepository.create(exchangerate).flatMap(w -> {
            responseService.setStatus(Status.OK);
            responseService.setData(w);
            //Enviar Exchangerate a Kafka para su registro en otros microservicios
            kafkaOperations.create(exchangerate);
            return Mono.just(responseService);
        });
    }

    public Mono<ResponseService> validateDataexchangerateToCreate(Exchangerate exchangerate) {
        responseService = new ResponseService();
        responseService.setStatus(Status.ERROR);
        return Mono.just(exchangerate).flatMap(fm -> {
            if (!Optional.ofNullable(exchangerate.getSalePrice()).isPresent() || exchangerate.getSalePrice() <= 0) {
                responseService.setMessage("De ingresar el precio, Ejemplo: salePrice: 2.30 ");
                return Mono.just(responseService);
            }
            responseService.setStatus(Status.OK);
            responseService.setData(fm);
            return Mono.just(responseService);
        });
    }
}
