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
package dininghall.view.news;


import dininghall.service.common.MailService;
import dininghall.service.common.NewsletterService;
import dininghall.service.news.NewsArticleService;
import dininghall.common.MenuUtil;
import dininghall.domain.common.Newsletter;
import dininghall.domain.news.NewsArticleVW;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@ManagedBean
@ViewScoped
public class NewsArticleMailView implements Serializable {
    private MenuUtil menuUtil = new MenuUtil();

    private int newsArticleId;

    private NewsArticleVW selectedNewsArticleVW;

    @ManagedProperty("#{mailService}")
    private MailService mailService;

    @ManagedProperty("#{newsletterService}")
    private NewsletterService newsletterService;

    @ManagedProperty("#{newsArticleService}")
    private NewsArticleService newsArticleService;
    private String newsArticle;


    private String mailContent;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        int newsArticleId = Integer.parseInt(params.get("newsArticleId"));

        selectedNewsArticleVW = newsArticleService.getNewsArticleVWByNewsArticleId(newsArticleId);
    }

    public void sendEmail() {
        List<String> recipientList = new ArrayList<>();

        List<Newsletter> newsletterList = newsletterService.getNewsletterList(10000);
        for (Newsletter n : newsletterList) {
            recipientList.add(n.getEmail());
        }

        if (mailService.sendNewsletterEmail(recipientList, selectedNewsArticleVW)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Duyurusu başarıyla gönderilmiştir.", selectedNewsArticleVW.getHeadline());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Blog Duyurusu gönderilemedi, lütfen tekrar deneyin", selectedNewsArticleVW.getHeadline());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }


    public NewsArticleVW getSelectedNewsArticleVW() {
        return selectedNewsArticleVW;
    }

    public void setSelectedNewsArticleVW(NewsArticleVW selectedNewsArticleVW) {
        this.selectedNewsArticleVW = selectedNewsArticleVW;
    }

    public String getNewsArticle() {
        return newsArticle;
    }

    public void setNewsArticle(String newsArticle) {
        this.newsArticle = newsArticle;
    }


    public NewsArticleService getNewsArticleService() {
        return newsArticleService;
    }

    public void setNewsArticleService(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public NewsletterService getNewsletterService() {
        return newsletterService;
    }

    public void setNewsletterService(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }
}


