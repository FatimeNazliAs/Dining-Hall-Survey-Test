/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dininghall.domain.user;

import java.io.Serializable;

/**
 * @author Tolga
 */
public class LocalUserRole implements Serializable {

    private long localUserId;
    private long localRoleId;


    public long getLocalUserId() {
        return localUserId;
    }

    public void setLocalUserId(long localUserId) {
        this.localUserId = localUserId;
    }

    public long getLocalRoleId() {
        return localRoleId;
    }

    public void setLocalRoleId(long localRoleId) {
        this.localRoleId = localRoleId;
    }
}
