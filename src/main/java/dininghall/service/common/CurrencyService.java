/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dininghall.service.common;


import dininghall.dao.common.CurrencyDAO;
import dininghall.dao.common.CurrencyDAOImpl;
import dininghall.domain.common.Currency;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "currencyService")
@ApplicationScoped
public class CurrencyService {
    private final static CurrencyDAO currencyDAO;

    static {
        currencyDAO = new CurrencyDAOImpl();
    }

    public List<Currency> getCurrencyList(int size) {
        List<Currency> list = currencyDAO.getCurrencyList();

        return list;
    }

    public Currency getCurrencyByCurrencyId(int currencyId) {
        Currency currency = currencyDAO.getCurrencyByCurrencyId(currencyId);

        return currency;
    }

}
