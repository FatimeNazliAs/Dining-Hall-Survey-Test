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
package dininghall.service.news;


import dininghall.dao.news.NewsStateDAO;
import dininghall.dao.news.NewsStateDAOImpl;
import dininghall.domain.news.NewsState;
import dininghall.domain.news.NewsStateVW;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "newsStateService")
@ApplicationScoped
public class NewsStateService
{

    private final static NewsStateDAO newsStateDAO;

    static
    {
        newsStateDAO = new NewsStateDAOImpl();
    }

    public List<NewsState> getNewsStateList(int size)
    {
        return newsStateDAO.getNewsStateList();
    }

    public boolean updateNewsState(NewsState newsState)
    {
        return newsStateDAO.updateNewsState(newsState);
    }

    public boolean updateNewsState(NewsStateVW newsStateVW)
    {
        return newsStateDAO.updateNewsState(newsStateVW);
    }

    public boolean deleteNewsState(NewsState newsState)
    {
        return newsStateDAO.deleteNewsState(newsState.getNewsStateId());
    }

    public NewsState getNewsStateByNewsStateId(int newsStateId)
    {
        return newsStateDAO.getNewsStateByNewsStateId(newsStateId);
    }

    public boolean insertNewsState(NewsState newsState)
    {
        return newsStateDAO.addNewsState(newsState);
    }

    public List<NewsStateVW> getNewsStateVWList(int i)
    {
        return newsStateDAO.getNewsStateVWList();
    }
}
