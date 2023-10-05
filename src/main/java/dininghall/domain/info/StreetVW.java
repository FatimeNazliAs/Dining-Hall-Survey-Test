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
public class StreetVW implements Serializable {
    private int streetId;
    private String streetCode;
    private String englishName;
    private String nativeName;
    private int stateId;
    private String stateEnglishName;
    private int cityId;
    private String cityEnglishName;
    private int subDivisionId;
    private String subDivisionEnglishName;
    private int countryId;
    private String countryEnglishName;
    private int continentId;
    private String continentEnglishName;

    public int getStreetId() {
        return streetId;
    }

    public void setStreetId(int streetId) {
        this.streetId = streetId;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
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


    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateEnglishName() {
        return stateEnglishName;
    }

    public void setStateEnglishName(String stateEnglishName) {
        this.stateEnglishName = stateEnglishName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityEnglishName() {
        return cityEnglishName;
    }

    public void setCityEnglishName(String cityEnglishName) {
        this.cityEnglishName = cityEnglishName;
    }

    public int getSubDivisionId() {
        return subDivisionId;
    }

    public void setSubDivisionId(int subDivisionId) {
        this.subDivisionId = subDivisionId;
    }

    public String getSubDivisionEnglishName() {
        return subDivisionEnglishName;
    }

    public void setSubDivisionEnglishName(String subDivisionEnglishName) {
        this.subDivisionEnglishName = subDivisionEnglishName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryEnglishName() {
        return countryEnglishName;
    }

    public void setCountryEnglishName(String countryEnglishName) {
        this.countryEnglishName = countryEnglishName;
    }

    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(int continentId) {
        this.continentId = continentId;
    }

    public String getContinentEnglishName() {
        return continentEnglishName;
    }

    public void setContinentEnglishName(String continentEnglishName) {
        this.continentEnglishName = continentEnglishName;
    }
}
