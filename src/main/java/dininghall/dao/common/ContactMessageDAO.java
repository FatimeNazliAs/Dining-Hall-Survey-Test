/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.common;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import dininghall.domain.common.ContactMessage;
import dininghall.domain.common.ContactMessageVW;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface ContactMessageDAO {
    public List<ContactMessage> getContactMessageList();

    public ContactMessage getContactMessageByContactMessageId(long contactMessageId);

    public ContactMessage getContactMessageByEmail(String email);

    public boolean addContactMessage(ContactMessage contactMessage);

    public boolean updateContactMessage(ContactMessage contactMessage);

    public boolean updateContactMessage(ContactMessageVW contactMessageVW);

    public boolean deleteContactMessage(long contactMessageId);

    public int getContactMessageCount(Map<String, Object> filters);

    public List<ContactMessageVW> getContactMessageVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);

}
