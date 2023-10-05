/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.Address;
import dininghall.domain.user.LocalUserAddressVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface AddressDAO {
    List<Address> getAddressList();

    Address getAddressByAddressId(long addressId);

    boolean addAddress(Address address);

    boolean addLocalUserAddress(long localUserId, long addressId);

    boolean updateAddress(Address address);

    boolean deleteAddress(long addressId);

    List<LocalUserAddressVW> getLocalUserAddressVWListByLocalUserId(long localUserId);

    LocalUserAddressVW getLocalUserAddressVWByAddressId(long addressId);

    boolean updateAddress(LocalUserAddressVW localUserAddressVW);

    boolean addAddress(LocalUserAddressVW localUserAddressVW);
}
