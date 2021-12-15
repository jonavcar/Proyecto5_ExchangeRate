package com.banck.exchangerate.aplication.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.banck.exchangerate.domain.Exchangerate;
import com.banck.exchangerate.aplication.ExchangerateKafkaOperations;
import com.banck.exchangerate.aplication.model.ExchangerateKafka;

@Service
@RequiredArgsConstructor
public class ExchangerateProducerOperationsIml implements ExchangerateKafkaOperations {

    Logger logger = LoggerFactory.getLogger(ExchangerateProducerOperationsIml.class);
    private final ExchangerateKafka walletKafka;

    @Override
    public void create(Exchangerate wallet) {
        walletKafka.create(wallet);
    }
}
