package be.kdg.android.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import be.kdg.android.R;
import be.kdg.android.utilities.Utilities;
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

        initControls();

        if (!Utilities.isOnline(this.getApplicationContext())) {
            Toast.makeText(this, R.string.network_noconnection, Toast.LENGTH_LONG).show();
        }
    }

    private void initControls() {
        txtUsername = (EditText) findViewById(R.id.login_username);
        txtPassword = (EditText) findViewById(R.id.login_password);
        btnLogin = (Button) findViewById(R.id.login_submit);
    }

    public void login(View view) {
        if (Utilities.isOnline(this.getApplicationContext())) {
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
                    finish();
                } else {
                    Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ExecutionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            Toast.makeText(this, R.string.network_noconnection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}