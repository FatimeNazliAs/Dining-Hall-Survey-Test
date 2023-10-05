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


import dininghall.service.news.NewsArticleCategoryService;
import dininghall.service.news.NewsArticleService;
import dininghall.service.news.NewsCategoryService;
import dininghall.service.news.NewsStateService;
import org.jsoup.Jsoup;
import dininghall.common.AppUtil;
import dininghall.common.MenuUtil;
import dininghall.domain.news.NewsArticle;
import dininghall.domain.news.NewsCategory;
import dininghall.domain.news.NewsState;
import dininghall.domain.user.LocalUserSession;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ManagedBean
@ViewScoped
public class NewsArticleInsertView implements Serializable {
    private MenuUtil menuUtil = new MenuUtil();

    private NewsArticle newNewsArticle;

    @ManagedProperty("#{newsArticleService}")
    private NewsArticleService newsArticleService;
    private String newsArticle;

    @ManagedProperty("#{newsArticleCategoryService}")
    private NewsArticleCategoryService newsArticleCategoryService;


    @ManagedProperty("#{newsStateService}")
    private NewsStateService newsStateService;
    private List<SelectItem> newsStateItemList;
    private List<NewsState> newsStateList;
    private int newsStateId;
    private String newsState;

    @ManagedProperty("#{newsCategoryService}")
    private NewsCategoryService newsCategoryService;
    private List<SelectItem> newsCategoryItemList;
    private List<NewsCategory> newsCategoryList;
    private List<Integer> selectedCategoryIds;


    private String headline;
    private String extract;
    private String encoding = "UTF-8";
    private String text;
    private Date publishDate;
    private String byAuthor;
    private String tweetText;
    private String source;
    private String clientQuote;
    private Date createdDate;
    private Date lastModifiedDate;
    private String htmlTitle;
    private String htmlMetaDescription;
    private String htmlMetaKeywords;
    private String htmlMetaLanguage;
    private String tags;
    private int priority = 20;
    private String format;
    private String photoHtmlAlt;
    private String photoOrientation;
    private int photoWidth;
    private int photoHeight;
    private String photoURL;
    private int wordCount = 0;
    private int viewCount = 0;


    @PostConstruct
    public void init() {
        newNewsArticle = new NewsArticle();

        newsStateItemList = new ArrayList<>();
        newsStateList = newsStateService.getNewsStateList(100);
        for (NewsState ns : newsStateList) {
            newsStateItemList.add(new SelectItem(ns.getNewsStateId(), ns.getNewsState()));
        }

        newsCategoryItemList = new ArrayList<>();
        newsCategoryList = newsCategoryService.getNewsCategoryList(100);
        for (NewsCategory nc : newsCategoryList) {
            newsCategoryItemList.add(new SelectItem(nc.getNewsCategoryId(), nc.getNewsCategory()));
        }
    }

