/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.helpdesk;

import java.io.Serializable;

/**
 * @author Tolga
 */
public class HDeskCategoryVW implements Serializable {

    private int catId;
    private String catName;
    private String catDesc;
    private int parentCatId;
    private String parentCatName;
    private String parentCatDesc;


    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public int getParentCatId() {
        return parentCatId;
    }

    public void setParentCatId(int parentCatId) {
        this.parentCatId = parentCatId;
    }

    public String getParentCatName() {
        return parentCatName;
    }

    public void setParentCatName(String parentCatName) {
        this.parentCatName = parentCatName;
    }

    public String getParentCatDesc() {
        return parentCatDesc;
    }

    public void setParentCatDesc(String parentCatDesc) {
        this.parentCatDesc = parentCatDesc;
    }
}
