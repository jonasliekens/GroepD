package be.kdg.android.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import be.kdg.android.R;

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
        initListener();
    }

    private void initControls() {
        txtUsername = (EditText)findViewById(R.id.login_username);
        txtPassword = (EditText)findViewById(R.id.login_password);
        btnLogin = (Button)findViewById(R.id.login_submit);
    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // REST-service aanroepen
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                Toast.makeText(getApplicationContext(),username + " " + password, Toast.LENGTH_LONG).show();
            }
        });
    }
}