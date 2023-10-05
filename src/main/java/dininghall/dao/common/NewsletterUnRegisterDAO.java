/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import org.primefaces.model.SortOrder;
import dininghall.domain.common.NewsletterUnRegister;
import dininghall.domain.common.NewsletterUnRegisterVW;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface NewsletterUnRegisterDAO {
    List<NewsletterUnRegister> getNewsletterUnRegisterList();

    NewsletterUnRegisterVW getNewsletterUnRegisterVWUnRegisterId(int unRegisterId);

    boolean addNewsletterUnRegister(NewsletterUnRegister newsletterUnRegister);

    boolean deleteNewsletterUnRegister(int unRegisterId);

    int getNewsletterUnRegisterCount(Map<String, Object> filters);

    List<NewsletterUnRegisterVW> getNewsletterUnRegisterVWList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);
}
