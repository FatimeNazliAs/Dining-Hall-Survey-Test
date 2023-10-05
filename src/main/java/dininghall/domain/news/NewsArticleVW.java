/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.news;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tolga
 */
public class NewsArticleVW implements Serializable
{

    private int newsArticleId;
    private String headline;
    private String extract;
    private String text;
    private Date publishDate;
    private String publishDateStr;
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
    private String newsState;
    private long localUserId;
    private String localUser;
    private List<Integer> categoryIds;
    private List<String> categoryNames;
    private String targetLink;
    private String firstImage;
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

    public void setNewsState(String newsState)
    {
        this.newsState = newsState;
    }

    public String getNewsState()
    {
        return newsState;
    }

    public long getLocalUserId()
    {
        return localUserId;
    }

    public void setLocalUserId(long localUserId)
    {
        this.localUserId = localUserId;
    }

    public String getLocalUser()
    {
        return localUser;
    }

    public void setLocalUser(String localUser)
    {
        this.localUser = localUser;
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

    public String getTargetLink()
    {
        targetLink = this.headline.toLowerCase()
                .replace(":", "")
                .replace(";", "")
                .replace(",", "")
                .replace("?", "")
                .replace("!", "")
                .replace("&", "")
                .replace("#", "")
                .replace("-", "")
                .replace("'", "")
                .replace("\\", "")
                .replace("/", "")
                .replace("*", "")
                .replace("_", "")
                .replace("%", "")
                .replace("+", "")
                .replace("{", "")
                .replace("}", "")
                .replace("(", "")
                .replace(")", "")
                .replace("=", "")
                .replace("\"", "")
                .replace("^", "")
                .replace("[", "")
                .replace("]", "")
                .replace("@", "")
                .replace("|", "")
                .replace(".", "")
                .replace("~", "")
                .replace("´", "")
                .replace("’", "")
                .replace(' ','-')
                + "-" + this.newsArticleId;

        return targetLink;
    }

    public void setTargetLink(String targetLink)
    {
        this.targetLink = targetLink;
    }

    public String getFirstImage()
    {
        try
        {
            Pattern pattern = Pattern.compile("(<img\\b|(?!^)\\G)[^>]*?\\b(src|width|height)=([\"']?)([^\"]*)\\3");
            Matcher matcher = pattern.matcher(text);
            while (matcher.find())
            {
                if (!matcher.group(1).isEmpty())
                { // We have a new IMG tag
                    //System.out.println("\n--- NEW MATCH ---");
                }

                //System.out.println(matcher.group(2) + ": " + matcher.group(4));

                String extensionPart = matcher.group(4).substring(matcher.group(4).indexOf('/') + 1, matcher.group(4).indexOf(';'));
                //System.out.println(extensionPart);
                String dataPart = matcher.group(4).substring(matcher.group(4).indexOf(',') + 1);
                //System.out.println(dataPart);

                firstImage = "data:image/" + extensionPart + ";base64," + dataPart;

                break;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return firstImage;
    }

    public void setFirstImage(String firstImage)
    {
        this.firstImage = firstImage;
    }

    public String getPublishDateStr()
    {
        return publishDateStr;
    }

    public void setPublishDateStr(String publishDateStr)
    {
        this.publishDateStr = publishDateStr;
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
