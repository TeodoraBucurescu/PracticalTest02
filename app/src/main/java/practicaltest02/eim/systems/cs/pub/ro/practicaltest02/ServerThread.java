package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

/**
 * Created by student on 23.05.2017.
 */

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread{

    private int port = 0;
    private ServerSocket serverSocket = null;

    final public static String TAG = "[PracticalTest02]";
    final public static boolean DEBUG = true;

    private HashMap<String, String> data = null;
    String ceva = null;
    String i= "1";


    public ServerThread(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioException) {
            Log.e(TAG, "An exception has occurred: " + ioException.getMessage());
            if (DEBUG) {
                ioException.printStackTrace();
            }
        }
        this.data = new HashMap<>();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public synchronized void setData(String data, String ceva) {
        this.data.put(data, ceva);
    }

    public synchronized HashMap<String, String> getData() {
        return data;
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i(TAG, "[SERVER THREAD] Waiting for a client invocation...");
                Socket socket = serverSocket.accept();
                Log.i(TAG, "[SERVER THREAD] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (IOException ioException) {
            Log.e(TAG, "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
            if (DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e(TAG, "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
                if (DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}
