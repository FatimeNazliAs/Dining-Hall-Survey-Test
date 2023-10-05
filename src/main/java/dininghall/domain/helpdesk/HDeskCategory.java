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
public class HDeskCategory implements Serializable {

    private int catId;
    private String name;
    private int parentCatId;
    private String description;


    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentCatId() {
        return parentCatId;
    }

    public void setParentCatId(int parentCatId) {
        this.parentCatId = parentCatId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
