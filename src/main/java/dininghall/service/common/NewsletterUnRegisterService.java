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


import org.primefaces.model.SortOrder;
import dininghall.dao.common.NewsletterUnRegisterDAO;
import dininghall.dao.common.NewsletterUnRegisterDAOImpl;
import dininghall.domain.common.NewsletterUnRegister;
import dininghall.domain.common.NewsletterUnRegisterVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "newsletterUnRegisterService")
@ApplicationScoped
public class NewsletterUnRegisterService {
    private final static NewsletterUnRegisterDAO newsletterUnRegisterDAO;

    static {
        newsletterUnRegisterDAO = new NewsletterUnRegisterDAOImpl();
    }

    public List<NewsletterUnRegister> getNewsletterUnRegisterList(int size) {
        List<NewsletterUnRegister> list = newsletterUnRegisterDAO.getNewsletterUnRegisterList();

        return list;
    }

    public boolean deleteNewsletterUnRegister(NewsletterUnRegister newsletterUnRegister) {
        return newsletterUnRegisterDAO.deleteNewsletterUnRegister(newsletterUnRegister.getUnRegisterId());
    }

    public NewsletterUnRegisterVW getNewsletterByNewsletterId(int unRegisterId) {
        return newsletterUnRegisterDAO.getNewsletterUnRegisterVWUnRegisterId(unRegisterId);
    }

    public boolean insertNewsletterUnRegister(NewsletterUnRegister newsletterUnRegister) {
        return newsletterUnRegisterDAO.addNewsletterUnRegister(newsletterUnRegister);
    }

    public int getNewsletterUnRegisterCount(Map<String, Object> filters) {
        return newsletterUnRegisterDAO.getNewsletterUnRegisterCount(filters);
    }

    public List<NewsletterUnRegisterVW> getNewsletterUnRegisterVWList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return newsletterUnRegisterDAO.getNewsletterUnRegisterVWList(first, pageSize, sortField, sortOrder, filters);
    }


}
