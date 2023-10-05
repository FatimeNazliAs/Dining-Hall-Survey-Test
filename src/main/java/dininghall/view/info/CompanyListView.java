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

import dininghall.service.info.AddressService;
import dininghall.service.info.CompanyAddressService;
import dininghall.service.info.CompanyService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import dininghall.domain.info.Address;
import dininghall.domain.info.Company;
import dininghall.domain.info.CompanyAddress;
import dininghall.domain.info.CompanyAddressVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


@ManagedBean
@ViewScoped
public class CompanyListView implements Serializable {
    private List<Company> companyList;

    private Company selectedCompany;

    private List<Company> filteredCompanyList;

    private List<CompanyAddressVW> selectedCompanyAddressVWList;


    @ManagedProperty("#{companyService}")
    private CompanyService companyService;

    @ManagedProperty("#{companyAddressService}")
    private CompanyAddressService companyAddressService;

    @ManagedProperty("#{addressService}")
    private AddressService addressService;


    @PostConstruct
    public void init() {
        companyList = companyService.getCompanyList(10);
    }

    public void onRowEdit(RowEditEvent event) {
        Company company = (Company) event.getObject();
        FacesMessage msg = new FacesMessage("Company Edited", company.getCompanyName());
        FacesContext.getCurrentInstance().addMessage(null, msg);

        companyService.updateCompany(company);
    }

    public void onRowCancel(RowEditEvent event) {
        Company company = (Company) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", company.getCompanyName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowToggle(ToggleEvent event) {
        this.selectedCompany = (Company) event.getData();
        this.selectedCompanyAddressVWList = companyAddressService.getCompanyAddressListByCompanyId(this.selectedCompany.getCompanyId());
    }

    public void deleteCompany(long companyId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Company company = companyService.getCompanyByCompanyId(companyId);
        if (company == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Company not found!"));
            return;
        }

        if (companyService.deleteCompany(company)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Company deleted successfully."));
            companyList = companyService.getCompanyList(10);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, company not deleted."));
        }

    }

    public void deleteCompanyAddress(long companyAddressId) {
        FacesContext context = FacesContext.getCurrentInstance();

        CompanyAddress companyAddress = companyAddressService.getCompanyAddressByCompanyAddressId(companyAddressId);
        if (companyAddress == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Company Address not found!"));
            return;
        }

        if (companyAddressService.deleteCompanyAddress(companyAddress)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Company Address deleted successfully."));
            this.selectedCompanyAddressVWList = companyAddressService.getCompanyAddressListByCompanyId(this.selectedCompany.getCompanyId());
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, Company Address not deleted."));
        }

    }

    public void deleteAddress(long addressId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Address address = addressService.getAddressByAddressId(addressId);
        if (address == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Address not found!"));
            return;
        }

        if (addressService.deleteAddress(address)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Address deleted successfully."));
            this.selectedCompanyAddressVWList = companyAddressService.getCompanyAddressListByCompanyId(this.selectedCompany.getCompanyId());
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "System Error, Address not deleted."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Company Selected", ((Company) event.getObject()).getCompanyName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Company Unselected", ((Company) event.getObject()).getCompanyName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public List<Company> getCompanyList() {
        return companyList;
    }

    public List<Company> getFilteredCompanyList() {
        return filteredCompanyList;
    }

    public void setFilteredCompanyList(List<Company> filteredCompanyList) {
        this.filteredCompanyList = filteredCompanyList;
    }


    public void setService(CompanyService service) {
        this.companyService = service;
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }


    public List<CompanyAddressVW> getSelectedCompanyAddressVWList() {
        return selectedCompanyAddressVWList;
    }

    public void setSelectedCompanyAddressVWList(List<CompanyAddressVW> selectedCompanyAddressVWList) {
        this.selectedCompanyAddressVWList = selectedCompanyAddressVWList;
    }


    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public CompanyAddressService getCompanyAddressService() {
        return companyAddressService;
    }

    public void setCompanyAddressService(CompanyAddressService companyAddressService) {
        this.companyAddressService = companyAddressService;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }
}
