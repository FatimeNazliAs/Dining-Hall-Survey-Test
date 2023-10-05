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
import dininghall.dao.common.FaqDAO;
import dininghall.dao.common.FaqDAOImpl;
import dininghall.domain.common.Faq;
import dininghall.domain.common.FaqVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "faqService")
@ApplicationScoped
public class FaqService {
    private final static FaqDAO faqDAO;

    static {
        faqDAO = new FaqDAOImpl();
    }

    public List<Faq> getFaqList(int size) {
        List<Faq> list = faqDAO.getFaqList();

        return list;
    }


    public boolean updateFaq(Faq faq) {
        return faqDAO.updateFaq(faq);
    }

    public boolean updateFaq(FaqVW faqVW) {
        return faqDAO.updateFaq(faqVW);
    }

    public boolean deleteFaq(Faq faq) {
        return faqDAO.deleteFaq(faq.getFaqId());
    }

    public Faq getFaqByFaqId(int faqId) {
        return faqDAO.getFaqByFaqId(faqId);
    }

    public Faq getFaqByQuestion(String question) {
        return faqDAO.getFaqByQuestion(question);
    }

    public boolean insertFaq(Faq faq) {
        return faqDAO.addFaq(faq);
    }

    public int getFaqCount(Map<String, Object> filters) {
        return faqDAO.getFaqCount(filters);
    }

    public List<FaqVW> getFaqVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        return faqDAO.getFaqVWList(first, pageSize, sortFilters, filters);
    }

}
