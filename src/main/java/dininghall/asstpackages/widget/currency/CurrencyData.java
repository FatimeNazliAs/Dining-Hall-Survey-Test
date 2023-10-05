package dininghall.asstpackages.widget.currency;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrencyData implements Serializable {
    private String currencyName;
    private float currencySellingPrice;
    private float currencyBuyingPrice;
    private int isForex;
    private String date;


}