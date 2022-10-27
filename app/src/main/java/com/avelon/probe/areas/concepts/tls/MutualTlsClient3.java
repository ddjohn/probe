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
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MutualTlsClient3 {
    private static final String TAG = MutualTlsClient3.class.getCanonicalName();

    private final char[] PASSWORD_ORANGE = "orange".toCharArray();
    private final char[] PASSWORD_SNOWSTORM = "snowstorm".toCharArray();
    private final String TLS_VERSION = "TLSv1.2";

    public MutualTlsClient3(Context ctx) throws Exception {
        Log.e(TAG, "MutualTlsClient()");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
/*
        HostnameVerifier verifier = (hostname, session) -> {
            Log.i(TAG, "Do we trust " + hostname + "?");
            Log.i(TAG, "  session=" + session.getPeerHost());
            Log.i(TAG, "  session=" + session.getPeerPort());
            Log.i(TAG, "  session=" + session.getProtocol());
            Log.i(TAG, "  session=" + session.getCipherSuite());
            return true;
        };
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
*/
        KeyStore identity = KeyStore.getInstance("BKS");
        identity.load(ctx.getResources().openRawResource(R.raw.client_identity), PASSWORD_ORANGE);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(identity, "android".toCharArray());
        Log.e(TAG, "identities= " + keyManagerFactory.getKeyManagers().length);

        KeyStore truststore = KeyStore.getInstance("BKS");
        truststore.load(ctx.getResources().openRawResource(R.raw.client_truststore), PASSWORD_SNOWSTORM);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(truststore);
        Log.e(TAG, "truststores= " + trustManagerFactory.getTrustManagers().length);

        SSLContext context2 = SSLContext.getInstance(TLS_VERSION);
        context2.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        Log.e(TAG, "truststore=" + truststore);

        SSLContext context = SSLContext.getInstance(TLS_VERSION);
        context.init(null, new X509TrustManager[]{ new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.e(TAG, "checkClientTrusted()");
            }
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.i(TAG, "Is server trusted?");
                Log.i(TAG, "  authtype: " + authType);
                for(X509Certificate x509 : chain.clone()) {
                    Log.i(TAG, "  certificate: " + x509.getSigAlgName());
                    Log.i(TAG, "  certificate: " + x509.getSigAlgOID());
                    Log.i(TAG, "  certificate: " + x509.getType());
                    Log.i(TAG, "  certificate: " + x509.getIssuerDN());
                }
            }
            public X509Certificate[] getAcceptedIssuers() {
                Log.e(TAG, "getAcceptedIssuers()");
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

        URL url = new URL("https://localhost:8443/");
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        //connection.setHostnameVerifier(verifier);
        //connection.setSSLSocketFactory(context2.getSocketFactory());
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

        Log.i(TAG, "Respond code: " + connection.getResponseCode());
        Log.i(TAG, "Got: " + reader.readLine());

/*
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                //.useTLS()
                .build();
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, new AllowAllHostnameVerifier());
*/


        /*
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keystore, "android".toCharArray())
                .build();
        SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslContext,  new AllowAllHostnameVerifier());
        builder.setSSLSocketFactory(sslConnectionFactory);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslConnectionFactory)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);
        builder.setConnectionManager(ccm);

        CloseableHttpClient httpClient = builder.build();
        HttpGet httpget = new HttpGet("https://192.168.0.10:8443/example-server");
        CloseableHttpResponse response = httpClient.execute(httpget);
        Log.e(TAG, "Response code: " + response.getStatusLine().getStatusCode());
        */
    }
/*

    @Override
    protected ClientConnectionManager createClientConnectionManager() {
        Log.d(TAG, "Creating client connection manager");

        final SchemeRegistry registry = new SchemeRegistry();

        Log.d(TAG, "Adding https scheme for port " + securePort);
        registry.register(new Scheme("https", this.createSSLSocketFactory(), this.securePort));

        return new SingleClientConnManager(getParams(), registry);
    }

 */
}
