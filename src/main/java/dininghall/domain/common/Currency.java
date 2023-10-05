package dininghall.domain.common;

/**
 * @author Barış Meral
 * @version 1.0.1
 * <code>com.github.barismeral.dovizAPI.Currency</code> <b>Interface</b>
 * <p>get current Date, get Money Name, get Buying & Selling Price, is forex ?</p>
 * @since 12.15.2018
 */


public class Currency {

    private int currencyId;
    private String date;
    private String name;
    private double buyingPrice;
    private double sellingPrice;
    private int isForex;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getIsForex() {
        return isForex;
    }

    public void setIsForex(int isForex) {
        this.isForex = isForex;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }
}