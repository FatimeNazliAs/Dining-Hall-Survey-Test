/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.info;

import java.io.Serializable;

/**
 * @author Tolga
 */
public class SubDivisionVW implements Serializable {
    private int subDivisionId;
    private String subDivisionCode;
    private String englishName;
    private String nativeName;
    private int countryId;
    private String country;
    private int continentId;
    private String continent;

    public int getSubDivisionId() {
        return subDivisionId;
    }

    public void setSubDivisionId(int subDivisionId) {
        this.subDivisionId = subDivisionId;
    }

    public String getSubDivisionCode() {
        return subDivisionCode;
    }

    public void setSubDivisionCode(String subDivisionCode) {
        this.subDivisionCode = subDivisionCode;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(int continentId) {
        this.continentId = continentId;
    }


    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
