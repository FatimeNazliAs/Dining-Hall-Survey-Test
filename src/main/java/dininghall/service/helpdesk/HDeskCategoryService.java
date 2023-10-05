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
package dininghall.service.helpdesk;

import dininghall.dao.helpdesk.HDeskCategoryDAO;
import dininghall.dao.helpdesk.HDeskCategoryDAOImpl;
import dininghall.domain.helpdesk.HDeskCategory;
import dininghall.domain.helpdesk.HDeskCategoryVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "hDeskCategoryService")
@ApplicationScoped
public class HDeskCategoryService {

    private final static HDeskCategoryDAO hDeskCategoryDAO;

    static {

        hDeskCategoryDAO = new HDeskCategoryDAOImpl();

    }

    public List<HDeskCategory> getHDeskCategoryList(int size) {
        return hDeskCategoryDAO.getHDeskCategoryList();
    }


    public boolean updateHDeskCategory(HDeskCategory hDeskCategory) {
        return hDeskCategoryDAO.updateHDeskCategory(hDeskCategory);
    }

    public boolean updateHDeskCategory(HDeskCategoryVW hDeskCategoryVW) {
        return hDeskCategoryDAO.updateHDeskCategory(hDeskCategoryVW);
    }

    public boolean deleteHDeskCategory(HDeskCategory hDeskCategory) {
        return hDeskCategoryDAO.deleteHDeskCategory(hDeskCategory.getCatId());
    }

    public HDeskCategory getHDeskCategoryByHDeskCategoryId(int catId) {
        return hDeskCategoryDAO.getHDeskCategoryByHDeskCategoryId(catId);
    }

    public boolean insertHDeskCategory(HDeskCategory hDeskCategory) {
        return hDeskCategoryDAO.addHDeskCategory(hDeskCategory);
    }

    public List<HDeskCategory> getHDeskCategorySubListByParentCatId(int parentCatId) {
        return hDeskCategoryDAO.getHDeskCategorySubListByParentCatId(parentCatId);
    }

    public List<HDeskCategoryVW> getHDeskCategoryVWList(int i) {
        return hDeskCategoryDAO.getHDeskCategoryVWList();
    }
}
