/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.Street;
import dininghall.domain.info.StreetVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface StreetDAO {
    public List<Street> getStreetList();

    public List<StreetVW> getStreetVWList();

    public Street getStreetByStreetId(int streetId);

    public StreetVW getStreetVWByStreetId(int streetId);

    public List<Street> getStreetListByStateId(int stateId);

    public Street getStreetByStreetCode(String streetCode);

    public StreetVW getStreetVWByStreetCode(String streetCode);

    public Street getStreetByEnglishName(String englishName);

    public StreetVW getStreetVWByEnglishName(String englishName);

    public Street getStreetByNativeName(String nativeName);

    public StreetVW getStreetVWByNativeName(String nativeName);

    public boolean addStreet(Street street);

    public boolean updateStreet(Street street);

    public boolean deleteStreet(int streetId);

    public boolean updateStreet(StreetVW streetVW);


}
