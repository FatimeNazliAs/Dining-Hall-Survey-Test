/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.CompanyAddress;
import dininghall.domain.info.CompanyAddressVW;

import java.util.List;

/**
 * @author Tolga
 */
public interface CompanyAddressDAO {
    List<CompanyAddress> getCompanyAddressList();

    List<CompanyAddressVW> getCompanyAddressListByCompanyId(long companyId);

    CompanyAddress getCompanyAddressByCompanyAddressId(long companyAddressId);

    CompanyAddress getCompanyAddressByCompanyId(long companyId);

    CompanyAddress getCompanyAddressByAddressId(long addressId);

    boolean addCompanyAddress(CompanyAddress companyAddress);

    boolean updateCompanyAddress(CompanyAddress companyAddress);

    boolean deleteCompanyAddress(long companyAddressId);


}
