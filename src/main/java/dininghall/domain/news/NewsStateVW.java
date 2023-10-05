/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.news;

import java.io.Serializable;

/**
 *
 * @author Tolga
 */
public class NewsStateVW implements Serializable
{
    private int newsStateId;
    private String newsState;
    private int newNewsStateId;


    public int getNewsStateId()
    {
        return newsStateId;
    }

    public void setNewsStateId(int newsStateId)
    {
        this.newsStateId = newsStateId;
    }

    public String getNewsState()
    {
        return newsState;
    }

    public void setNewsState(String newsState)
    {
        this.newsState = newsState;
    }

    public int getNewNewsStateId()
    {
        return newNewsStateId;
    }

    public void setNewNewsStateId(int newNewsStateId)
    {
        this.newNewsStateId = newNewsStateId;
    }
}
