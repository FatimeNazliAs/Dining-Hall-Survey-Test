/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.domain.helpdesk.HDeskPriority;

import java.util.List;

/**
 * @author Tolga
 */
public interface HDeskPriorityDAO {
    public List<HDeskPriority> getHDeskPriorityList();

    public HDeskPriority getHDeskPriorityByHDeskPriorityId(int priorityId);

    public HDeskPriority getHDeskPriorityByName(String name);

    public boolean addHDeskPriority(HDeskPriority hDeskPriority);

    public void updateHDeskPriority(HDeskPriority hDeskPriority);

    public boolean deleteHDeskPriority(int priorityId);

}
