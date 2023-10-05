/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.domain.helpdesk.HDeskStatus;

import java.util.List;

/**
 * @author Tolga
 */
public interface HDeskStatusDAO {
    public List<HDeskStatus> getHDeskStatusList();

    public HDeskStatus getHDeskStatusByHDeskStatusId(int statusId);

    public HDeskStatus getHDeskStatusByName(String name);

    public boolean addHDeskStatus(HDeskStatus hDeskStatus);

    public void updateHDeskStatus(HDeskStatus hDeskStatus);

    public boolean deleteHDeskStatus(int statusId);

}
