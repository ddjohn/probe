package com.avelon.probe.areas.concepts.tls;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SslTrustManagerHelper {

    private InputStream keyStore;
    private String keyStorePassword;
    private InputStream trustStore;
    private String trustStorePassword;

    public SslTrustManagerHelper(InputStream keyStore,
                                 String keyStorePassword,
                                 InputStream trustStore,
                                 String trustStorePassword) throws Exception {
        if (keyStore == null || keyStorePassword.trim().isEmpty() || trustStore == null || trustStorePassword.trim().isEmpty()) {
            throw new Exception("TrustStore or KeyStore details are empty, which are required to be present when SSL is enabled");
        }

        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
        this.trustStore = trustStore;
        this.trustStorePassword = trustStorePassword;
    }

    public SSLContext clientSSLContext() throws Exception {
        try {
            TrustManagerFactory trustManagerFactory = getTrustManagerFactory(trustStore, trustStorePassword);
            KeyManagerFactory keyManagerFactory = getKeyManagerFactory(keyStore, keyStorePassword);
            this.keyStore.close();
            this.trustStore.close();

            return getSSLContext(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private static SSLContext getSSLContext(KeyManager[] keyManagers, TrustManager[] trustManagers) throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(keyManagers, trustManagers, null);
        return sslContext;
    }

    private static KeyManagerFactory getKeyManagerFactory(InputStream keystore, String keystorePassword) throws Exception {
        KeyStore keyStore = loadKeyStore(keystore, keystorePassword);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());
        return keyManagerFactory;
    }

    private static TrustManagerFactory getTrustManagerFactory(InputStream truststore, String truststorePassword) throws Exception {
        KeyStore trustStore = loadKeyStore(truststore, truststorePassword);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        return trustManagerFactory;
    }

    private static KeyStore loadKeyStore(InputStream keystoreStream, String keystorePassword) throws Exception {
        if (keystoreStream == null) {
            throw new Exception("keystore was null.");
        }

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(keystoreStream, keystorePassword.toCharArray());
        return keystore;
    }

}