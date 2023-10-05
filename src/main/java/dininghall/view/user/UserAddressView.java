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
package dininghall.view.user;

import dininghall.asstpackages.multilanguage.language.TestLanguageView;
import dininghall.service.info.AddressService;
import dininghall.service.info.CountryService;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import dininghall.common.AppUtil;
import dininghall.domain.info.Address;
import dininghall.domain.info.Country;
import dininghall.domain.user.LocalUserAddressVW;
import dininghall.domain.user.LocalUserSession;
import dininghall.domain.user.LocalUserVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean(name = "userAddressView")
@ViewScoped
@Data
public class UserAddressView implements Serializable {

    @ManagedProperty("#{addressService}")
    private AddressService addressService;

    private List<LocalUserAddressVW> localUserAddressVWList;
    private Address newAddress;
    private Address selectedAddress;
    private String city;

    private LocalUserAddressVW selectedLocalUserAddressVW;

    private List<LocalUserAddressVW> selectedLocalUserAddresses;


    @ManagedProperty("#{countryService}")
    private CountryService countryService;
    private List<SelectItem> countryItemList;
    private List<Country> countryList;


    @PostConstruct
    public void init() {
        newAddress = new Address();

        selectedAddress = new Address();

        LocalUserSession localUserSession = (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        setLocalUserAddressVWList(getAddressService().getLocalUserAddressVWListByLocalUserId(localUserSession.getUserId()));

        countryItemList = new ArrayList<>();
        countryList = countryService.getCountryList(9999999);
        for (Country country : countryList) {
            countryItemList.add(new SelectItem(country.getCountryId(), country.getEnglishName()));
        }

    }

    public void onRowEdit(RowEditEvent event) {
        LocalUserAddressVW localUserAddressVW = (LocalUserAddressVW) event.getObject();

        FacesContext context = FacesContext.getCurrentInstance();

        if (addressService.updateAddress(localUserAddressVW)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Adres başarıyla güncellendi."
                    + localUserAddressVW.getHeader()));

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Adres güncellenemedi."));
        }

    }

    public void onRowCancel(RowEditEvent event) {
        LocalUserVW localUserVW = (LocalUserVW) event.getObject();

        FacesMessage msg = new FacesMessage("Adres Düzenleme iptal edildi.", localUserVW.getUsername());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void openNew() {
        this.selectedLocalUserAddressVW = new LocalUserAddressVW();
        this.selectedLocalUserAddressVW.setAddressId(0);
    }

    public void deleteSelectedUserAddresses() {
        for (LocalUserAddressVW localUserAddressVW : selectedLocalUserAddresses) {
            Address address = new Address();
            address.setAddressId(localUserAddressVW.getAddressId());
            addressService.deleteAddress(address);

        }

        this.selectedLocalUserAddresses = null;

        this.selectedLocalUserAddressVW = null;

        LocalUserSession localUserSession = (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        setLocalUserAddressVWList(getAddressService().getLocalUserAddressVWListByLocalUserId(localUserSession.getUserId()));

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Adres başarıyla silindi."));

        PrimeFaces.current().ajax().update("addressForm:messages", "addressForm:userAddressList", "addressForm:delete-userAddresses-button");
    }

    public String getDeleteButtonMessage() {
        TestLanguageView d1 = new TestLanguageView();
        if (hasSelectedUserAddresses()) {
            int size = this.selectedLocalUserAddresses.size();
            return size > 1 ? size + " " + d1.getTextDb("user.addressesselected") : "1 " + d1.getTextDb("user.addressselected");
        }

        return d1.getTextDb("user.delete");
    }

    public boolean hasSelectedUserAddresses() {
        return this.selectedLocalUserAddresses != null && !this.selectedLocalUserAddresses.isEmpty();
    }


    /**
     * Insert new address to database
     */
    public void insertAddress() {
        LocalUserSession localUserSession = AppUtil.getLocalUserSession();

        if (localUserSession != null) {
            this.selectedLocalUserAddressVW.setLocalUserId(localUserSession.getUserId());

            if (this.selectedLocalUserAddressVW.getAddressId() == 0) {
                if (addressService.insertAddress(selectedLocalUserAddressVW)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Adres başarıyla eklendi.",
                            selectedLocalUserAddressVW.getHeader());
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    setLocalUserAddressVWList(getAddressService().getLocalUserAddressVWListByLocalUserId(localUserSession.getUserId()));

                    PrimeFaces.current().executeScript("PF('manageUserAddressDialog').hide()");

                    PrimeFaces.current().ajax().update("addressForm:messages", "addressForm:userAddressList");

                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Adres eklenemedi.",
                            selectedLocalUserAddressVW.getHeader());
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    PrimeFaces.current().executeScript("PF('manageUserAddressDialog').hide()");

                    PrimeFaces.current().ajax().update("addressForm:messages");
                }
            } else {

                if (addressService.updateAddress(selectedLocalUserAddressVW)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Adres başarıyla güncellendi.",
                            selectedLocalUserAddressVW.getHeader());
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    setLocalUserAddressVWList(getAddressService().getLocalUserAddressVWListByLocalUserId(localUserSession.getUserId()));

                    PrimeFaces.current().executeScript("PF('manageUserAddressDialog').hide()");

                    PrimeFaces.current().ajax().update("addressForm:messages", "addressForm:userAddressList");

                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Adres güncellenemedi.",
                            selectedLocalUserAddressVW.getHeader());
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    PrimeFaces.current().executeScript("PF('manageUserAddressDialog').hide()");

                    PrimeFaces.current().ajax().update("addressForm:messages");
                }
            }

        }


    }

    /**
     * Delete address from database
     */
    public void deleteAddress() {
        String addressId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        Address deleteAddress = addressService.getAddressByAddressId(Long.parseLong(addressId));

        LocalUserSession localUserSession = (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (addressId != null && deleteAddress != null) {
            FacesMessage msg;
            if (addressService.deleteAddress(deleteAddress)) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Adres başarıyla silinmiştir.", deleteAddress.getHeader());

                setLocalUserAddressVWList(getAddressService().getLocalUserAddressVWListByLocalUserId(localUserSession.getUserId()));

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem hatası, Adres silme işlemi başarısız.", deleteAddress.getHeader());
            }

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Görüntüleme Hatası", "Üzgünüz, Adres üzerinde işlem yapılamıyor."));
        }

    }


    public List<LocalUserAddressVW> getLocalUserAddressVWList() {
        return localUserAddressVWList;
    }

    public void setLocalUserAddressVWList(List<LocalUserAddressVW> localUserAddressVWList) {
        this.localUserAddressVWList = localUserAddressVWList;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public Address getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(Address newAddress) {
        this.newAddress = newAddress;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }
}
