package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

/**
 * Created by student on 23.05.2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;
import android.widget.TextView;

public class ClientThread extends Thread{

    final public static String TAG = "[PracticalTest02]";
    final public static boolean DEBUG = true;

    private String data;
    private int port;
    String functie;

    private Socket socket;

    public ClientThread(String data, String functie) {
        this.data = data;
        this.functie = functie;
    }

    public void run() {
        try {
            socket = new Socket();
            if (socket == null) {
                Log.e(TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(data);
            printWriter.flush();

            printWriter.println(functie);
            printWriter.flush();
            //String weatherInformation;
            /*while ((weatherInformation = bufferedReader.readLine()) != null) {
                final String finalizedWeateherInformation = weatherInformation;
                weatherForecastTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        weatherForecastTextView.setText(finalizedWeateherInformation);
                    }
                });
            }*/
        } catch (IOException ioException) {
            Log.e(TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}


