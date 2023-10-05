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

import dininghall.dao.info.SubDivisionDAO;
import dininghall.dao.info.SubDivisionDAOImpl;
import dininghall.domain.info.SubDivision;
import dininghall.domain.info.SubDivisionVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "subDivisionService")
@ApplicationScoped
public class SubDivisionService {
    private final static SubDivisionDAO subDivisionDAO;

    static {
        subDivisionDAO = new SubDivisionDAOImpl();
    }

    public List<SubDivision> getSubDivisionList(int size) {
        List<SubDivision> list = subDivisionDAO.getSubDivisionList();

        return list;
    }


    public boolean updateSubDivision(SubDivision subDivision) {
        return subDivisionDAO.updateSubDivision(subDivision);
    }

    public boolean deleteSubDivision(SubDivision subDivision) {
        return subDivisionDAO.deleteSubDivision(subDivision.getSubDivisionId());
    }

    public SubDivision getSubDivisionBySubDivisionId(int subDivisionId) {
        return subDivisionDAO.getSubDivisionBySubDivisionId(subDivisionId);
    }

    public boolean insertSubDivision(SubDivision subDivision) {
        return subDivisionDAO.addSubDivision(subDivision);
    }

    public List<SubDivision> getSubDivisionListByCountryId(int countryId) {
        return subDivisionDAO.getSubDivisionListByCountryId(countryId);
    }

    public List<SubDivisionVW> getSubDivisionVWList(int i) {
        return subDivisionDAO.getSubDivisionVWList();
    }

    public SubDivisionVW getSubDivisionVWBySubDivisionId(int subDivisionId) {
        return subDivisionDAO.getSubDivisionVWBySubDivisionId(subDivisionId);
    }

    public boolean updateSubDivision(SubDivisionVW selectedSubDivisionVW) {
        return subDivisionDAO.updateSubDivision(selectedSubDivisionVW);
    }
}
