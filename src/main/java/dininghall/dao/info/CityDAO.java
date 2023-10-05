/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.City;
import dininghall.domain.info.CityVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface CityDAO {
    List<City> getCityList();

    List<CityVW> getCityVWList();

    City getCityByCityId(int cityId);

    CityVW getCityVWByCityId(int cityId);

    City getCityByCityCode(String cityCode);

    CityVW getCityVWByCityCode(String cityCode);

    City getCityByEnglishName(String englishName);

    CityVW getCityVWByEnglishName(String englishName);

    City getCityByNativeName(String nativeName);

    CityVW getCityVWByNativeName(String nativeName);

    boolean addCity(City city);

    boolean updateCity(City city);

    boolean updateCity(CityVW city);

    boolean deleteCity(int cityId);

    List<City> getCityListBySubDivisionId(int subDivisionId);

    List<City> getCityListByCountryId(int countryId);
}
