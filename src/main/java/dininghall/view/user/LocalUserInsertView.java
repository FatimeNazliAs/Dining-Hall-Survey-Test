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
package dininghall.view.user;

import dininghall.service.user.LocalUserService;
import dininghall.domain.user.LocalUser;
import dininghall.domain.user.LocalUserRole;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;


@ManagedBean
@ViewScoped
public class LocalUserInsertView implements Serializable {

    private LocalUser newLocalUser;

    @ManagedProperty("#{localUserService}")
    private LocalUserService localUserService;

    private String role;


    @PostConstruct
    public void init() {
        newLocalUser = new LocalUser();
    }

    public void insertLocalUser() {

        if (localUserService.checkLocalUserEmail(newLocalUser.getEmail())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Girdiğiniz e-posta adresi sistemimizde kayıtlıdır, lütfen farklı bir e-posta adresi ile yeniden deneyiniz.",
                    newLocalUser.getEmail());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            return;
        }

        newLocalUser.setRegisteredDate(new Date());

        LocalUserRole localUserRole = new LocalUserRole();
        if (role.equalsIgnoreCase("ROLE_SUPER")) {
            localUserRole.setLocalRoleId(1);

        } else if (role.equalsIgnoreCase("ROLE_ADMIN")) {
            localUserRole.setLocalRoleId(2);

        } else if (role.equalsIgnoreCase("ROLE_USER")) {
            localUserRole.setLocalRoleId(3);
        } else {
            localUserRole.setLocalRoleId(3);
        }
        newLocalUser.setLocalUserRole(localUserRole);

        if (localUserService.insertLocalUser(newLocalUser)) {
            FacesMessage msg = new FacesMessage("Kullanıcı başarıyla eklendi.", newLocalUser.getFirstName());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Sistem Hatası, Kullanıcı eklenemedi.", newLocalUser.getFirstName());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public LocalUser getNewLocalUser() {
        return newLocalUser;
    }

    public void setNewLocalUser(LocalUser newLocalUser) {
        this.newLocalUser = newLocalUser;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalUserService getLocalUserService() {
        return localUserService;
    }

    public void setLocalUserService(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }
}
