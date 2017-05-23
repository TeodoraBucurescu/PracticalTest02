package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

/**
 * Created by student on 23.05.2017.
 */


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CommunicationThread extends Thread{

    final public static String TAG = "[PracticalTest02]";
    final public static boolean DEBUG = true;


    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    public void run() {
        if (socket == null) {
            Log.e(TAG, "[COMMUNICATION THREAD] Socket is null!");
            return;
        }
        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(TAG, "[COMMUNICATION THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            Log.i(TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type!");
            String ora = bufferedReader.readLine();
            String functie = bufferedReader.readLine();
            if (ora == null || ora.isEmpty() || functie == null || functie.isEmpty()) {
                Log.e(TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (city / information type!");
                return;
            }
            HashMap<String, String> data = serverThread.getData();

            if (data.containsKey(ora)) {
                Log.i(TAG, "[COMMUNICATION THREAD] Getting the information from the cache...");
            } else {
                Log.i(TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
            }
        } catch (IOException ioException) {
            Log.e(TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
            if (DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                    if (DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}
