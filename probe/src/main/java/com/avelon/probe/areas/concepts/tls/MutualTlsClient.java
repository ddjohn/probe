package com.avelon.probe.areas.concepts.tls;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.avelon.probe.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class MutualTlsClient {
    private static final String TAG = MutualTlsClient.class.getCanonicalName();

    private final char[] PASSWORD_ORANGE = "orange".toCharArray();
    private final char[] PASSWORD_SNOWSTORM = "snowstorm".toCharArray();
    private final String TLS_VERSION = "TLSv1.2";

    public MutualTlsClient(Context ctx) throws Exception {
        Log.e(TAG, "MutualTlsClient()");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        KeyStore identity = KeyStore.getInstance("BKS");
        identity.load(ctx.getResources().openRawResource(R.raw.client_identity), PASSWORD_ORANGE);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(identity, "android".toCharArray());

        KeyStore truststore = KeyStore.getInstance("BKS");
        truststore.load(ctx.getResources().openRawResource(R.raw.client_truststore), PASSWORD_SNOWSTORM);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(truststore);

        SSLContext context = SSLContext.getInstance(TLS_VERSION);
        context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        URL url = new URL("https://localhost:8443/");
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setHostnameVerifier((hostname, session) -> true);
        connection.setSSLSocketFactory(context.getSocketFactory());
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

        Log.i(TAG, "Respond code: " + connection.getResponseCode());
        Log.i(TAG, "Got: " + reader.readLine());
    }
}
