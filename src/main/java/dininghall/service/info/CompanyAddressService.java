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

import dininghall.dao.info.CompanyAddressDAO;
import dininghall.dao.info.CompanyAddressDAOImpl;
import dininghall.domain.info.CompanyAddress;
import dininghall.domain.info.CompanyAddressVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "companyAddressService")
@ApplicationScoped
public class CompanyAddressService {

    private final static CompanyAddressDAO companyAddressDAO;

    static {
        companyAddressDAO = new CompanyAddressDAOImpl();
    }

    public List<CompanyAddress> getCompanyAddressList(int size) {
        return companyAddressDAO.getCompanyAddressList();
    }


    public boolean updateCompany(CompanyAddress companyAddress) {
        return companyAddressDAO.updateCompanyAddress(companyAddress);
    }

    public boolean deleteCompanyAddress(CompanyAddress companyAddress) {
        return companyAddressDAO.deleteCompanyAddress(companyAddress.getCompanyAddressId());
    }

    public CompanyAddress getCompanyAddressByCompanyAddressId(long companyAddressId) {
        return companyAddressDAO.getCompanyAddressByCompanyAddressId(companyAddressId);
    }

    public boolean insertCompanyAddress(CompanyAddress companyAddress) {
        return companyAddressDAO.addCompanyAddress(companyAddress);
    }

    public List<CompanyAddressVW> getCompanyAddressListByCompanyId(long companyId) {
        return companyAddressDAO.getCompanyAddressListByCompanyId(companyId);
    }
}
