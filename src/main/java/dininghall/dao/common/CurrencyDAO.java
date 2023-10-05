/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import dininghall.domain.common.Currency;

import java.util.List;

/**
 * @author Tolga
 */
public interface CurrencyDAO {
    public List<Currency> getCurrencyList();

    public Currency getCurrencyByCurrencyId(int currencyId);

    public Currency getCurrencyByName(String name);

    public boolean addCurrency(Currency currency);

    public boolean updateCurrency(Currency currency);

    public boolean deleteCurrency(int currencyId);


}
