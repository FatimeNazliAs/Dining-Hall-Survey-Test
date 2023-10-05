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

import dininghall.dao.info.CityDAO;
import dininghall.dao.info.CityDAOImpl;
import dininghall.domain.info.City;
import dininghall.domain.info.CityVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "cityService")
@ApplicationScoped
public class CityService {
    private final static CityDAO cityDAO;

    static {
        cityDAO = new CityDAOImpl();
    }

    public List<City> getCityList(int size) {
        List<City> list = cityDAO.getCityList();

        return list;
    }


    public boolean updateCity(City city) {
        return cityDAO.updateCity(city);
    }

    public boolean updateCity(CityVW cityVW) {
        return cityDAO.updateCity(cityVW);
    }

    public boolean deleteCity(City city) {
        return cityDAO.deleteCity(city.getCityId());
    }

    public City getCityByCityId(int cityId) {
        return cityDAO.getCityByCityId(cityId);
    }

    public boolean insertCity(City city) {
        return cityDAO.addCity(city);
    }

    public List<City> getCityListBySubDivisionId(int subDivisionId) {
        return cityDAO.getCityListBySubDivisionId(subDivisionId);
    }

    public List<CityVW> getCityVWList(int i) {
        return cityDAO.getCityVWList();
    }

    public CityVW getCityVWByCityId(int cityId) {
        return cityDAO.getCityVWByCityId(cityId);
    }

    public List<City> getCityListByCountryId(int countryId) {
        return cityDAO.getCityListByCountryId(countryId);
    }
}
