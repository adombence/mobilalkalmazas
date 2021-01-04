package hu.adombence.cukorbeteg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

public class activity_cukor extends AppCompatActivity {
    String userId;
    private ListView listView;
    ArrayList<HashMap<String, String>> valueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cukor);
        init();


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
                Toast.makeText(activity_cukor.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
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
                    URL url = new URL(URLS.URL_GET_SUGAR + userId);
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

            String id = obj.getString("id");
            String date = obj.getString("date");
            String value = obj.getString("value");

            HashMap<String, String> values = new HashMap<>();

            values.put("id", id);
            values.put("date", date);
            values.put("value", value);

            valueList.add(values);
            Log.d("VALUES", String.valueOf(valueList));
        }

        ListAdapter adapter = new SimpleAdapter(activity_cukor.this, valueList,
                R.layout.list_item, new String[]{"date", "value"},
                new int[]{R.id.date, R.id.value});
        listView.setAdapter(adapter);
    }


    void init() {
        //getting the current user
        User user = PrefManager.getInstance(this).getUser();

        //setting the value to the variable
        userId = String.valueOf(user.getId());
    }
}