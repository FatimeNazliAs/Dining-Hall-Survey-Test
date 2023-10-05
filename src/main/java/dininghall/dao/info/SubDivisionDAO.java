/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.SubDivision;
import dininghall.domain.info.SubDivisionVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface SubDivisionDAO {
    public List<SubDivision> getSubDivisionList();

    public List<SubDivisionVW> getSubDivisionVWList();

    public SubDivision getSubDivisionBySubDivisionId(int subDivisionId);

    public SubDivisionVW getSubDivisionVWBySubDivisionId(int subDivisionId);

    public SubDivision getSubDivisionBySubDivisionCode(String subDivisionCode);

    public SubDivisionVW getSubDivisionVWBySubDivisionCode(String subDivisionCode);

    public SubDivision getSubDivisionByEnglishName(String englishName);

    public SubDivisionVW getSubDivisionVWByEnglishName(String englishName);

    public SubDivision getSubDivisionByNativeName(String nativeName);

    public SubDivisionVW getSubDivisionVWByNativeName(String nativeName);

    public boolean addSubDivision(SubDivision subDivision);

    public boolean updateSubDivision(SubDivision subDivision);

    public boolean deleteSubDivision(int subDivisionId);

    public List<SubDivision> getSubDivisionListByCountryId(int countryId);

    public boolean updateSubDivision(SubDivisionVW selectedSubDivisionVW);
}
