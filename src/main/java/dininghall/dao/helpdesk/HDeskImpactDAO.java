/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.domain.helpdesk.HDeskImpact;

import java.util.List;

/**
 * @author Tolga
 */
public interface HDeskImpactDAO {
    public List<HDeskImpact> getHDeskImpactList();

    public HDeskImpact getHDeskImpactByHDeskImpactId(int impactId);

    public HDeskImpact getHDeskImpactByName(String name);

    public boolean addHDeskImpact(HDeskImpact hDeskImpact);

    public void updateHDeskImpact(HDeskImpact hDeskImpact);

    public boolean deleteHDeskImpact(int impactId);

}
