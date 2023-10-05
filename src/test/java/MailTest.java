

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


import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.junit.jupiter.api.Test;
import org.primefaces.shaded.json.JSONObject;
import dininghall.common.gmail.GmailCredentials;
import dininghall.common.gmail.GmailService;
import dininghall.common.gmail.GmailServiceImpl;


import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import java.io.IOException;
import java.util.Properties;

public class MailTest
{

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
    private static final String CREDENTIALS_FILE_PATH = "D:/DigiturkCoUk/gmail/digiturkcouk_credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "D:/DigiturkCoUk/gmail/tokens";

    @Test
    public void testEmail()
    {
        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);


        try
        {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName("DigiturkCoUk")
                    .build();

            List<String> recipientList = new ArrayList();
            recipientList.add("tolgayeltekin@hotmail.com");

            MimeMessage mimeMessage = createEmail(recipientList, "info@keas.com.tr", "DigiturkCoUk - Test", "Body");

            sendMessage(service, "info@keas.com.tr", mimeMessage);

        } catch (MessagingException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmailAfterAuth()
    {
        try
        {
            GmailService gmailService = new GmailServiceImpl(GoogleNetHttpTransport.newTrustedTransport());
            gmailService.setGmailCredentials(GmailCredentials.builder()
                    .userEmail("info@keas.com.tr")
                    .clientId("712348985708-lugmkhb1a30hb36hgb7l246n3ssjindl.apps.googleusercontent.com")
                    .clientSecret("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")
                    .accessToken("ya29.GluCBY6YE-TzEU2-F86sRl_Gq5QyPmUNW2wEV0MynFN-L3HK2AHEUD09pknXfrvk8UY6NYnGwuCIxAh97s6ipVylgwoNIsxLs7uouIBqj8vWiAODGiS2a1ZDNa8D")
                    .refreshToken("1/XyMnZb4UfU8WDt6SnHIeE3wFTPyTAg2K16ZA7NIF0bY")
                    .build());

            gmailService.sendMessage("asstclinic@gmail.com", "Subject", "body text");

        } catch (GeneralSecurityException | IOException | MessagingException e)
        {
            e.printStackTrace();
        }
    }

    private String getAccessToken(String refreshToken){

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://accounts.google.com/o/oauth2/token");
        try
        {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("grant_type",    "refresh_token"));
            nameValuePairs.add(new BasicNameValuePair("client_id",     "712348985708-lugmkhb1a30hb36hgb7l246n3ssjindl.apps.googleusercontent.com"));
            nameValuePairs.add(new BasicNameValuePair("client_secret", "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"));
            nameValuePairs.add(new BasicNameValuePair("refresh_token", refreshToken));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            org.apache.http.HttpResponse response = client.execute(post);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer buffer = new StringBuffer();
            for (String line = reader.readLine(); line != null; line = reader.readLine())
            {
                buffer.append(line);
            }

            JSONObject json = new JSONObject(buffer.toString());
            String accessToken = json.getString("access_token");

            return accessToken;

        }
        catch (IOException e) { e.printStackTrace(); }

        return null;
    }


    public Credential authorize() throws Exception
    {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                SCOPES).setDataStoreFactory(
                new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH))).build();

        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("info@keas.com.tr");
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException
    {

        InputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));

        if (in == null)
        {
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

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(80).build();

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
    public Message sendMessage(Gmail service,
                               String userId,
                               MimeMessage emailContent)
            throws MessagingException, IOException
    {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());

        return message;
    }

    @Test
    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException
    {


        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);

        return email;
    }

    @Test
    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public MimeMessage createEmail(List<String> toList, String from, String subject, String bodyText) throws MessagingException
    {


        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        for (String to : toList)
        {
            email.addRecipient(javax.mail.Message.RecipientType.BCC,
                    new InternetAddress(to));
        }

        email.setSubject(subject);
        email.setText(bodyText);

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
            throws MessagingException, IOException
    {
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
            throws MessagingException, IOException
    {
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