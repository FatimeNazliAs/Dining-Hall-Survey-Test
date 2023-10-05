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
package dininghall.view.offer;

import dininghall.service.offer.OfferService;
import dininghall.domain.offer.Offer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean(name = "offerView")
@ViewScoped
public class OfferView implements Serializable {
    private Offer offer;

    @ManagedProperty("#{offerService}")
    private OfferService offerService;


    @PostConstruct
    public void init() {
        offer = new Offer();
    }

    public void insertOffer() {
        if (offerService.insertOffer(offer)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "İndirim kodu başarıyla oluşturulmuştur!", offer.getCode());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            offer = new Offer();

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "İndirim kodu eklenemedi, lütfen tekrar deneyiniz!",
                    offer.getCode());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public OfferService getOfferService() {
        return offerService;
    }

}
