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
package dininghall.service.helpdesk;

import dininghall.dao.helpdesk.HDeskScoreDAO;
import dininghall.dao.helpdesk.HDeskScoreDAOImpl;
import dininghall.domain.helpdesk.HDeskScore;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "hDeskScoreService")
@ApplicationScoped
public class HDeskScoreService {

    private final static HDeskScoreDAO hDeskScoreDAO;

    static {

        hDeskScoreDAO = new HDeskScoreDAOImpl();

    }

    public List<HDeskScore> getHDeskScoreList(int size) {
        List<HDeskScore> list = hDeskScoreDAO.getHDeskScoreList();

        return list;
    }


    public void updateHDeskScore(HDeskScore hDeskScore) {
        hDeskScoreDAO.updateHDeskScore(hDeskScore);
    }

    public boolean deleteHDeskScore(HDeskScore hDeskScore) {
        return hDeskScoreDAO.deleteHDeskScore(hDeskScore.getScoreId());
    }

    public HDeskScore getHDeskScoreByHDeskScoreId(int scoreId) {
        return hDeskScoreDAO.getHDeskScoreByHDeskScoreId(scoreId);
    }

    public boolean insertHDeskScore(HDeskScore hDeskScore) {
        return hDeskScoreDAO.addHDeskScore(hDeskScore);
    }
}
