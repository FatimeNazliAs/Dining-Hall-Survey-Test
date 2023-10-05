/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.info;

import dininghall.domain.info.Company;

import java.util.List;

/**
 * @author Tolga
 */
public interface CompanyDAO {
    public List<Company> getCompanyList();

    public Company getCompanyByCompanyId(long companyId);

    public Company getCompanyByName(String name);

    public boolean addCompany(Company company);

    public boolean updateCompany(Company company);

    public boolean deleteCompany(long priorityId);

}
