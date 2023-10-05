/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.news;

import dininghall.domain.news.NewsState;
import dininghall.domain.news.NewsStateVW;

import java.util.List;

/**
 *
 * @author Tolga
 */
public interface NewsStateDAO
{
    public List<NewsState> getNewsStateList();

    public NewsState getNewsStateByNewsStateId(int statusId);

    public NewsState getNewsStateByName(String name);

    public boolean addNewsState(NewsState newsState);

    public boolean updateNewsState(NewsState newsState);

    public boolean updateNewsState(NewsStateVW newsStateVW);

    public boolean deleteNewsState(int statusId);

    public List<NewsStateVW> getNewsStateVWList();

    public NewsStateVW getNewsStateVWByNewsStateId(int newsStateId);
}
