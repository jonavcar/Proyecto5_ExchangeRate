package com.banck.exchangerate.infraestructure.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import com.banck.exchangerate.domain.Exchangerate;
import com.banck.exchangerate.aplication.model.ExchangerateKafka;

@Component
public class ExchangerateKafkaProducer implements ExchangerateKafka {

    @Autowired
    private KafkaTemplate<String, Exchangerate> kafkaTemplate;
    private final String EXCHANGE_RATE = "topic-exchangerate";

    @Override
    public void create(Exchangerate wallet) {
        this.kafkaTemplate.send(EXCHANGE_RATE, wallet);

        ListenableFuture<SendResult<String, Exchangerate>> future = this.kafkaTemplate.send(EXCHANGE_RATE, wallet);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Exchangerate>>() {

            @Override
            public void onSuccess(SendResult<String, Exchangerate> result) {
                System.out.println("EXCHANGE_RATE=[" + wallet
                        + "] con offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Imposible enviar EXCHANGE_RATE message=["
                        + wallet + "] due to : " + ex.getMessage());
            }
        });
    }

}
