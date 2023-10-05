/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.domain.helpdesk.HDeskCategory;
import dininghall.domain.helpdesk.HDeskCategoryVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface HDeskCategoryDAO {
    public List<HDeskCategory> getHDeskCategoryList();

    public HDeskCategory getHDeskCategoryByHDeskCategoryId(int catId);

    public HDeskCategory getHDeskCategoryByName(String name);

    public boolean addHDeskCategory(HDeskCategory hDeskCategory);

    public boolean updateHDeskCategory(HDeskCategory hDeskCategory);

    public boolean updateHDeskCategory(HDeskCategoryVW hDeskCategoryVW);

    public boolean deleteHDeskCategory(int catId);

    public List<HDeskCategory> getHDeskCategorySubListByParentCatId(int parentCatId);

    public List<HDeskCategoryVW> getHDeskCategoryVWList();
}
