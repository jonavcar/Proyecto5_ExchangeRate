package com.banck.exchangerate.domain;

import lombok.Data;

/**
 *
 * @author jonavcar
 */
@Data
public class Exchangerate {
    
    public String exchangerate;
    public String divisa;
    public double purchasePrice;
    public double salePrice;
    public String date;
}
