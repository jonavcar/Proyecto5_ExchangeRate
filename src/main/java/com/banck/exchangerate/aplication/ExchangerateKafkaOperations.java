package com.banck.exchangerate.aplication;
 
import com.banck.exchangerate.domain.Exchangerate;


public interface ExchangerateKafkaOperations{

    public void create(Exchangerate wallet);
}
