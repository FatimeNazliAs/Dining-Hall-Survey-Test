/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.Country;
import dininghall.domain.info.CountryVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface CountryDAO {
    public List<Country> getCountryList();

    public List<CountryVW> getCountryVWList();

    public Country getCountryByCountryId(int countryId);

    public CountryVW getCountryVWByCountryId(int countryId);

    public Country getCountryByCountryAlpha3Code(String countryAlpha3Code);

    public CountryVW getCountryVWByCountryAlpha3Code(String countryAlpha3Code);

    public Country getCountryByEnglishName(String englishName);

    public CountryVW getCountryVWByEnglishName(String englishName);

    public Country getCountryByNativeName(String nativeName);

    public CountryVW getCountryVWByNativeName(String nativeName);

    public boolean addCountry(Country country);

    public boolean updateCountry(Country country);

    public boolean updateCountry(CountryVW countryVW);

    public boolean deleteCountry(int countryId);

    public List<Country> getCountryListByContinentId(int continentId);


    public List<Country> getCountryVWListByEnglishName(String englishName);
}
