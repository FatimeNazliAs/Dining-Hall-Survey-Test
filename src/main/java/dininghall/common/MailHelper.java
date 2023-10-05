package dininghall.common;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import dininghall.view.payment.language.LanguageItemView;
import dininghall.domain.common.ContactMessage;
import dininghall.domain.news.NewsArticleVW;

import dininghall.domain.user.ForgetPasswordToken;
import dininghall.domain.user.LocalUser;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class MailHelper {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);

    // WINDOWS PATHS
   /* private static final String CREDENTIALS_FILE_PATH = "D:/DigiturkCoUk/gmail/digiturkcouk_credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "D:/DigiturkCoUk/gmail/tokens";
    private static final String CONTACT_EMAIL_FILE_PATH = "D:/DigiturkCoUk/gmail/template/contact.html";
    private static final String FORGET_PASSWORD_EMAIL_FILE_PATH = "D:/DigiturkCoUk/gmail/template/reset_password.html";
    private static final String REGISTER_EMAIL_FILE_PATH = "D:/DigiturkCoUk/gmail/template/welcome.html";
    private static final String NEWSLETTER_EMAIL_FILE_PATH = "D:/DigiturkCoUk/gmail/template/newsletter.html";
*/

    // LINUX PATHS
    private static final String CREDENTIALS_FILE_PATH = "/opt/tomcat/content/DigiturkCoUk/gmail/digiturkcouk_credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "/opt/tomcat/content/DigiturkCoUk/gmail/tokens";
    private static final String CONTACT_EMAIL_FILE_PATH_TR = "/opt/tomcat/content/DigiturkCoUk/gmail/template/contact_tr.html";
    private static final String CONTACT_EMAIL_FILE_PATH_EN = "/opt/tomcat/content/DigiturkCoUk/gmail/template/contact_en.html";
    private static final String FORGET_PASSWORD_EMAIL_FILE_PATH_TR = "/opt/tomcat/content/DigiturkCoUk/gmail/template/reset_password_tr.html";
    private static final String FORGET_PASSWORD_EMAIL_FILE_PATH_EN = "/opt/tomcat/content/DigiturkCoUk/gmail/template/reset_password_en.html";
    private static final String REGISTER_EMAIL_FILE_PATH_TR = "/opt/tomcat/content/DigiturkCoUk/gmail/template/welcome_tr.html";
    private static final String REGISTER_EMAIL_FILE_PATH_EN = "/opt/tomcat/content/DigiturkCoUk/gmail/template/welcome_en.html";
    private static final String NEWSLETTER_EMAIL_FILE_PATH_TR = "/opt/tomcat/content/DigiturkCoUk/gmail/template/newsletter_tr.html";
    private static final String NEWSLETTER_EMAIL_FILE_PATH_EN = "/opt/tomcat/content/DigiturkCoUk/gmail/template/newsletter_en.html";
    private static final String RENEWAL_EMAIL_FILE_PATH_TR = "/opt/tomcat/content/DigiturkCoUk/gmail/template/renewal_tr.html";
    private static final String RENEWAL_EMAIL_FILE_PATH_EN = "/opt/tomcat/content/DigiturkCoUk/gmail/template/renewal_en.html";
    private static final String RENEWAL_INFO_EMAIL_FILE_PATH_TR = "/opt/tomcat/content/DigiturkCoUk/gmail/template/renewal_info_tr.html";
    private static final String RENEWAL_INFO_EMAIL_FILE_PATH_EN = "/opt/tomcat/content/DigiturkCoUk/gmail/template/renewal_info_en.html";
    private static final String AGAIN_PAY_ORDER_INFO_EMAIL_FILE_PATH_TR = "/opt/tomcat/content/DigiturkCoUk/gmail/template/again_pay_order_info_tr.html";
    private static final String AGAIN_PAY_ORDER_INFO_EMAIL_FILE_PATH_EN = "/opt/tomcat/content/DigiturkCoUk/gmail/template/again_pay_order_info_en.html";



    /**
     * @param toList
     * @param from
     * @param subject
     * @return
     */


    /**
     * @param toList
     * @param from
     * @param subject
     * @param localUser
     * @param packageName
     * @return
     * @throws MessagingException
     * @throws FileNotFoundException
     */

    /**
     * @param recipientList
     * @param localUser
     * @param productDescriptionList
     * @return
     */

    /**
     * @param toList
     * @param from
     * @param subject
     * @param localUserSession
     * @return
     * @throws MessagingException
     * @throws FileNotFoundException
     */

    /**
     * @param recipientList
     * @param selectedNewsArticleVW
     * @return
     */
    public static boolean sendNewsletterEmail(List<String> recipientList, NewsArticleVW selectedNewsArticleVW) {
        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        boolean done = false;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName("DigiturkCoUk")
                    .build();

            String subject;
            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    subject = "DigiturkCoUk - Blog Post";
                    break;
                default:
                    subject = "DigiturkCoUk - Blog Yazısı";
                    break;
            }

            MimeMessage mimeMessage = createNewsletterEmail(recipientList, "info@keas.com.tr", subject, selectedNewsArticleVW);

            sendMessage(service, "info@keas.com.tr", mimeMessage);

            done = true;

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return done;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param toList                email address of the receiver
     * @param from                  email address of the sender, the mailbox account
     * @param subject               subject of the email
     * @param selectedNewsArticleVW selectedNewsArticleVW text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createNewsletterEmail(List<String> toList, String from, String subject, NewsArticleVW selectedNewsArticleVW) throws MessagingException {
        String content = "";

        try {

            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    content = new String(Files.readAllBytes(Paths.get(NEWSLETTER_EMAIL_FILE_PATH_EN)), StandardCharsets.UTF_8);
                    content = content.replace("{{template_title}}", "YEMEKHANE - Blog Post");
                    content = content.replace("{{blog_header}}", selectedNewsArticleVW.getHeadline());
                    content = content.replace("{{blog_extract}}", selectedNewsArticleVW.getExtract());
                    content = content.replace("{{slug}}", selectedNewsArticleVW.getSlug());
                    content = content.replace("{{live_chat_url}}", " (  +44 75 3512 6237  ) ");
                    break;

                default:
                    content = new String(Files.readAllBytes(Paths.get(NEWSLETTER_EMAIL_FILE_PATH_TR)), StandardCharsets.UTF_8);
                    content = content.replace("{{template_title}}", "YEMEKHANE - Blog Yazısı");
                    content = content.replace("{{blog_header}}", selectedNewsArticleVW.getHeadline());
                    content = content.replace("{{blog_extract}}", selectedNewsArticleVW.getExtract());
                    content = content.replace("{{slug}}", selectedNewsArticleVW.getSlug());
                    content = content.replace("{{live_chat_url}}", " (  +44 75 3512 6237  ) ");
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        // Create the HTML text part of the message.
        MimeMultipart multipart = new MimeMultipart("mixed");
        BodyPart bp = new MimeBodyPart();
        bp.setContent(content, "text/html; charset=UTF-8");
        multipart.addBodyPart(bp);

        try {
            email.setFrom(new InternetAddress(from, "DigiturkCoUk"));
        } catch (UnsupportedEncodingException e) {
            email.setFrom(new InternetAddress(from));
        }

        for (String to : toList) {
            email.addRecipient(javax.mail.Message.RecipientType.BCC,
                    new InternetAddress(to));
        }

        email.setSubject(subject);
        email.setContent(multipart);

        return email;
    }

    public static boolean sendContactEmail(List<String> recipientList, ContactMessage contactMessage) {
        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        boolean done = false;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName("DigiturkCoUk")
                    .build();

            String subject;
            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    subject = "YEMEKHANE - Contact Page";
                    break;
                default:
                    subject = "YEMEKHANE - İletişim Sayfası";
                    break;
            }

            MimeMessage mimeMessage = createContactEmail(recipientList, "info@keas.com.tr", subject, contactMessage);

            sendMessage(service, "info@keas.com.tr", mimeMessage);

            done = true;

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return done;
    }


    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param toList         email address of the receiver
     * @param from           email address of the sender, the mailbox account
     * @param subject        subject of the email
     * @param contactMessage contactMessage text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createContactEmail(List<String> toList, String from, String subject, ContactMessage contactMessage) throws MessagingException {
        String content = "";

        try {
            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    content = new String(Files.readAllBytes(Paths.get(CONTACT_EMAIL_FILE_PATH_EN)), StandardCharsets.UTF_8);
                    content = content.replace("{{template_title}}", "YEMEKHANE - Contact");
                    content = content.replace("{{name}}", contactMessage.getFullName());
                    content = content.replace("{{email}}", contactMessage.getEmail());
                    content = content.replace("{{subject}}", contactMessage.getSubject());
                    content = content.replace("{{message}}", contactMessage.getMessageBody());
                    break;

                default:
                    content = new String(Files.readAllBytes(Paths.get(CONTACT_EMAIL_FILE_PATH_TR)), StandardCharsets.UTF_8);
                    content = content.replace("{{template_title}}", "YEMEKHANE - İletişim");
                    content = content.replace("{{name}}", contactMessage.getFullName());
                    content = content.replace("{{email}}", contactMessage.getEmail());
                    content = content.replace("{{subject}}", contactMessage.getSubject());
                    content = content.replace("{{message}}", contactMessage.getMessageBody());
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        // Create the HTML text part of the message.
        MimeMultipart multipart = new MimeMultipart("mixed");
        BodyPart bp = new MimeBodyPart();
        bp.setContent(content, "text/html; charset=UTF-8");
        multipart.addBodyPart(bp);

        try {
            email.setFrom(new InternetAddress(from, "DigiturkCoUk"));
        } catch (UnsupportedEncodingException e) {
            email.setFrom(new InternetAddress(from));
        }

        for (String to : toList) {
            email.addRecipient(javax.mail.Message.RecipientType.BCC,
                    new InternetAddress(to));
        }

        email.setSubject(subject);
        email.setContent(multipart);

        return email;
    }

    /**
     * @param recipientList
     * @param forgetPasswordToken
     * @param localUser
     * @return
     */
    public static boolean sendForgetPasswordEmail(List<String> recipientList, ForgetPasswordToken forgetPasswordToken, LocalUser localUser) {
        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        boolean done = false;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName("DigiturkCoUk")
                    .build();

            String subject;
            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    subject = "YEMEKHANE - Password Renewal";
                    break;
                default:
                    subject = "YEMEKHANE - Şifre Yenileme";
                    break;
            }

            MimeMessage mimeMessage = createForgetPasswordEmail(recipientList, "info@keas.com.tr", subject, forgetPasswordToken, localUser);

            sendMessage(service, "info@keas.com.tr", mimeMessage);

            done = true;

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return done;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param toList  email address of the receiver
     * @param from    email address of the sender, the mailbox account
     * @param subject subject of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createForgetPasswordEmail(List<String> toList, String from, String subject, ForgetPasswordToken forgetPasswordToken, LocalUser localUser)
            throws MessagingException, FileNotFoundException {
        String content = "";

        try {
            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    content = new String(Files.readAllBytes(Paths.get(FORGET_PASSWORD_EMAIL_FILE_PATH_EN)), StandardCharsets.UTF_8);
                    content = content.replace("$$product_name$$", "YEMEKHANE - Password Renewal");
                    content = content.replace("$$name$$", localUser.getFirstName() + " " + localUser.getLastName());
                    content = content.replace("$$support_url$$", "https://www.keas.com.tr/contact");
                    content = content.replace("$$action_url$$", "https://www.keas.com.tr/renewpassword/" + forgetPasswordToken.getToken());
                    content = content.replace("$$action_url_name$$", "https://www.keas.com.tr/renewpassword/" + forgetPasswordToken.getToken());
                    break;

                default:
                    content = new String(Files.readAllBytes(Paths.get(FORGET_PASSWORD_EMAIL_FILE_PATH_TR)), StandardCharsets.UTF_8);
                    content = content.replace("$$product_name$$", "YEMEKHANE - Şifre Yenileme");
                    content = content.replace("$$name$$", localUser.getFirstName() + " " + localUser.getLastName());
                    content = content.replace("$$support_url$$", "https://www.keas.com.tr/contact");
                    content = content.replace("$$action_url$$", "https://www.keas.com.tr/renewpassword/" + forgetPasswordToken.getToken());
                    content = content.replace("$$action_url_name$$", "https://www.keas.com.tr/renewpassword/" + forgetPasswordToken.getToken());
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        // Create the HTML text part of the message.
        MimeMultipart multipart = new MimeMultipart("mixed");
        BodyPart bp = new MimeBodyPart();
        bp.setContent(content, "text/html; charset=UTF-8");
        multipart.addBodyPart(bp);

        try {
            email.setFrom(new InternetAddress(from, "DigiturkCoUk"));
        } catch (UnsupportedEncodingException e) {
            email.setFrom(new InternetAddress(from));
        }

        for (String to : toList) {
            email.addRecipient(javax.mail.Message.RecipientType.BCC,
                    new InternetAddress(to));
        }

        email.setSubject(subject);
        email.setContent(multipart);


        return email;
    }

    /**
     * @param recipientList
     * @param localUser
     * @return
     */
    public static boolean sendRegisterEmail(List<String> recipientList, LocalUser localUser) {
        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        boolean done = false;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName("DigiturkCoUk")
                    .build();

            String subject;
            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    subject = "YEMEKHANE - Membership";
                    break;
                default:
                    subject = "YEMEKHANE - Üyelik";
                    break;
            }

            MimeMessage mimeMessage = createRegisterEmail(recipientList, "info@keas.com.tr", subject, localUser);

            sendMessage(service, "info@keas.com.tr", mimeMessage);

            done = true;

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return done;
    }

    /**
     * @param toList
     * @param from
     * @param subject
     * @param localUser
     * @return
     * @throws MessagingException
     * @throws FileNotFoundException
     */
    public static MimeMessage createRegisterEmail(List<String> toList, String from, String subject, LocalUser localUser)
            throws MessagingException, FileNotFoundException {
        String content = "";

        try {

            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    content = new String(Files.readAllBytes(Paths.get(REGISTER_EMAIL_FILE_PATH_EN)), StandardCharsets.UTF_8);
                    content = content.replace("{{template_title}}", "YEMEKHANE - Membership");
                    content = content.replace("{{name}}", localUser.getFirstName() + " " + localUser.getLastName());
                    content = content.replace("{{username}}", localUser.getEmail());
                    content = content.replace("{{support_url}}", "https://www.keas.com.tr/contact");
                    content = content.replace("{{live_chat_url}}", " (  +44 75 3512 6237  ) ");
                    content = content.replace("{{action_url}}", "https://www.keas.com.tr/email-verification/" + localUser.getAccessToken());
                    content = content.replace("{{action_url_name}}", "https://www.keas.com.tr/email-verification/" + localUser.getAccessToken());
                    break;

                default:
                    content = new String(Files.readAllBytes(Paths.get(REGISTER_EMAIL_FILE_PATH_TR)), StandardCharsets.UTF_8);
                    content = content.replace("{{template_title}}", "YEMEKHANE - Üyelik");
                    content = content.replace("{{name}}", localUser.getFirstName() + " " + localUser.getLastName());
                    content = content.replace("{{username}}", localUser.getEmail());
                    content = content.replace("{{support_url}}", "https://www.keas.com.tr/contact");
                    content = content.replace("{{live_chat_url}}", " (  +44 75 3512 6237  ) ");
                    content = content.replace("{{action_url}}", "https://www.keas.com.tr/email-verification/" + localUser.getAccessToken());
                    content = content.replace("{{action_url_name}}", "https://www.keas.com.tr/email-verification/" + localUser.getAccessToken());
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        // Create the HTML text part of the message.
        MimeMultipart multipart = new MimeMultipart("mixed");
        BodyPart bp = new MimeBodyPart();
        bp.setContent(content, "text/html; charset=UTF-8");
        multipart.addBodyPart(bp);

        try {
            email.setFrom(new InternetAddress(from, "DigiturkCoUk"));
        } catch (UnsupportedEncodingException e) {
            email.setFrom(new InternetAddress(from));
        }

        for (String to : toList) {
            email.addRecipient(javax.mail.Message.RecipientType.BCC,
                    new InternetAddress(to));
        }

        email.setSubject(subject);
        email.setContent(multipart);


        return email;
    }

    public static boolean sendOrderEmail(List<String> recipientList, String body) {
        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        boolean done = false;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName("DigiturkCoUk")
                    .build();

            String subject;
            switch (LanguageItemView.getCurrentLanguage()) {
                case "en":
                    subject = "YEMEKHANE - Order Detail";
                    break;
                default:
                    subject = "YEMEKHANE - Sipariş Detayı";
                    break;
            }

            MimeMessage mimeMessage = createOrderEmail(recipientList, "info@keas.com.tr", subject, body);

            sendMessage(service, "info@keas.com.tr", mimeMessage);

            done = true;

        } catch (MessagingException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return done;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        InputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        SCOPES = new ArrayList<>();

        SCOPES.add("https://www.googleapis.com/auth/gmail.send");

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("info@keas.com.tr");
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service      Authorized Gmail API instance.
     * @param userId       User's email address. The special value "me"
     *                     can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    public static Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        return message;
    }


    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to       email address of the receiver
     * @param from     email address of the sender, the mailbox account
     * @param subject  subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));

        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));

        email.setSubject(subject);

        email.setContent(bodyText, "text/html");

        return email;
    }


    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param toList   email address of the receiver
     * @param from     email address of the sender, the mailbox account
     * @param subject  subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createOrderEmail(List<String> toList, String from, String subject, String bodyText) throws MessagingException {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        // Create the HTML text part of the message.
        MimeMultipart multipart = new MimeMultipart("mixed");
        BodyPart bp = new MimeBodyPart();
        bp.setContent(bodyText, "text/html; charset=UTF-8");
        multipart.addBodyPart(bp);

        try {
            email.setFrom(new InternetAddress(from, "DigiturkCoUk"));

        } catch (UnsupportedEncodingException e) {
            email.setFrom(new InternetAddress(from));
        }

        for (String to : toList) {
            email.addRecipient(javax.mail.Message.RecipientType.BCC,
                    new InternetAddress(to));
        }

        email.setSubject(subject);
        email.setContent(multipart);

        return email;
    }


    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        emailContent.writeTo(buffer);

        byte[] bytes = buffer.toByteArray();

        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);

        Message message = new Message();

        message.setRaw(encodedEmail);

        return message;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to       Email address of the receiver.
     * @param from     Email address of the sender, the mailbox account.
     * @param subject  Subject of the email.
     * @param bodyText Body text of the email.
     * @param file     Path to the file to be attached.
     * @return MimeMessage to be used to send email.
     * @throws MessagingException
     */
    public static MimeMessage createEmailWithAttachment(String to,
                                                        String from,
                                                        String subject,
                                                        String bodyText,
                                                        File file)
            throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyText, "text/plain");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeBodyPart = new MimeBodyPart();

        mimeBodyPart.setFileName(file.getName());

        multipart.addBodyPart(mimeBodyPart);
        email.setContent(multipart);

        return email;
    }


}