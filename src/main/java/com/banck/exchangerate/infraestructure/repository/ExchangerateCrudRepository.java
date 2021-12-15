package com.banck.exchangerate.infraestructure.repository;

import com.banck.exchangerate.domain.Exchangerate;
import com.banck.exchangerate.infraestructure.model.dao.ExchangerateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.banck.exchangerate.aplication.model.ExchangerateRepository;

/**
 *
 * @author jonavcar
 */
@Component
public class ExchangerateCrudRepository implements ExchangerateRepository {

    @Autowired
    IExchangerateCrudRepository crudRepository;

    @Override
    public Mono<Exchangerate> get(String debitcardaccount) {
        return crudRepository.findById(debitcardaccount).map(this::ExchangerateDaoToExchangerate);
    }

    @Override
    public Flux<Exchangerate> list() {
        return crudRepository.findAll().map(this::ExchangerateDaoToExchangerate);
    }

    @Override
    public Mono<Exchangerate> create(Exchangerate debitcardaccount) {
        return crudRepository.save(PersonToExchangerateDao(debitcardaccount)).map(this::ExchangerateDaoToExchangerate);
    }

    @Override
    public Mono<Exchangerate> update(String debitcardaccount, Exchangerate c) {
        return crudRepository.save(PersonToExchangerateDao(c)).map(this::ExchangerateDaoToExchangerate);
    }

    @Override
    public void delete(String debitcardaccount) {
        crudRepository.deleteById(debitcardaccount).subscribe();
    }

    public Exchangerate ExchangerateDaoToExchangerate(ExchangerateDao md) {
        Exchangerate m = new Exchangerate();
        m.setExchangerate(md.getExchangerate());
        m.setSalePrice(md.getSalePrice());
        m.setPurchasePrice(md.getPurchasePrice());
        m.setDivisa(md.getDivisa());
        m.setDate(md.getDate());
        return m;
    }

    public ExchangerateDao PersonToExchangerateDao(Exchangerate p) {
        ExchangerateDao pd = new ExchangerateDao();
        pd.setExchangerate(p.getExchangerate());
        pd.setSalePrice(p.getSalePrice());
        pd.setPurchasePrice(p.getPurchasePrice());
        pd.setDivisa(p.getDivisa());
        pd.setDate(p.getDate());
        return pd;
    }

}
