/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.news;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Tolga
 */
public class NewsArticle implements Serializable
{

    private int newsArticleId;
    private String headline;
    private String extract;
    private String text;
    private Date publishDate;
    private String byAuthor;
    private String tweetText;
    private String source;
    private int newsStateId;
    private String clientQuote;
    private Date createdDate;
    private Date lastModifiedDate;
    private String htmlTitle;
    private String htmlMetaDescription;
    private String htmlMetaKeywords;
    private String htmlMetaLanguage;
    private String tags;
    private int priority;
    private int wordCount;
    private int viewCount;
    private long localUserId;
    private List<Integer> categoryIds;
    private List<String> categoryNames;
    private int mainFileId;
    private String filePath;
    private String slug;


    public int getNewsArticleId()
    {
        return newsArticleId;
    }

    public void setNewsArticleId(int newsArticleId)
    {
        this.newsArticleId = newsArticleId;
    }

    public String getHeadline()
    {
        return headline;
    }

    public void setHeadline(String headline)
    {
        this.headline = headline;
    }

    public String getExtract()
    {
        return extract;
    }

    public void setExtract(String extract)
    {
        this.extract = extract;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Date getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(Date publishDate)
    {
        this.publishDate = publishDate;
    }

    public String getByAuthor()
    {
        return byAuthor;
    }

    public void setByAuthor(String byAuthor)
    {
        this.byAuthor = byAuthor;
    }

    public String getTweetText()
    {
        return tweetText;
    }

    public void setTweetText(String tweetText)
    {
        this.tweetText = tweetText;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public int getNewsStateId()
    {
        return newsStateId;
    }

    public void setNewsStateId(int newsStateId)
    {
        this.newsStateId = newsStateId;
    }

    public String getClientQuote()
    {
        return clientQuote;
    }

    public void setClientQuote(String clientQuote)
    {
        this.clientQuote = clientQuote;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getHtmlTitle()
    {
        return htmlTitle;
    }

    public void setHtmlTitle(String htmlTitle)
    {
        this.htmlTitle = htmlTitle;
    }

    public String getHtmlMetaDescription()
    {
        return htmlMetaDescription;
    }

    public void setHtmlMetaDescription(String htmlMetaDescription)
    {
        this.htmlMetaDescription = htmlMetaDescription;
    }

    public String getHtmlMetaKeywords()
    {
        return htmlMetaKeywords;
    }

    public void setHtmlMetaKeywords(String htmlMetaKeywords)
    {
        this.htmlMetaKeywords = htmlMetaKeywords;
    }



    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public int getWordCount()
    {
        return wordCount;
    }

    public void setWordCount(int wordCount)
    {
        this.wordCount = wordCount;
    }

    public int getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(int viewCount)
    {
        this.viewCount = viewCount;
    }

    public long getLocalUserId()
    {
        return localUserId;
    }

    public void setLocalUserId(long localUserId)
    {
        this.localUserId = localUserId;
    }

    public List<Integer> getCategoryIds()
    {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds)
    {
        this.categoryIds = categoryIds;
    }

    public List<String> getCategoryNames()
    {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames)
    {
        this.categoryNames = categoryNames;
    }

    public int getMainFileId()
    {
        return mainFileId;
    }

    public void setMainFileId(int mainFileId)
    {
        this.mainFileId = mainFileId;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getSlug()
    {
        return slug;
    }

    public void setSlug(String slug)
    {
        this.slug = slug;
    }

    public String getHtmlMetaLanguage()
    {
        return htmlMetaLanguage;
    }

    public void setHtmlMetaLanguage(String htmlMetaLanguage)
    {
        this.htmlMetaLanguage = htmlMetaLanguage;
    }
}
