package be.kdg.android.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import be.kdg.android.R;
import be.kdg.android.entities.User;
import be.kdg.android.networking.RestHttpConnection;
import be.kdg.android.utilities.Utilities;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Sander
 * Date: 26/02/13 14:05
 */
public class LoginActivity extends Activity {
    private EditText txtUsername;
    private EditText txtPassword;
    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initControls();
        initSettings();

        if (!Utilities.isOnline(this.getApplicationContext())) {
            Toast.makeText(this, R.string.network_noconnection, Toast.LENGTH_LONG).show();
        }
    }

    private void initSettings() {
        settings = getSharedPreferences(Utilities.PREFS_NAME, 0);
        settingsEditor = settings.edit();
    }

    private void initControls() {
        txtUsername = (EditText) findViewById(R.id.login_username);
        txtPassword = (EditText) findViewById(R.id.login_password);
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
        } else {
            Toast.makeText(this, R.string.network_noconnection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public class LoginTask extends AsyncTask<String, String, User> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, getString(R.string.login_progress_title),getString(R.string.login_progress_desc), true, false);
        }

        @Override
        protected User doInBackground(String... data) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                String username = data[0];
                String password = Utilities.getEncryptPassword(data[1]); // sha-256!
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                RestHttpConnection restHttpConnection = new RestHttpConnection();
                String result = restHttpConnection.doPostWithResult(Utilities.LOGIN_ADDRESS, params);

                if (!(result.equals("null") || result.isEmpty())) {
                    User user = Utilities.getUser(result);
                    return user;
                }
            } catch (IOException e) {
              e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            progressDialog.dismiss();

            if (user == null) {
                Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_LONG).show();
            } else {
                settingsEditor.putInt("userId", user.getId());
                settingsEditor.commit();

                setResult(RESULT_OK);
                finish();
            }
        }
    }
}