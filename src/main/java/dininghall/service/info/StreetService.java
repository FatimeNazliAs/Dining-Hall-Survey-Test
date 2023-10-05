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

import dininghall.dao.info.StreetDAO;
import dininghall.dao.info.StreetDAOImpl;
import dininghall.domain.info.Street;
import dininghall.domain.info.StreetVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "streetService")
@ApplicationScoped
public class StreetService {

    private final static StreetDAO streetDAO;

    static {
        streetDAO = new StreetDAOImpl();
    }

    public List<Street> getStreetList(int size) {
        List<Street> list = streetDAO.getStreetList();

        return list;
    }


    public boolean updateStreet(Street street) {
        return streetDAO.updateStreet(street);
    }

    public boolean deleteStreet(Street street) {
        return streetDAO.deleteStreet(street.getStreetId());
    }

    public Street getStreetByStreetId(int streetId) {
        return streetDAO.getStreetByStreetId(streetId);
    }

    public boolean insertStreet(Street street) {
        return streetDAO.addStreet(street);
    }

    public List<StreetVW> getStreetVWList(int size) {
        return streetDAO.getStreetVWList();
    }

    public boolean updateStreet(StreetVW streetVW) {
        return streetDAO.updateStreet(streetVW);
    }

    public StreetVW getStreetVWByStreetId(int streetId) {
        return streetDAO.getStreetVWByStreetId(streetId);
    }

    public List<Street> getStreetListByStateId(int stateId) {
        return streetDAO.getStreetListByStateId(stateId);
    }
}
