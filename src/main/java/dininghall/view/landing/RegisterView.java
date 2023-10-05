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
package dininghall.view.landing;

import dininghall.service.common.MailService;
import dininghall.service.user.LocalUserService;
import dininghall.common.BTUtil;
import dininghall.domain.user.ForgetPasswordToken;
import dininghall.domain.user.LocalUser;
import dininghall.domain.user.LocalUserRole;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@ManagedBean(name = "registerView")
@ViewScoped
public class RegisterView implements Serializable {
    private LocalUser localUser;

    @ManagedProperty("#{localUserService}")
    private LocalUserService localUserService;

    @ManagedProperty("#{mailService}")
    private MailService mailService;

    private String verifyMessage;

    private String email;

    private String newPassword;

    private String newPasswordRepeat;

    private String token;


    @PostConstruct
    public void init() {
        setLocalUser(new LocalUser());
    }

    public void register() {
        if (localUserService.checkLocalUserEmail(localUser.getEmail())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Girdiğiniz e-posta adresi sistemimizde kayıtlıdır, lütfen farklı bir e-posta adresi ile yeniden deneyiniz.",
                    localUser.getEmail());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            return;
        }

        localUser.setRegisteredDate(new Date());

        // USER ROLE
        LocalUserRole localUserRole = new LocalUserRole();
        localUserRole.setLocalRoleId(3);
        localUser.setLocalUserRole(localUserRole);

        // create verification code
        localUser.setAccessToken(BTUtil.randomStringGenerator(50));

        if (localUserService.insertLocalUser(localUser)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kayıt işlemi başarıyla gerçekleştirildi, alışverişe başlamadan önce eposta adresinize " +
                    "gönderilen link üzerinden hesabınızı aktifleştirmeniz gerekmektedir.",
                    localUser.getFirstName() + localUser.getLastName());

            FacesContext.getCurrentInstance().addMessage(null, msg);

            List<String> recipientList = new ArrayList<>();
            recipientList.add(localUser.getEmail());

            mailService.sendRegisterEmail(recipientList, localUser);

            localUser = new LocalUser();

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kayıt işleminiz başarısız, sorun devam ediyorsa iletişim bölümünden bize ulaşabilirsiniz.",
                    localUser.getEmail());

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void verifyEmail() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String accessToken = params.get("accessToken");

        if (accessToken == null || accessToken.isEmpty()) {
            verifyMessage = "* Girdiğiniz doğrulama kodu geçerli değildir, üzgünüz :(";

            return;
        }

        LocalUser localUser = localUserService.getLocalUserByAccessToken(accessToken);

        if (localUser != null) {
            localUser.setAccessToken(null);
            localUser.setEnabled(null);

            verifyMessage = "Tebrikler, Eposta doğrulamanız başarıyla gerçekleştirildi, keyifli alışverişler dileriz :)";

            localUserService.updateLocalUser(localUser);
        } else {
            verifyMessage = "Doğrulama işlemi daha önce gerçekleştirilmiştir, teşekkür ederiz :)";
        }
    }

    public void forgetPassword() {
        LocalUser localUser = localUserService.getLocalUserByEmail(email);

        if (localUser != null) {
            List<String> recipientList = new ArrayList<>();
            recipientList.add(email);

            ForgetPasswordToken forgetPasswordToken = new ForgetPasswordToken();
            forgetPasswordToken.setToken(BTUtil.randomStringGenerator(50));
            forgetPasswordToken.setLocalUserId(localUser.getLocalUserId());
            forgetPasswordToken.setEnabled(true);
            forgetPasswordToken.setExpired(false);
            forgetPasswordToken.setExpirationDate(new Date());

            if (localUserService.insertForgetPasswordToken(forgetPasswordToken)) {
                if (mailService.sendForgetPasswordMail(recipientList, forgetPasswordToken, localUser)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Şifre yenilemek için posta kutunuza gönderilen maili kontrol edebilirsiniz.",
                            localUser.getFirstName() + " " + localUser.getLastName());

                    FacesContext.getCurrentInstance().addMessage(null, msg);

                    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

                    String path = context.getRequestContextPath() + "/renew-password-request/baglanti-bilgisi-iletildi";

                    try {
                        context.redirect(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;


                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maalesef yeni şifre belirleme isteği sırasında hata meydana geldi.",
                            localUser.getFirstName() + " " + localUser.getLastName());

                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Maalesef şu an için işleminizi gerçekleştiremiyoruz, " +
                        "sorun devam ediyorsa iletişim sayfamızdan bize bilgi verebilirsiniz.",
                        localUser.getFirstName() + " " + localUser.getLastName());

                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kullanıcı bilgilerinizi lütfen kontrol ediniz.",
                    email);

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public LocalUser getLocalUser() {
        return localUser;
    }

    public void setLocalUser(LocalUser localUser) {
        this.localUser = localUser;
    }

    public LocalUserService getLocalUserService() {
        return localUserService;
    }

    public void setLocalUserService(LocalUserService localUserService) {
        this.localUserService = localUserService;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public String getVerifyMessage() {
        return verifyMessage;
    }

    public void setVerifyMessage(String verifyMessage) {
        this.verifyMessage = verifyMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
