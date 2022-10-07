package com.avelon.probe.areas.concepts;

import android.content.Context;
import android.util.Log;

import com.avelon.probe.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class MutualTlsServer {
    private static final String TAG = MutualTlsServer.class.getCanonicalName();
    private final String TLS_VERSION = "TLSv1.2";

    private final char[] PASSWORD_BANANA = "banana".toCharArray();
    private final char[] PASSWORD_MAGENTA = "magenta".toCharArray();
    private final char[] PASSWORD_RAINSTORM = "rainstorm".toCharArray();

    public MutualTlsServer(Context ctx) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {
        Log.e(TAG, "MutualTlsServer()");

        /*
         * Load my identity
         */
        KeyStore identity = KeyStore.getInstance("BKS");
        identity.load(ctx.getResources().openRawResource(R.raw.server_identity), PASSWORD_BANANA);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
        keyManagerFactory.init(identity, PASSWORD_MAGENTA);

        /*
         * Load the clients certificate signed with client public key
         */
        KeyStore truststore = KeyStore.getInstance("BKS");
        truststore.load(ctx.getResources().openRawResource(R.raw.server_truststore), PASSWORD_RAINSTORM);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(truststore);

        /*
         * Initiate security on the socket communication
         */
        Log.i(TAG, "SSL Provider: " +SSLContext.getDefault().getProvider());
        SSLContext ssl = SSLContext.getInstance(TLS_VERSION);
        ssl.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),  new SecureRandom());
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", 8443);
        final ServerSocket server = ssl.getServerSocketFactory().createServerSocket(address.getPort(), 5, address.getAddress());

        /*
         * Manage requests
         */
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Log.i(TAG, "Listening...");

                    try {
                        Socket socket = server.accept();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                        Log.i(TAG, "Got a request...");
                        String line = reader.readLine();
                        while (!line.isEmpty()) {
                            Log.i(TAG, "  Header-line: " + line);
                            line = reader.readLine();
                        }
                        writer.write("HTTP/1.1 200 OK\r\n");
                        writer.write("Content-type: text/plain\r\n");
                        writer.write("\r\n");
                        writer.write("Hello World\r\n");
                        writer.flush();
                    }
                    catch(IOException e) {
                        Log.e(TAG, "exception", e);
                    }
                }
            }
        });
        t.start();
    }
}
