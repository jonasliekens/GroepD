package be.kdg.android.login;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import be.kdg.android.utilities.Constants;
import be.kdg.android.utilities.NetworkUtilities;
import de.akquinet.android.androlog.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Sander
 * Date: 27/02/13 9:20
 */
public class LoginTask extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... data) {
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(Constants.LOGIN_ADDRESS);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String username = data[0];
            String password = data[1]; // sha-256!
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));

            String query = NetworkUtilities.getQuery(params);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
//            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setFixedLengthStreamingMode(query.getBytes().length);

            OutputStream out = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(query);
            writer.close();
            out.close();

            urlConnection.connect();

            StringBuilder response = new StringBuilder();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (urlConnection != null) {
            urlConnection.disconnect();
        }

        return "null";
    }
}
