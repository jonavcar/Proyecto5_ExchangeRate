package com.banck.exchangerate.infraestructure.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author jonavcar
 */
@Data
@Document("exchangerate")
public class ExchangerateDao {

    @Id
    public String exchangerate;
    public String divisa;
    public double purchasePrice;
    public double salePrice;
    public String date;
}
