/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.helpdesk;

import dininghall.domain.helpdesk.HDeskNote;

import java.util.List;

/**
 * @author Tolga
 */
public interface HDeskNoteDAO {
    public List<HDeskNote> getHDeskNoteList();

    public HDeskNote getHDeskNoteByHDeskNoteId(int noteId);

    public HDeskNote getHDeskNoteByName(String name);

    public boolean addHDeskNote(HDeskNote hDeskNote);

    public void updateHDeskNote(HDeskNote hDeskNote);

    public boolean deleteHDeskNote(int noteId);

}
