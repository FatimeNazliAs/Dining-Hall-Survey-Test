/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.view.siteadmin;

import dininghall.service.common.CurrencyService;
import dininghall.service.news.NewsArticleService;
import dininghall.service.user.LocalUserService;
import dininghall.common.MenuUtil;
import dininghall.common.SessionCounterListener;
import dininghall.domain.common.Currency;
import dininghall.domain.news.NewsArticleVW;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * @author Tolga
 */

@ManagedBean(name = "dashboardView")
@ViewScoped
@Data
public class DashboardView implements Serializable
{
    private MenuUtil menuUtil;

    @ManagedProperty("#{localUserService}")
    private LocalUserService localUserService;
    private int totalUserCount;

    @ManagedProperty("#{productService}")
    // private ProductService productService;
    private int totalProductCount = 0;
    private int activeProductCount = 0;
    private int passiveProductCount = 0;

    @ManagedProperty("#{newsArticleService}")
    private NewsArticleService newsArticleService;
    private List<NewsArticleVW> newsArticleVWList;
    private List<NewsArticleVW> topNewsArticleVWList;

    @ManagedProperty("#{currencyService}")
    private CurrencyService currencyService;
    private List<Currency> currencyList;


    @PostConstruct
    public void init()
    {
        menuUtil = new MenuUtil();

        // Total Passive Product Count
        passiveProductCount = totalProductCount - activeProductCount;

        // Total User Count
        totalUserCount = localUserService.getLocalUserCount(null);

    }


}
