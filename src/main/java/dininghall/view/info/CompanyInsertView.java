/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dininghall.view.info;

import dininghall.service.info.CompanyService;
import dininghall.domain.info.Company;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean
@ViewScoped
public class CompanyInsertView implements Serializable {
    private Company newCompany;

    @ManagedProperty("#{companyService}")
    private CompanyService service;

    private String companyName;
    private String phoneNumber;
    private String website;
    private String email;
    private String faxNumber;


    @PostConstruct
    public void init() {
        newCompany = new Company();
    }

    public void insertCompany() {
        newCompany.setCompanyName(companyName);
        newCompany.setPhoneNumber(phoneNumber);
        newCompany.setEmail(email);
        newCompany.setWebsite(website);
        newCompany.setFaxNumber(faxNumber);

        if (service.insertCompany(newCompany)) {
            FacesMessage msg = new FacesMessage("Company added successfully", newCompany.getCompanyName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("System error, Company not added.", newCompany.getCompanyName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void setService(CompanyService service) {
        this.service = service;
    }

    public Company getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(Company newCompany) {
        this.newCompany = newCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }
}
