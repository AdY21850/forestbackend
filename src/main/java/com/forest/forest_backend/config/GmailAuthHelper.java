package com.forest.forest_backend.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.client.auth.oauth2.Credential;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Collections;

public class GmailAuthHelper {

    private static final String TOKENS_DIR = "/app/gmail-tokens";
    private static final String CREDENTIALS_FILE = "src/main/resources/credentials.json";

    public static Gmail authorize() throws Exception {

        ensureTokenExists(); // 🔥 IMPORTANT

        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();

        var clientSecrets = com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
                .load(jsonFactory, Files.newBufferedReader(new File(CREDENTIALS_FILE).toPath()));

        var flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport,
                jsonFactory,
                clientSecrets,
                Collections.singleton(GmailScopes.GMAIL_SEND)
        )
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIR)))
                .setAccessType("offline")
                .build();

        Credential credential = flow.loadCredential("user");

        return new Gmail.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Forest Backend Mailer")
                .build();
    }

    /**
     * Writes token from ENV into Railway volume if missing
     */
    private static void ensureTokenExists() throws Exception {
        File dir = new File(TOKENS_DIR);
        if (!dir.exists()) dir.mkdirs();

        File tokenFile = new File(dir, "StoredCredential");

        if (!tokenFile.exists()) {
            String tokenJson = System.getenv("GMAIL_TOKEN_JSON");

            if (tokenJson == null || tokenJson.isBlank()) {
                throw new IllegalStateException("GMAIL_TOKEN_JSON env variable missing");
            }

            try (FileWriter writer = new FileWriter(tokenFile)) {
                writer.write(tokenJson);
            }
        }
    }
}
