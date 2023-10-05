package dininghall.lazydomain.news;

import dininghall.domain.news.NewsArticleVW;
import org.primefaces.model.SortOrder;

import java.util.Comparator;

public class LazyNewsArticleSorter implements Comparator<NewsArticleVW>
{

    private String sortField;

    private SortOrder sortOrder;

    public LazyNewsArticleSorter(String sortField, SortOrder sortOrder)
    {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(NewsArticleVW newsArticleVW1, NewsArticleVW newsArticleVW2)
    {
        try
        {
            Object value1 = NewsArticleVW.class.getField(this.sortField).get(newsArticleVW1);
            Object value2 = NewsArticleVW.class.getField(this.sortField).get(newsArticleVW2);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;

        } catch (Exception e)
        {
            throw new RuntimeException();
        }
    }
}