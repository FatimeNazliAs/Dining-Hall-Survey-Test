/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.domain.helpdesk.HDeskScore;

import java.util.List;

/**
 * @author Tolga
 */
public interface HDeskScoreDAO {
    public List<HDeskScore> getHDeskScoreList();

    public HDeskScore getHDeskScoreByHDeskScoreId(int scoreId);

    public HDeskScore getHDeskScoreByName(String name);

    public boolean addHDeskScore(HDeskScore hDeskScore);

    public void updateHDeskScore(HDeskScore hDeskScore);

    public boolean deleteHDeskScore(int scoreId);

}
