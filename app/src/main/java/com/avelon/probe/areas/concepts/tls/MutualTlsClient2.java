package com.avelon.probe.areas.concepts.tls;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.avelon.probe.R;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

public class MutualTlsClient2 {
    private static final String TAG = MutualTlsClient2.class.getCanonicalName();

    public MutualTlsClient2(Context ctx) throws Exception {
        Log.e(TAG, "MutualTlsClient()");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
/*
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> {
            Log.e(TAG, "Do we trust " + hostname + "?");
            Log.e(TAG, "  session=" + session.getPeerHost());
            Log.e(TAG, "  session=" + session.getProtocol());
            Log.e(TAG, "  session=" + session.getCipherSuite());
            Log.e(TAG, "  session=" + session.getCreationTime());
            Log.e(TAG, "  session=" + session.getApplicationBufferSize());
            Log.e(TAG, "  session=" + session.getId().length);
            Log.e(TAG, "  session=" + session.getLocalCertificates());
            Log.e(TAG, "  session=" + session.getPacketBufferSize());
            Log.e(TAG, "  session=" + session.getPeerPort());
            return true;
        });
*/
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{ new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.e(TAG, "checkClientTrusted()");
            }
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.e(TAG, "checkServerTrusted()");
            }
            public X509Certificate[] getAcceptedIssuers() {
                Log.e(TAG, "getAcceptedIssuers()");
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

        Log.e(TAG, "https://www.aftonbladet.se");

        URL url = new URL("https://vecka.nu");

        /* Native */
        HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
        //urlConnection.setSSLSocketFactory(context.getSocketFactory());
        InputStream in = urlConnection.getInputStream();
        byte[] bytes = new byte[1024];
        in.read(bytes);
        Log.e(TAG, "response=" + new String(bytes, StandardCharsets.UTF_8));

        KeyStore identity = KeyStore.getInstance("BKS");
        identity.load(ctx.getResources().openRawResource(R.raw.client_identity), "android".toCharArray());

        KeyStore truststore = KeyStore.getInstance("BKS");
        truststore.load(ctx.getResources().openRawResource(R.raw.client_truststore), "android".toCharArray());

        Log.e(TAG, "identity=" + identity);
        Log.e(TAG, "truststore=" + truststore);

/*
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                //.useTLS()
                .build();
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, new AllowAllHostnameVerifier());
*/
/*
        SSLSocketFactory ssf = new SSLSocketFactory(new TrustStrategy(){
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.e(TAG, "isTrusted()");
                return true;
            }
        }, new AllowAllHostnameVerifier());
*/
        /*
        PlainSocketFactory psf = PlainSocketFactory.getSocketFactory();

        CloseableHttpClient client = HttpClientBuilder.create()
                //.setSSLSocketFactory(psf)
                .build();
        CloseableHttpResponse response = client.execute(new HttpGet("http://www.aftonbladet.se/"));
        Log.e(TAG, "Response code: " + response.getStatusLine().getStatusCode());
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
    private SSLSocketFactory createSSLSocketFactory(Context ctx) {
        Log.d(TAG, "Creating SSL socket factory");

         KeyStore truststore = ctx.loadStore(ctx.getResources().openRawResource(R.raw.clienttruststore, "android", "BKS");
         KeyStore keystore = this.loadStore(ctx.getResources().openRawResource(R.raw.client, "android", "BKS");
        return this.createFactory(keystore, "android", truststore);
    }

    private SSLSocketFactory createFactory(final KeyStore keystore, String keystorePassword, KeyStore truststore) {

        SSLSocketFactory factory;
        try {
            factory.setHostnameVerifier((X509HostnameVerifier) SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            Log.e(TAG, "Caught exception when trying to create ssl socket factory. Reason: " +  e.getMessage());
            throw new RuntimeException(e);
        }

        return factory;
    }

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
