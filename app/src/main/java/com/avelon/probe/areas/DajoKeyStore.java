package com.avelon.probe.areas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.ECGenParameterSpec;
import java.util.Enumeration;

public class DajoKeyStore extends AbstractManager {
    private KeyStore keyStore;
    public static String[] permissions = new String[] {};

    public DajoKeyStore(Context ctx) throws Exception {
        super(ctx, permissions);

        keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
    }

    @Override
    public void orchestrate() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
        generator.initialize(new KeyGenParameterSpec.Builder("alias", KeyProperties.PURPOSE_ENCRYPT)
                .setAlgorithmParameterSpec(new ECGenParameterSpec("secp384r1"))
                .build());
        KeyPair pair = generator.generateKeyPair();
        KeyFactory factory = KeyFactory.getInstance(pair.getPrivate().getAlgorithm(), "AndroidKeyStore");
        KeyInfo info = factory.getKeySpec(pair.getPrivate(), KeyInfo.class);
        System.out.println("" + info.isInsideSecureHardware());

        String cert = "-----BEGIN CERTIFICATE-----\n" +
                "MIICEjCCAXsCAg36MA0GCSqGSIb3DQEBBQUAMIGbMQswCQYDVQQGEwJKUDEOMAwG\n" +
                "A1UECBMFVG9reW8xEDAOBgNVBAcTB0NodW8ta3UxETAPBgNVBAoTCEZyYW5rNERE\n" +
                "MRgwFgYDVQQLEw9XZWJDZXJ0IFN1cHBvcnQxGDAWBgNVBAMTD0ZyYW5rNEREIFdl\n" +
                "YiBDQTEjMCEGCSqGSIb3DQEJARYUc3VwcG9ydEBmcmFuazRkZC5jb20wHhcNMTIw\n" +
                "ODIyMDUyNjU0WhcNMTcwODIxMDUyNjU0WjBKMQswCQYDVQQGEwJKUDEOMAwGA1UE\n" +
                "CAwFVG9reW8xETAPBgNVBAoMCEZyYW5rNEREMRgwFgYDVQQDDA93d3cuZXhhbXBs\n" +
                "ZS5jb20wXDANBgkqhkiG9w0BAQEFAANLADBIAkEAm/xmkHmEQrurE/0re/jeFRLl\n" +
                "8ZPjBop7uLHhnia7lQG/5zDtZIUC3RVpqDSwBuw/NTweGyuP+o8AG98HxqxTBwID\n" +
                "AQABMA0GCSqGSIb3DQEBBQUAA4GBABS2TLuBeTPmcaTaUW/LCB2NYOy8GMdzR1mx\n" +
                "8iBIu2H6/E2tiY3RIevV2OW61qY2/XRQg7YPxx3ffeUugX9F4J/iPnnu1zAxxyBy\n" +
                "2VguKv4SWjRFoRkIfIlHX0qVviMhSlNy2ioFLy7JcPZb+v3ftDGywUqcBiVDoea0\n" +
                "Hn+GmxZA\n" +
                "-----END CERTIFICATE-----";

        String rootCert = cert;
        String clientCert = cert;
        String certCert = cert;

        try {
            {
                // Diagnostic Routine
                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);

                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                keyStore.setCertificateEntry("root_alias", cf.generateCertificate(new ByteArrayInputStream(rootCert.getBytes())));
                keyStore.setCertificateEntry("client_alias", cf.generateCertificate(new ByteArrayInputStream(clientCert.getBytes())));
                keyStore.setCertificateEntry("cert_alias", cf.generateCertificate(new ByteArrayInputStream(certCert.getBytes())));
            }

            // Android Auto
            {
                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);

                Enumeration<String> aliases = keyStore.aliases();
                while(aliases.hasMoreElements()) {
                    Log.i(TAG, "alias=" + aliases.nextElement());
                }

                Log.i(TAG, "root=" + keyStore.getCertificate("root_alias"));
                Log.i(TAG, "root=" + keyStore.getCertificate("cert_alias"));
                Log.i(TAG, "root=" + keyStore.getCertificate("client_alias"));
            }
        }
        catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        //store.importKey("root", new byte[] {0, 0, 22});
        //store.importKey("root", new byte[] {0, 0, 22});
        //store.importKey("root", new byte[] {0, 0, 22});
    }
}
