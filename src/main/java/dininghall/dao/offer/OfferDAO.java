/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.offer;


import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import dininghall.domain.offer.Offer;
import dininghall.domain.offer.OfferVW;

import java.util.List;
import java.util.Map;

/**
 * @author Tolga
 */
public interface OfferDAO {
    List<Offer> getOfferList();

    List<OfferVW> getOfferVWList(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters);

    Offer getOfferByOfferId(int orderId);

    OfferVW getOfferVWByOfferId(int orderId);

    Offer getOfferByCode(String code);

    OfferVW getOfferVWByCode(String code);

    boolean addOffer(Offer order);

    void updateOffer(Offer order);

    boolean deleteOffer(int orderId);

    boolean updateOffer(OfferVW orderVW);

    int getOfferCount(Map<String, Object> filters);

    List<OfferVW> getOfferVWList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

    List<OfferVW> getTopOfferVWList(int size);

    List<OfferVW> getTopPriceOfferVWList(int size);

    List<OfferVW> getActiveOfferVWList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

    int getActiveOfferCount(Map<String, Object> filters);

}
