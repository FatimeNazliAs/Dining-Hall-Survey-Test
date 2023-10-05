/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.domain.common.Newsletter;
import dininghall.domain.common.NewsletterVW;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface NewsletterDAO {
    List<Newsletter> getNewsletterList();

    Newsletter getNewsletterByNewsletterId(int newsletterId);

    Newsletter getNewsletterByEmail(String email);

    boolean addNewsletter(Newsletter newsletter);

    boolean updateNewsletter(Newsletter newsletter);

    boolean updateNewsletter(NewsletterVW newsletterVW);

    boolean deleteNewsletter(int newsletterId);

    int getNewsletterCount(Map<String, Object> filters);

    List<NewsletterVW> getNewsletterVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);
}
