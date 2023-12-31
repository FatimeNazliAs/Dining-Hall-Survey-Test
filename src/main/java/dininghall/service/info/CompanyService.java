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

import dininghall.dao.info.CompanyDAO;
import dininghall.dao.info.CompanyDAOImpl;
import dininghall.domain.info.Company;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "companyService")
@ApplicationScoped
public class CompanyService {

    private final static CompanyDAO companyDAO;

    static {
        companyDAO = new CompanyDAOImpl();
    }

    public List<Company> getCompanyList(int size) {
        List<Company> list = companyDAO.getCompanyList();

        return list;
    }


    public boolean updateCompany(Company company) {
        return companyDAO.updateCompany(company);
    }

    public boolean deleteCompany(Company company) {
        return companyDAO.deleteCompany(company.getCompanyId());
    }

    public Company getCompanyByCompanyId(long companyId) {
        return companyDAO.getCompanyByCompanyId(companyId);
    }

    public boolean insertCompany(Company company) {
        return companyDAO.addCompany(company);
    }
}
