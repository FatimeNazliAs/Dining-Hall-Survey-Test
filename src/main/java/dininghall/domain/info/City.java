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
public class City implements Serializable {
    private int cityId;
    private String cityCode;
    private String englishName;
    private String nativeName;
    private int subDivisionId;


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

    public int getSubDivisionId() {
        return subDivisionId;
    }

    public void setSubDivisionId(int subDivisionId) {
        this.subDivisionId = subDivisionId;
    }
}
