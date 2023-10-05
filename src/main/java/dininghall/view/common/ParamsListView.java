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
package dininghall.view.common;


import dininghall.domain.common.ParamsVW;
import dininghall.lazydomain.common.LazyParamsDataModel;
import dininghall.service.common.ParamsService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import dininghall.domain.common.Params;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean(name = "paramsListView")
@ViewScoped
public class ParamsListView implements Serializable {
    private LazyDataModel<ParamsVW> lazyParamsDataModel;

    private ParamsVW selectedParamsVW;

    @ManagedProperty("#{paramsService}")
    private ParamsService paramsService;

    private int pageSize = 15;


    @PostConstruct
    public void init() {
        lazyParamsDataModel = new LazyParamsDataModel(paramsService.getParamsVWList(0, pageSize, null, null));
    }


    public void onRowEdit(RowEditEvent event) {
        ParamsVW paramsVW = (ParamsVW) event.getObject();
        FacesMessage msg;


        if (paramsService.updateParams(paramsVW)) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Parametre başarıyla güncellendi", paramsVW.getParamName());

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parametre düzenlenemedi.", paramsVW.getParamName());
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        ParamsVW paramsVW = (ParamsVW) event.getObject();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Düzenleme iptal edildi.", paramsVW.getParamName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteParams(int paramsId) {
        FacesContext context = FacesContext.getCurrentInstance();

        Params params = paramsService.getParamsByParamsId(paramsId);
        if (params == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Düzenlenecek Parametre bulunamadı!"));
            return;
        }

        if (paramsService.deleteParams(params)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Parametre başarıyla silindi."));

            lazyParamsDataModel = new LazyParamsDataModel(paramsService.getParamsVWList(0, pageSize, null, null));

            selectedParamsVW = new ParamsVW();

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Sistem Hatası, Parametre silinemedi."));
        }

    }


    public LazyDataModel<ParamsVW> getLazyParamsDataModel() {
        return lazyParamsDataModel;
    }

    public void setLazyParamsDataModel(LazyDataModel<ParamsVW> lazyParamsDataModel) {
        this.lazyParamsDataModel = lazyParamsDataModel;
    }

    public ParamsVW getSelectedParamsVW() {
        return selectedParamsVW;
    }

    public void setSelectedParamsVW(ParamsVW selectedParamsVW) {
        this.selectedParamsVW = selectedParamsVW;
    }

    public ParamsService getParamsService() {
        return paramsService;
    }

    public void setParamsService(ParamsService paramsService) {
        this.paramsService = paramsService;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
