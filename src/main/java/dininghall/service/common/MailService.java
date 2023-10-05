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
package dininghall.service.common;


import dininghall.common.MailHelper;
import dininghall.domain.common.ContactMessage;
import dininghall.domain.news.NewsArticleVW;

import dininghall.domain.user.ForgetPasswordToken;
import dininghall.domain.user.LocalUser;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "mailService")
@ApplicationScoped
public class MailService {
    private final static MailHelper mailHelper;

    static {
        mailHelper = new MailHelper();
    }

    public boolean sendContactMail(List<String> recipientList, ContactMessage contactMessage) {
        return mailHelper.sendContactEmail(recipientList, contactMessage);
    }

    public boolean sendOrderEmail(List<String> recipientList, String body) {
        return mailHelper.sendOrderEmail(recipientList, body);
    }


    public boolean sendForgetPasswordMail(List<String> recipientList, ForgetPasswordToken forgetPasswordToken, LocalUser localUser) {
        return mailHelper.sendForgetPasswordEmail(recipientList, forgetPasswordToken, localUser);
    }

    public boolean sendRegisterEmail(List<String> recipientList, LocalUser localUser) {
        return mailHelper.sendRegisterEmail(recipientList, localUser);
    }

    public boolean sendNewsletterEmail(List<String> recipientList, NewsArticleVW selectedNewsArticleVW) {
        return mailHelper.sendNewsletterEmail(recipientList, selectedNewsArticleVW);
    }

}
