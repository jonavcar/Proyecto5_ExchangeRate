package com.banck.exchangerate.infraestructure.repository;

import com.banck.exchangerate.infraestructure.model.dao.ExchangerateDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 *
 * @author jonavcar
 */
public interface IExchangerateCrudRepository extends ReactiveCrudRepository<ExchangerateDao, String> {

}
