/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * @author tyeltekin
 */
@ManagedBean(name = "navigationController", eager = true)
public class NavigationController {

    @ManagedProperty(value = "#{param.pageId}")
    private String pageId;

    public String showPage() {

        if (getPageId() == null) {
            return "home";
        }

        if (getPageId().equals("1")) {
            return "page1";
        } else if (getPageId().equals("2")) {
            return "page2";
        } else {
            return "/Views/Home/index";
        }

    }

    /**
     * @return the pageId
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * @param pageId the pageId to set
     */
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

}
