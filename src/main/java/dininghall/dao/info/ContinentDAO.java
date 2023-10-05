/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.Continent;
import dininghall.domain.info.ContinentVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface ContinentDAO {
    public List<Continent> getContinentList();

    public List<ContinentVW> getContinentVWList();

    public Continent getContinentByContinentId(int continentId);

    public ContinentVW getContinentVWByContinentId(int continentId);

    public Continent getContinentByContinentCode(String continentCode);

    public ContinentVW getContinentVWByContinentCode(String continentCode);

    public Continent getContinentByEnglishName(String englishName);

    public ContinentVW getContinentVWByEnglishName(String englishName);

    public Continent getContinentByNativeName(String nativeName);

    public ContinentVW getContinentVWByNativeName(String nativeName);

    public boolean addContinent(Continent continent);

    public boolean updateContinent(Continent continent);

    public boolean deleteContinent(int continentId);

    public boolean updateContinent(ContinentVW selectedContinentVW);
}
