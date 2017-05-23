package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class PracticalTest02MainActivity extends AppCompatActivity {

    final public static String TAG = "[PracticalTest02]";

    private EditText ora = null;
    private Button set = null;
    private Button reset = null;
    private Button poll = null;
    private EditText port = null;
    private Button connect = null;

    String functie = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;


    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = port.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }

    private SetButtonClickListener setButtonClickListener = new SetButtonClickListener();
    private class SetButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String textOra = ora.getText().toString();
            functie = "set";
            if (textOra == null || textOra.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Ora should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            clientThread = new ClientThread(textOra, functie);
            clientThread.start();

        }

    }

    private ResetButtonClickListener resetButtonClickListener = new ResetButtonClickListener();
    private class ResetButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String textOra = ora.getText().toString();
            functie = "reset";
            if (textOra == null || textOra.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Ora should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            clientThread = new ClientThread(textOra, functie);
            clientThread.start();

        }
    }

    private PollButtonClickListener pollButtonClickListener = new PollButtonClickListener();
    private class PollButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String textOra = ora.getText().toString();
            functie = "poll";
            if (textOra == null || textOra.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Ora should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                return;
            }
            clientThread = new ClientThread(textOra, functie);
            clientThread.start();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        connect.setOnClickListener(connectButtonClickListener);
        set.setOnClickListener(setButtonClickListener);
        reset.setOnClickListener(resetButtonClickListener);
        poll.setOnClickListener(pollButtonClickListener);


    }
}
