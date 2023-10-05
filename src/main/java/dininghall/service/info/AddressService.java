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
package dininghall.service.info;

import dininghall.dao.info.AddressDAO;
import dininghall.dao.info.AddressDAOImpl;
import dininghall.domain.info.Address;
import dininghall.domain.user.LocalUserAddressVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "addressService")
@ApplicationScoped
public class AddressService {

    private final static AddressDAO addressDAO;

    static {
        addressDAO = new AddressDAOImpl();
    }

    public List<Address> getAddressList(int size) {
        return addressDAO.getAddressList();
    }


    public boolean updateCompany(Address address) {
        return addressDAO.updateAddress(address);
    }

    public boolean deleteAddress(Address address) {
        return addressDAO.deleteAddress(address.getAddressId());
    }

    public Address getAddressByAddressId(long addressId) {
        return addressDAO.getAddressByAddressId(addressId);
    }


    public List<LocalUserAddressVW> getLocalUserAddressVWListByLocalUserId(long userId) {
        return addressDAO.getLocalUserAddressVWListByLocalUserId(userId);
    }

    public boolean insertAddress(Address newAddress) {
        return addressDAO.addAddress(newAddress);
    }

    public boolean updateAddress(Address selectedAddress) {
        return addressDAO.updateAddress(selectedAddress);
    }

    public LocalUserAddressVW getLocalUserAddressVWByAddressId(int addressId) {
        return addressDAO.getLocalUserAddressVWByAddressId(addressId);
    }

    public boolean updateAddress(LocalUserAddressVW localUserAddressVW) {
        return addressDAO.updateAddress(localUserAddressVW);
    }

    public boolean insertAddress(LocalUserAddressVW localUserAddressVW) {
        return addressDAO.addAddress(localUserAddressVW);
    }
}
