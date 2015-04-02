package fi.iki.elonen;

import android.*;
import android.util.Log;

import java.io.IOException;

public class ServerRunner {
    public static void run(Class serverClass) {
        try {
            executeInstance((NanoHTTPD) serverClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeInstance(NanoHTTPD server) {
        try {
            server.start();
        } catch (IOException ioe) {
        	
        	Log.e("Server", " not start.\n");
            System.exit(-1);
        }

        Log.e("Server", "Server started, Hit Enter to stop.\n");

    }
}
