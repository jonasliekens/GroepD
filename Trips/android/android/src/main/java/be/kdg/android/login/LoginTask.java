package be.kdg.android.login;

import android.os.AsyncTask;
import be.kdg.android.networking.RestHttpConnection;
import be.kdg.android.utilities.Utilities;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Sander
 * Date: 27/02/13 9:20
 */
public class LoginTask extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... data) {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String username = data[0];
            String password = data[1]; // sha-256!
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));

            RestHttpConnection restHttpConnection = new RestHttpConnection();
            String result = restHttpConnection.doPostWithResult(Utilities.LOGIN_ADDRESS, params);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "null";
    }
}
