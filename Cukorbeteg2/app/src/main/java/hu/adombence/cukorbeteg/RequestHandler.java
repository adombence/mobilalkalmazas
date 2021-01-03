package hu.adombence.cukorbeteg;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {
    String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        StringBuilder sb = new StringBuilder();
        Log.d("HASHTERKEP", String.valueOf(postDataParams));
        try {

            String postData = getPostDataString(postDataParams);
            Log.d("POSTADATA", postData);

            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os));
            writer.write(postData);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.d("RESPONSECODE", String.valueOf(responseCode));
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                while ((response = br.readLine()) != null) {
                    Log.d("RESPONSE", response);
                    sb.append(response);
                }
            } else {
                Log.d("RESPONSECODE", String.valueOf(responseCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    //method for converting key-value pairs data into a query string as needed to send to the server.
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.d("DEBUG", result.toString());
        Log.d("HASHTERKEP", String.valueOf(params));
        Log.d("HASHTERKEP", String.valueOf(result));
        return result.toString();
    }
}

