package hu.adombence.receptteszt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    static final String URL = "https://www.adombence.hu/serverside/get_recipes.php";

    TextView receptNev, receptHozzavalok, receptLeiras, receptEnergia, receptSzenhidrat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receptNev = this.findViewById(R.id.receptnev);
        receptHozzavalok = this.findViewById(R.id.recepthozzavalok);
        receptLeiras = this.findViewById(R.id.receptleiras);
        receptEnergia = this.findViewById(R.id.receptenergia);
        receptHozzavalok = this.findViewById(R.id.recepthozzavalok);

        getJSON();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(MainActivity.this, "downloading", Toast.LENGTH_LONG).show();
            }


            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(MainActivity.URL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                    Log.d("SBTARTALOM", sb.toString());
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
}