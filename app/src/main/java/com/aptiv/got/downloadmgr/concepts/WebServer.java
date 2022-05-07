package com.aptiv.got.downloadmgr.concepts;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer implements Runnable {
    private static final String TAG = WebServer.class.getCanonicalName();

    public WebServer(Context context) {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(8080);

            while (true) {
                Socket clientSocket = socket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

                String s;
                while ((s = in.readLine()) != null) {
                    System.out.println(s);

                    out.write("HTTP/1.0 200 OK\r\n");
                    out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                    out.write("Server: Apache/0.8.4\r\n");
                    out.write("Content-Type: text/html\r\n");
                    out.write("Content-Length: 59\r\n");
                    out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                    out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                    out.write("\r\n");
                    out.write("<TITLE>Exemple</TITLE>");
                    out.write("<P>Ceci est une page d'exemple.</P>");
                }

                out.close();
                in.close();
                clientSocket.close();
            }
        }
        catch(Exception e) {
            Log.e(TAG, "exception", e);
        }
    }
}
