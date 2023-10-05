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
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import dininghall.domain.user.LocalUser;
import dininghall.domain.user.LocalUserSession;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean(name = "userView")
@Data
@SessionScoped
public class UserView implements Serializable {

    private LocalUser keasUser;

    @ManagedProperty("#{localUserService}")
    private LocalUserService localUserService;


    @PostConstruct
    public void init() {
        LocalUserSession localUserSession = (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        keasUser = localUserService.getLocalUserByLocalUserId(localUserSession.getUserId());

    }

    public void updateKeasUserInfo() {

        if (localUserService.updateKeasUser(keasUser)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullanıcı bilgileriniz başarıyla güncellendi.",
                    keasUser.getFirstName() + " " + keasUser.getLastName());
            FacesContext.getCurrentInstance().addMessage(null, msg);

            LocalUserSession localUserSession = (LocalUserSession) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            localUserSession.setFullname(keasUser.getFirstName() + " " + keasUser.getLastName());
            localUserSession.setEmail(keasUser.getEmail());

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Kullanıcı bilgileriniz güncellenemedi.",
                    keasUser.getFirstName() + keasUser.getLastName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void updateKeasUserPassword() {
        if (!localUserService.checkKeasUserPassword(keasUser)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Girdiğiniz eski şifreniz geçerli değildir, lütfen tekrar kontrol ediniz.",
                    keasUser.getFirstName() + keasUser.getLastName());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            return;
        }

        if (localUserService.updateKeasUserPassword(keasUser)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullanıcı şifreniz başarıyla güncellendi.",
                    keasUser.getFirstName() + " " + keasUser.getLastName());

            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistem Hatası, Kullanıcı bilgileriniz güncellenemedi.",
                    keasUser.getFirstName() + keasUser.getLastName());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public LocalUserService getLocalUserService() {
        return localUserService;
    }

    public void setLocalUserService(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }



}
