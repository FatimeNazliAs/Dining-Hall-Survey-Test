/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.domain.helpdesk.HDeskReportedVia;

import java.util.List;

/**
 * @author Tolga
 */
public interface HDeskReportedViaDAO {
    public List<HDeskReportedVia> getHDeskReportedViaList();

    public HDeskReportedVia getHDeskReportedViaByHDeskReportedViaId(int reportedViaId);

    public HDeskReportedVia getHDeskReportedViaByName(String name);

    public boolean addHDeskReportedVia(HDeskReportedVia hDeskReportedVia);

    public void updateHDeskReportedVia(HDeskReportedVia hDeskReportedVia);

    public boolean deleteHDeskReportedVia(int reportedViaId);

}
