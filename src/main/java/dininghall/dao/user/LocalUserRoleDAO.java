/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.dao.user;

import dininghall.domain.user.LocalUserRole;
import dininghall.domain.user.RoleVW;

/**
 * @author Tolga
 */
public interface LocalUserRoleDAO {
    public RoleVW getRoleViewByUserId(long localUserId);

    public LocalUserRole getLocalUserRoleByUserId(long localUserId);

    public boolean addLocalUserRole(LocalUserRole localUserRole);

    public void updateLocalUserRole(LocalUserRole localUserRole);

    public boolean deleteLocalUserRole(long localUserId);

}
