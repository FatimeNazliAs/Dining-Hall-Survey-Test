package dininghall.lazydomain.news;

import dininghall.service.news.NewsArticleService;
import dininghall.domain.news.NewsArticleVW;
import dininghall.view.common.NewsStateEnum;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

public class LazyNewsArticleDataModel extends LazyDataModel<NewsArticleVW>
{
    private static final long serialVersionUID = 1928300515443655570L;

    private NewsArticleService newsArticleService = new NewsArticleService();

    private List<NewsArticleVW> newsArticleVWList;

    private NewsStateEnum newsStateEnum;

    public LazyNewsArticleDataModel(NewsStateEnum newsStateEnum, List<NewsArticleVW> newsArticleVWList)
    {
        this.newsStateEnum = newsStateEnum;
        this.newsArticleVWList = newsArticleVWList;
    }

    @Override
    public String getRowKey(NewsArticleVW newsArticleVW)
    {
        return "" + newsArticleVW.getNewsArticleId();
    }

    @Override
    public NewsArticleVW getRowData(String rowKey)
    {
        try
        {
            for (NewsArticleVW obj : newsArticleVWList)
            {
                if (String.valueOf(obj.getNewsArticleId()).equals(rowKey))
                {
                    return obj;
                }
            }

        } catch (Exception e)
        {

            System.out.println("getRowData method error" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public int count(Map<String, FilterMeta> map)
    {
        return 0;
    }

    @Override
    public List<NewsArticleVW> load(int first, int pageSize, Map<String, SortMeta> sortFilters, Map<String, FilterMeta> filters)
    {
        List<NewsArticleVW> list = null;
        try
        {
            int rowCount = newsArticleService.getNewsArticleCount(newsStateEnum, null);

            this.setRowCount(rowCount);

            list = newsArticleService.getNewsArticleVWList(newsStateEnum, first, first + pageSize, sortFilters, filters);

        } catch (Exception e)
        {
            this.setRowCount(0);
            System.out.println("NewsArticleDataModel LOAD method error" + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

}