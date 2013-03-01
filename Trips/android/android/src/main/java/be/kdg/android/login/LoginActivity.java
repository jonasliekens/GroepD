package be.kdg.android.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import be.kdg.android.R;
import de.akquinet.android.androlog.Log;

import java.util.concurrent.ExecutionException;

/**
 * User: Sander
 * Date: 26/02/13 14:05
 */
public class LoginActivity extends Activity {
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Log.v("TRIPS", "LoginActivity created");

        initControls();
    }

    private void initControls() {
        txtUsername = (EditText) findViewById(R.id.login_username);
        txtPassword = (EditText) findViewById(R.id.login_password);
        btnLogin = (Button) findViewById(R.id.login_submit);
    }

    public void login(View view) {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        String[] params = new String[2];
        params[0] = username;
        params[1] = password;

        LoginTask login = new LoginTask();
        login.execute(params);

        try {
            String result = login.get();
            if (result.equals("true")) {
                setResult(RESULT_OK);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
    }
}