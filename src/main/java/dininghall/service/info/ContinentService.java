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

import dininghall.dao.info.ContinentDAO;
import dininghall.dao.info.ContinentDAOImpl;
import dininghall.domain.info.Continent;
import dininghall.domain.info.ContinentVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "continentService")
@ApplicationScoped
public class ContinentService {
    private final static ContinentDAO continentDAO;

    static {
        continentDAO = new ContinentDAOImpl();
    }

    public List<Continent> getContinentList(int size) {
        List<Continent> list = continentDAO.getContinentList();

        return list;
    }


    public boolean updateContinent(Continent continent) {
        return continentDAO.updateContinent(continent);
    }

    public boolean deleteContinent(Continent continent) {
        return continentDAO.deleteContinent(continent.getContinentId());
    }

    public Continent getContinentByContinentId(int continentId) {
        return continentDAO.getContinentByContinentId(continentId);
    }

    public boolean insertContinent(Continent continent) {
        return continentDAO.addContinent(continent);
    }

    public ContinentVW getContinentVWByContinentId(int continentId) {
        return continentDAO.getContinentVWByContinentId(continentId);
    }

    public List<ContinentVW> getContinentVWList(int i) {
        return continentDAO.getContinentVWList();
    }

    public boolean updateContinent(ContinentVW selectedContinentVW) {
        return continentDAO.updateContinent(selectedContinentVW);
    }
}
