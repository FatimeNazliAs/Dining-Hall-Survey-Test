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
package dininghall.service.info;

import dininghall.dao.info.CountryDAO;
import dininghall.dao.info.CountryDAOImpl;
import dininghall.domain.info.Country;
import dininghall.domain.info.CountryVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "countryService")
@ApplicationScoped
public class CountryService {
    private final static CountryDAO countryDAO;

    static {
        countryDAO = new CountryDAOImpl();
    }

    public List<Country> getCountryList(int size) {
        List<Country> list = countryDAO.getCountryList();

        return list;
    }


    public boolean updateCountry(Country country) {
        return countryDAO.updateCountry(country);
    }

    public boolean deleteCountry(Country country) {
        return countryDAO.deleteCountry(country.getCountryId());
    }

    public Country getCountryByCountryId(int countryId) {
        return countryDAO.getCountryByCountryId(countryId);
    }

    public boolean insertCountry(Country country) {
        return countryDAO.addCountry(country);
    }

    public List<Country> getCountryListByContinentId(int continentId) {
        return countryDAO.getCountryListByContinentId(continentId);
    }

    public List<CountryVW> getCountryVWList(int i) {
        return countryDAO.getCountryVWList();
    }

    public boolean updateCountry(CountryVW countryVW) {
        return countryDAO.updateCountry(countryVW);
    }

    public CountryVW getCountryVWByCountryId(int countryId) {
        return countryDAO.getCountryVWByCountryId(countryId);
    }

    public List<Country> getCountryListByEnglishName(String englishName) {
        return countryDAO.getCountryVWListByEnglishName(englishName);
    }
}
