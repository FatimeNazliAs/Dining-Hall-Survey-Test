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

import dininghall.lazydomain.offer.LazyOfferDataModel;
import dininghall.service.offer.OfferService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.file.UploadedFile;
import dininghall.common.FileHelper;
import dininghall.domain.offer.Offer;
import dininghall.domain.offer.OfferVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean(name = "offerListView")
@ViewScoped
public class OfferListView implements Serializable {
    private OfferVW selectedOfferVW;

    @ManagedProperty("#{offerService}")
    private OfferService offerService;

    private LazyDataModel<OfferVW> lazyOfferDataModel;

    private int pageSize = 20;

    private UploadedFile file;
    private FileHelper fileHelper;


    @PostConstruct
    public void init() {
        fileHelper = new FileHelper();

        lazyOfferDataModel = new LazyOfferDataModel(offerService.getOfferVWList(0, pageSize, null, null), offerService);

    }

    public void updateInfo(int offerId) {
        selectedOfferVW = offerService.getOfferVWByOfferId(offerId);
    }

    public void onRowToggle(ToggleEvent event) {

    }

    public void onRowEdit(RowEditEvent event) {
        OfferVW offerVW = (OfferVW) event.getObject();

        FacesMessage msg;

        if (offerService.updateOffer(offerVW)) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kampanya başarıyla güncellendi.", offerVW.getCode() + "/" + offerVW.getCodeDesc());

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Kampanya güncellenemedi.", offerVW.getCode() + "/" + offerVW.getCodeDesc());
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        OfferVW offerVW = (OfferVW) event.getObject();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Düzenleme iptal edildi.", offerVW.getCode() + "/" + offerVW.getCodeDesc());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteOffer(int offerId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Offer offer = offerService.getOfferByOfferId(offerId);
        if (offer == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Hata", "Kampanya bulunamadı."));
            return;
        }

        if (offerService.deleteOffer(offer)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Kampanya başarıyla silindi."));

            lazyOfferDataModel = new LazyOfferDataModel(offerService.getOfferVWList(0, pageSize, null, null), offerService);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Kampanya silinemedi."));
        }

    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kampanya seçildi.",
                ((Offer) event.getObject()).getCode() + " " + ((Offer) event.getObject()).getCodeDesc());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kampanya seçimi iptal edildi.",
                ((Offer) event.getObject()).getCode() + " " + ((Offer) event.getObject()).getCodeDesc());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public OfferVW getSelectedOfferVW() {
        return selectedOfferVW;
    }

    public void setSelectedOfferVW(OfferVW selectedOfferVW) {
        this.selectedOfferVW = selectedOfferVW;
    }

    public OfferService getOfferService() {
        return offerService;
    }

    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    public LazyDataModel<OfferVW> getLazyOfferDataModel() {
        return lazyOfferDataModel;
    }

    public void setLazyOfferDataModel(LazyDataModel<OfferVW> lazyOfferDataModel) {
        this.lazyOfferDataModel = lazyOfferDataModel;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
