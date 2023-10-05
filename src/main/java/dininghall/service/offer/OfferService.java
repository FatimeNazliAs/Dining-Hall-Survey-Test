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
package dininghall.service.offer;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import dininghall.dao.offer.OfferDAO;
import dininghall.dao.offer.OfferDAOImpl;
import dininghall.domain.offer.Offer;
import dininghall.domain.offer.OfferVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "offerService")
@ApplicationScoped
public class OfferService {

    private final static OfferDAO offerDAO;

    static {

        offerDAO = new OfferDAOImpl();

    }

    public List<Offer> getOfferList(int size) {
        List<Offer> list = offerDAO.getOfferList();

        return list;
    }


    public void updateOffer(Offer offer) {
        offerDAO.updateOffer(offer);
    }

    public boolean deleteOffer(Offer offer) {
        return offerDAO.deleteOffer(offer.getOfferId());
    }

    public Offer getOfferByOfferId(int offerId) {
        return offerDAO.getOfferByOfferId(offerId);
    }

    public boolean insertOffer(Offer offer) {
        return offerDAO.addOffer(offer);
    }


    public boolean updateOffer(OfferVW offerVW) {
        return offerDAO.updateOffer(offerVW);
    }

    public OfferVW getOfferVWByCode(String code) {
        return offerDAO.getOfferVWByCode(code);
    }

    public int getOfferCount(Map<String, Object> filters) {
        return offerDAO.getOfferCount(filters);
    }

    public List<OfferVW> getOfferVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters) {
        return offerDAO.getOfferVWList(first, pageSize, sortFilters, filters);
    }

    public OfferVW getOfferVWByOfferId(int offerId) {
        return offerDAO.getOfferVWByOfferId(offerId);
    }


    public List<OfferVW> getTopOfferVWList(int size) {
        return offerDAO.getTopOfferVWList(size);
    }

    public List<OfferVW> getTopPriceOfferVWList(int size) {
        return offerDAO.getTopPriceOfferVWList(size);
    }

    public List<OfferVW> getActiveOfferVWList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return offerDAO.getActiveOfferVWList(first, pageSize, sortField, sortOrder, filters);
    }

    public int getActiveOfferCount(Map<String, Object> filters) {
        return offerDAO.getActiveOfferCount(filters);
    }

}
