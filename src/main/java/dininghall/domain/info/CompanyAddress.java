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
public class CompanyAddress implements Serializable {
    private long companyAddressId;
    private long companyId;
    private long addressId;
    private Address address;


    public long getCompanyAddressId() {
        return companyAddressId;
    }

    public void setCompanyAddressId(long companyAddressId) {
        this.companyAddressId = companyAddressId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
