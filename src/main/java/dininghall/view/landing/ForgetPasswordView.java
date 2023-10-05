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


@ManagedBean(name = "forgetPasswordView")
@ViewScoped
public class ForgetPasswordView implements Serializable {
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
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        token = params.get("token");

        verifyForgetPasswordToken();
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

    public void verifyForgetPasswordToken() {

        if (token == null || token.isEmpty()) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpassword-durum/baglanti-gecerli-degil";

            try {
                context.redirect(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        ForgetPasswordToken forgetPasswordToken = localUserService.getForgetPasswordToken(token);

        if (forgetPasswordToken == null) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/bulunamadi";

            try {
                context.redirect(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        if (BTUtil.hoursDifference(forgetPasswordToken.getExpirationDate(), new Date()) > 24) {
            forgetPasswordToken.setExpired(true);
            localUserService.updateForgetPasswordToken(forgetPasswordToken);
        }


        if (forgetPasswordToken.isExpired()) {

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/suresi-dolmus";

            try {
                context.redirect(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        if (!forgetPasswordToken.isEnabled()) {

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/pasif";

            try {
                context.redirect(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        LocalUser localUser = localUserService.getLocalUserByLocalUserId(forgetPasswordToken.getLocalUserId());

        if (localUser == null) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/kullanici-bulunamadi";

            try {
                context.redirect(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;


        }

    }

    public String displayResultMessage(String message) {
        String resultMessage = "";

        switch (message) {
            case "basarili":
                resultMessage = "Tebrikler Kullanıcı şifreniz başarıyla değiştirildi, yeni şifreniz ile giriş yapabilirsiniz.";
                break;

            case "baglanti-gecerli-degil":
                resultMessage = "* Girdiğiniz doğrulama kodu geçerli değildir, üzgünüz :(";
                break;

            case "bulunamadi":
                resultMessage = "* Belirtilen bağlantı bilgisi bulunamamıştır.";
                break;

            case "suresi-dolmus":
                resultMessage = "* Belirtilen bağlantının süresi dolmuştur.";
                break;

            case "pasif":
                resultMessage = "* Belirtilen bağlantı aktif değildir.";
                break;

            case "kullanici-bulunamadi":
                resultMessage = "* Doğrulama yapmak istediğiniz kullanıcı bulunamamıştır.";
                break;

        }

        return resultMessage;
    }

    public void renewPassword() throws IOException {

        if (newPassword == null || newPassword.isEmpty() || newPasswordRepeat == null || newPasswordRepeat.isEmpty()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bilgi", "* Tüm alanları doldurmanız gerekmektedir.");

            FacesContext.getCurrentInstance().addMessage(null, msg);

            return;
        }

        if (newPassword.length() < 6 || newPasswordRepeat.length() < 6) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "* Şifreniz en az 6 karakter olmalıdır.", "* Şifreniz en az 6 karakter olmalıdır.");

            FacesContext.getCurrentInstance().addMessage(null, msg);

            return;
        }

        if (!newPassword.equals(newPasswordRepeat)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "* Girdiğiniz şifreler uyuşmuyor, lütfen kontrol ediniz.",
                    "* Girdiğiniz şifreler uyuşmuyor, lütfen kontrol ediniz.");

            FacesContext.getCurrentInstance().addMessage(null, msg);

            return;
        }

        if (token == null || token.isEmpty()) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/baglanti-gecerli-degil";

            context.redirect(path);

            return;
        }

        ForgetPasswordToken forgetPasswordToken = localUserService.getForgetPasswordToken(token);

        if (forgetPasswordToken == null) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/bulunamadi";

            context.redirect(path);

            return;
        }

        LocalUser localUser = localUserService.getLocalUserByLocalUserId(forgetPasswordToken.getLocalUserId());

        if (localUser == null) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/kullanici-bulunamadi";

            context.redirect(path);

            return;
        }

        if (BTUtil.hoursDifference(forgetPasswordToken.getExpirationDate(), new Date()) > 24) {
            forgetPasswordToken.setExpired(true);
            localUserService.updateForgetPasswordToken(forgetPasswordToken);
        }

        if (forgetPasswordToken.isExpired()) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/suresi-dolmus";

            context.redirect(path);

            return;
        }

        localUser.setPassword(newPassword);

        if (localUserService.updateKeasUserPassword(localUser)) {
            forgetPasswordToken.setEnabled(false);

            localUserService.updateForgetPasswordToken(forgetPasswordToken);

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/basarili";

            context.redirect(path);

        } else {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            String path = context.getRequestContextPath() + "/renewpasswordresult/sistem-hatasi";

            context.redirect(path);

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
