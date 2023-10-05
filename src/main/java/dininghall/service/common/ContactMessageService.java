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
import dininghall.dao.common.ContactMessageDAO;
import dininghall.dao.common.ContactMessageDAOImpl;
import dininghall.domain.common.ContactMessage;
import dininghall.domain.common.ContactMessageVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "contactMessageService")
@ApplicationScoped
public class ContactMessageService {
    private final static ContactMessageDAO contactMessageDAO;

    static {
        contactMessageDAO = new ContactMessageDAOImpl();
    }

    public List<ContactMessage> getContactMessageList(int size) {
        List<ContactMessage> list = contactMessageDAO.getContactMessageList();

        return list;
    }


    public boolean updateContactMessage(ContactMessage contactMessage) {
        return contactMessageDAO.updateContactMessage(contactMessage);
    }

    public boolean updateContactMessage(ContactMessageVW contactMessageVW) {
        return contactMessageDAO.updateContactMessage(contactMessageVW);
    }

    public boolean deleteContactMessage(ContactMessage contactMessage) {
        return contactMessageDAO.deleteContactMessage(contactMessage.getContactMessageId());
    }

    public ContactMessage getContactMessageByContactMessageId(int contactMessageId) {
        return contactMessageDAO.getContactMessageByContactMessageId(contactMessageId);
    }

    public ContactMessage getContactMessageByEmail(String email) {
        return contactMessageDAO.getContactMessageByEmail(email);
    }

    public boolean insertContactMessage(ContactMessage contactMessage) {
        return contactMessageDAO.addContactMessage(contactMessage);
    }

    public int getContactMessageCount(Map<String, Object> filters) {
        return contactMessageDAO.getContactMessageCount(filters);
    }

    public List<ContactMessageVW> getContactMessageVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        return contactMessageDAO.getContactMessageVWList(first, pageSize, sortFilters, filters);
    }

}
