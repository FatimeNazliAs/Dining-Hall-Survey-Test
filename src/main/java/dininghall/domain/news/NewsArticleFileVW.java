package dininghall.domain.news;

import java.io.File;

public class NewsArticleFileVW
{
    private File file;
    private int newsArticleFileId;
    private int newsArticleId;
    private int width;
    private int height;
    private String filePath;

    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }


    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getNewsArticleFileId()
    {
        return newsArticleFileId;
    }

    public void setNewsArticleFileId(int newsArticleFileId)
    {
        this.newsArticleFileId = newsArticleFileId;
    }

    public int getNewsArticleId()
    {
        return newsArticleId;
    }

    public void setNewsArticleId(int newsArticleId)
    {
        this.newsArticleId = newsArticleId;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
}
