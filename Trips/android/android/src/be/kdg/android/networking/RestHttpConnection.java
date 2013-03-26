package be.kdg.android.networking;

import org.apache.http.NameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * User: Sander
 * Date: 2/03/13 - 19:48
 */
public class RestHttpConnection {
    private HttpURLConnection urlConnection;
    private URL url;
    private String query;
    private List<NameValuePair> params;
    private final int TIMEOUT = 10000;

    private void disconnect() {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }

    private void connect(boolean input, boolean output, String type) throws IOException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(input);
        urlConnection.setDoOutput(output);
        urlConnection.setRequestMethod(type);
        urlConnection.setConnectTimeout(TIMEOUT);
        urlConnection.setReadTimeout(TIMEOUT);

        if (output) {
            query = getQuery(params);
            urlConnection.setFixedLengthStreamingMode(query.getBytes().length);
        }

        urlConnection.connect();
    }

    public String doGet(String url) throws IOException {
        init(url, null);
        connect(true, false, "GET");

        String result = readInputStream();
        disconnect();

        return result;
    }

    public String doGetWithParams(String url, List<NameValuePair> params) throws IOException {
        init(url, params);
        connect(true, true, "GET");

        writeOutputStream();

        String result = readInputStream();
        disconnect();

        return result;
    }

    public void doPost(String url, List<NameValuePair> params) throws IOException {
        init(url, params);
        connect(false, true, "POST");

        writeOutputStream();
        disconnect();
    }

    public String doPostWithResult(String url, List<NameValuePair> params) throws IOException {
        init(url, params);
        connect(true, true, "POST");

        writeOutputStream();

        String result = readInputStream();
        disconnect();

        return result;
    }

    private String readInputStream() throws IOException {
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
        return "";
    }

    private void writeOutputStream() throws IOException {
        OutputStream out = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.write(query);
        writer.close();
        out.close();
    }

    private void init(String url, List<NameValuePair> params) throws MalformedURLException {
        this.url = new URL(url);
        this.params = params;
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
