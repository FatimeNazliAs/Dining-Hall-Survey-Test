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
package dininghall.view.landing;

import dininghall.lazydomain.common.LazyFaqDataModel;
import dininghall.service.common.FaqService;
import org.primefaces.model.LazyDataModel;
import dininghall.domain.common.FaqVW;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;


@ManagedBean(name = "faqView")
@ViewScoped
public class FaqView implements Serializable {
    private LazyDataModel<FaqVW> lazyFaqDataModel;

    @ManagedProperty("#{faqService}")
    private FaqService faqService;

    private int pageSize = 1000;


    @PostConstruct
    public void init() {
        lazyFaqDataModel = new LazyFaqDataModel(faqService.getFaqVWList(0, pageSize, null, null));
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LazyDataModel<FaqVW> getLazyFaqDataModel() {
        return lazyFaqDataModel;
    }

    public void setLazyFaqDataModel(LazyDataModel<FaqVW> lazyFaqDataModel) {
        this.lazyFaqDataModel = lazyFaqDataModel;
    }

    public void setFaqService(FaqService faqService) {
        this.faqService = faqService;
    }

    public FaqService getFaqService() {
        return faqService;
    }
}
