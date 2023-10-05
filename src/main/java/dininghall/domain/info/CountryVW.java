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
public class CountryVW implements Serializable {
    private int countryId;
    private String countryAlpha2Code;
    private String countryAlpha3Code;
    private String countryNumericCode;
    private String englishName;
    private String nativeName;
    private int continentId;
    private String continent;


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryAlpha2Code() {
        return countryAlpha2Code;
    }

    public void setCountryAlpha2Code(String countryAlpha2Code) {
        this.countryAlpha2Code = countryAlpha2Code;
    }

    public String getCountryAlpha3Code() {
        return countryAlpha3Code;
    }

    public void setCountryAlpha3Code(String countryAlpha3Code) {
        this.countryAlpha3Code = countryAlpha3Code;
    }

    public String getCountryNumericCode() {
        return countryNumericCode;
    }

    public void setCountryNumericCode(String countryNumericCode) {
        this.countryNumericCode = countryNumericCode;
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
