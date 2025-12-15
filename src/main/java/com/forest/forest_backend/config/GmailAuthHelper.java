package com.forest.forest_backend.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.services.gmail.model.Message;

import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.util.Base64;
import java.util.Collections;
import java.util.Properties;

public class GmailAuthHelper {

    // ✅ Railway volume (PERSISTENT)
    private static final String TOKENS_DIR = "/app/gmail-tokens";

    // ✅ credentials.json mounted manually (NOT in git)
    private static final String CREDENTIALS_FILE = "/app/credentials/credentials.json";

    public static Gmail authorize() throws Exception {

        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();

        File credentialsFile = new File(CREDENTIALS_FILE);
        if (!credentialsFile.exists()) {
            throw new IllegalStateException(
                    "credentials.json not found at " + CREDENTIALS_FILE
            );
        }

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory, new FileReader(credentialsFile));

        File tokenDir = new File(TOKENS_DIR);
        if (!tokenDir.exists()) {
            tokenDir.mkdirs();
        }

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport,
                        jsonFactory,
                        clientSecrets,
                        Collections.singleton(GmailScopes.GMAIL_SEND)
                )
                        .setDataStoreFactory(new FileDataStoreFactory(tokenDir))
                        .setAccessType("offline")
                        .build();

        // ❌ DO NOT use LocalServerReceiver on Railway
        Credential credential = flow.loadCredential("user");

        if (credential == null) {
            throw new IllegalStateException(
                    "Gmail token missing. Generate token locally and mount it on Railway."
            );
        }

        return new Gmail.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Forest Backend Mailer")
                .build();
    }

    // ✅ REQUIRED METHOD — YOU WERE MISSING THIS
    public static void sendEmail(
            Gmail service,
            String fromEmail,
            String toEmail,
            String subject,
            String bodyText
    ) throws Exception {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(fromEmail));
        email.addRecipient(
                jakarta.mail.Message.RecipientType.TO,
                new InternetAddress(toEmail)
        );
        email.setSubject(subject);
        email.setText(bodyText);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);

        String encodedEmail = Base64.getUrlEncoder()
                .encodeToString(buffer.toByteArray());

        Message message = new Message();
        message.setRaw(encodedEmail);

        service.users().messages().send("me", message).execute();
    }
}
