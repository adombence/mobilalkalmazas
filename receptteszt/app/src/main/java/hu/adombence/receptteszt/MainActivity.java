package hu.adombence.receptteszt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    static final String URL = "https://www.adombence.hu/serverside/get_recipes.php";
    private ListView listView;

    ArrayList<HashMap<String, String>> valueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        getJSON();
    }

    private void getJSON() {

        @SuppressLint("StaticFieldLeak")
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            //String id = obj.getString("id");
            String title = obj.getString("title");
            int energy = obj.getInt("energy");

            HashMap<String, String> values = new HashMap<>();

            //values.put("id", id);
            values.put("title", title);
            values.put("energy", String.valueOf(energy));

            valueList.add(values);
            Log.d("Recipes", String.valueOf(valueList));
        }

        ListAdapter adapter = new SimpleAdapter(MainActivity.this, valueList,
                R.layout.list_item, new String[]{"title", "energy"},
                new int[]{R.id.title, R.id.energy});
        listView.setAdapter(adapter);
    }
}