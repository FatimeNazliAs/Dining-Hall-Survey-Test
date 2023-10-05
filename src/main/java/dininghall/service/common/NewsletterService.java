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
package dininghall.service.common;


import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.dao.common.NewsletterDAO;
import dininghall.dao.common.NewsletterDAOImpl;
import dininghall.domain.common.Newsletter;
import dininghall.domain.common.NewsletterVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "newsletterService")
@ApplicationScoped
public class NewsletterService {
    private final static NewsletterDAO newsletterDAO;

    static {
        newsletterDAO = new NewsletterDAOImpl();
    }

    public List<Newsletter> getNewsletterList(int size) {
        List<Newsletter> list = newsletterDAO.getNewsletterList();

        return list;
    }


    public boolean updateNewsletter(Newsletter newsletter) {
        return newsletterDAO.updateNewsletter(newsletter);
    }

    public boolean updateNewsletter(NewsletterVW newsletterVW) {
        return newsletterDAO.updateNewsletter(newsletterVW);
    }

    public boolean deleteNewsletter(Newsletter newsletter) {
        return newsletterDAO.deleteNewsletter(newsletter.getNewsletterId());
    }

    public Newsletter getNewsletterByNewsletterId(int newsletterId) {
        return newsletterDAO.getNewsletterByNewsletterId(newsletterId);
    }

    public Newsletter getNewsletterByEmail(String email) {
        return newsletterDAO.getNewsletterByEmail(email);
    }

    public boolean insertNewsletter(Newsletter newsletter) {
        return newsletterDAO.addNewsletter(newsletter);
    }

    public int getNewsletterCount(Map<String, Object> filters) {
        return newsletterDAO.getNewsletterCount(filters);
    }

    public List<NewsletterVW> getNewsletterVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        return newsletterDAO.getNewsletterVWList(first, pageSize, sortFilters, filters);
    }

}
