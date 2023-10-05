/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.domain.common.Faq;
import dininghall.domain.common.FaqVW;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface FaqDAO {
    List<Faq> getFaqList();

    Faq getFaqByFaqId(int faqId);

    Faq getFaqByQuestion(String question);

    boolean addFaq(Faq faq);

    boolean updateFaq(Faq faq);

    boolean updateFaq(FaqVW faqVW);

    boolean deleteFaq(int faqId);

    int getFaqCount(Map<String, Object> filters);

    List<FaqVW> getFaqVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);
}