    public void insertNewsArticle() {
        LocalUserSession localUserSession = menuUtil.getLocalUserSession();

        newNewsArticle.setLocalUserId(localUserSession.getUserId());

        newNewsArticle.setCreatedDate(new Date());
        newNewsArticle.setLastModifiedDate(new Date());

        try {
            countWords();
        } catch (Exception e) {
            e.printStackTrace();
        }

        newNewsArticle.setWordCount(wordCount);
        newNewsArticle.setViewCount(0);

        if (newsArticleService.insertNewsArticle(newNewsArticle)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Blog Yazısı başarıyla eklendi.", newNewsArticle.getHeadline());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Blog Yazısı eklenemedi", newNewsArticle.getHeadline());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void countWords() throws Exception {
        org.jsoup.nodes.Document dom = Jsoup.parse(newNewsArticle.getText());
        String t = dom.text();

        wordCount = t.split(" ").length;

        newNewsArticle.setWordCount(wordCount);
    }

    public void resetForm() {
        newNewsArticle = new NewsArticle();
        this.text = "";
    }

    public void generateSlug() {
        String slug = AppUtil.toSlug(newNewsArticle.getHeadline());

        newNewsArticle.setSlug(slug);
    }

    public NewsArticle getNewNewsArticle() {
        return newNewsArticle;
    }

    public void setNewNewsArticle(NewsArticle newNewsArticle) {
        this.newNewsArticle = newNewsArticle;
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


    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getByAuthor() {
        return byAuthor;
    }

    public void setByAuthor(String byAuthor) {
        this.byAuthor = byAuthor;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getNewsStateId() {
        return newsStateId;
    }

    public void setNewsStateId(int newsStateId) {
        this.newsStateId = newsStateId;
    }

    public String getClientQuote() {
        return clientQuote;
    }

    public void setClientQuote(String clientQuote) {
        this.clientQuote = clientQuote;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getHtmlTitle() {
        return htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle) {
        this.htmlTitle = htmlTitle;
    }

    public String getHtmlMetaDescription() {
        return htmlMetaDescription;
    }

    public void setHtmlMetaDescription(String htmlMetaDescription) {
        this.htmlMetaDescription = htmlMetaDescription;
    }

    public String getHtmlMetaKeywords() {
        return htmlMetaKeywords;
    }

    public void setHtmlMetaKeywords(String htmlMetaKeywords) {
        this.htmlMetaKeywords = htmlMetaKeywords;
    }

    public String getHtmlMetaLanguage() {
        return htmlMetaLanguage;
    }

    public void setHtmlMetaLanguage(String htmlMetaLanguage) {
        this.htmlMetaLanguage = htmlMetaLanguage;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPhotoHtmlAlt() {
        return photoHtmlAlt;
    }

    public void setPhotoHtmlAlt(String photoHtmlAlt) {
        this.photoHtmlAlt = photoHtmlAlt;
    }

    public String getPhotoOrientation() {
        return photoOrientation;
    }

    public void setPhotoOrientation(String photoOrientation) {
        this.photoOrientation = photoOrientation;
    }

    public int getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(int photoWidth) {
        this.photoWidth = photoWidth;
    }

    public int getPhotoHeight() {
        return photoHeight;
    }

    public void setPhotoHeight(int photoHeight) {
        this.photoHeight = photoHeight;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public NewsStateService getNewsStateService() {
        return newsStateService;
    }

    public void setNewsStateService(NewsStateService newsStateService) {
        this.newsStateService = newsStateService;
    }

    public List<SelectItem> getNewsStateItemList() {
        return newsStateItemList;
    }

    public void setNewsStateItemList(List<SelectItem> newsStateItemList) {
        this.newsStateItemList = newsStateItemList;
    }

    public List<NewsState> getNewsStateList() {
        return newsStateList;
    }

    public void setNewsStateList(List<NewsState> newsStateList) {
        this.newsStateList = newsStateList;
    }

    public String getNewsState() {
        return newsState;
    }

    public void setNewsState(String newsState) {
        this.newsState = newsState;
    }

    public List<Integer> getSelectedCategoryIds() {
        return selectedCategoryIds;
    }

    public void setSelectedCategoryIds(List<Integer> selectedCategoryIds) {
        this.selectedCategoryIds = selectedCategoryIds;
    }

    public NewsCategoryService getNewsCategoryService() {
        return newsCategoryService;
    }

    public void setNewsCategoryService(NewsCategoryService newsCategoryService) {
        this.newsCategoryService = newsCategoryService;
    }

    public List<SelectItem> getNewsCategoryItemList() {
        return newsCategoryItemList;
    }

    public void setNewsCategoryItemList(List<SelectItem> newsCategoryItemList) {
        this.newsCategoryItemList = newsCategoryItemList;
    }

    public List<NewsCategory> getNewsCategoryList() {
        return newsCategoryList;
    }

    public void setNewsCategoryList(List<NewsCategory> newsCategoryList) {
        this.newsCategoryList = newsCategoryList;
    }


    public NewsArticleCategoryService getNewsArticleCategoryService() {
        return newsArticleCategoryService;
    }

    public void setNewsArticleCategoryService(NewsArticleCategoryService newsArticleCategoryService) {
        this.newsArticleCategoryService = newsArticleCategoryService;
    }
}


