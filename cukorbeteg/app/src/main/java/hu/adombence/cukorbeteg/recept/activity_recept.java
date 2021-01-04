package hu.adombence.cukorbeteg.recept;



import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hu.adombence.cukorbeteg.R;

public class activity_recept extends AppCompatActivity {
    RecyclerView mRecyclerView;
    public List<FoodData> myFoodList, tempFoodList;
    FoodData mFoodData;
    public String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        fetchData process = new fetchData();
        process.execute();

        mRecyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity_recept.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();
    }

    @SuppressLint("StaticFieldLeak")
    public class fetchData extends AsyncTask<Void, Void, Void> {
        String data = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(activity_recept.this, R.string.letoltes, Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://www.adombence.hu/serverside/get_recipes.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            jsonData = data;
            //jsonData = jsonData.replace("", "á");
            jsonData = jsonData.replace("\\u00e1", "á");
            jsonData = jsonData.replace("\\u00e9", "é");
            jsonData = jsonData.replace("\\u00ed", "í");
            jsonData = jsonData.replace("\\u00f3", "ó");
            jsonData = jsonData.replace("\\u00f6", "ö");
            jsonData = jsonData.replace("\\u0151", "ő");
            jsonData = jsonData.replace("\\u00fa", "ú");
            jsonData = jsonData.replace("\\u00fc", "ü");
            jsonData = jsonData.replace("\\u0171", "ű");

            //nagybetűk
            jsonData = jsonData.replace("\\u00c1", "Á");
            jsonData = jsonData.replace("\\u00c9", "É");
            jsonData = jsonData.replace("\\u00cd", "Í");
            jsonData = jsonData.replace("\\u00d3", "Ó");
            jsonData = jsonData.replace("\\u00d6", "Ö");
            jsonData = jsonData.replace("\\u0150", "Ő");
            jsonData = jsonData.replace("\\u00da", "Ú");
            jsonData = jsonData.replace("\\u00dc", "Ü");
            jsonData = jsonData.replace("\\u0170", "Ű");

            jsonData = jsonData.replace("\\/", "/");

            Log.d("JSONDATA", jsonData);
            /*tempFoodList = new ArrayList<>();
            tempFoodList = jsonReader(jsonData);*/
            jsonReader(jsonData);
            //Log.d("TEMPFOODLIST", tempFoodList.toString());
        }


    }

    public void jsonReader(String finaljson) {
        List<FoodData> retFoodList;
        retFoodList = new ArrayList<>();
        try {
            JSONArray parentArray = new JSONArray(finaljson);

            int count = 0;
            int[] id = new int[parentArray.length()];
            String[] name = new String[parentArray.length()];
            String[] ingredient = new String[parentArray.length()];
            String[] description = new String[parentArray.length()];
            int[] energy = new int[parentArray.length()];
            int[] carbohydrate = new int[parentArray.length()];

            StringBuilder finalBufferedData = new StringBuilder();

            while (count < parentArray.length()) {

                JSONObject finalObject = parentArray.getJSONObject(count);
                id[count] = finalObject.getInt("id");
                name[count] = finalObject.getString("name");
                ingredient[count] = finalObject.getString("ingredient");
                description[count] = finalObject.getString("description");
                energy[count] = finalObject.getInt("energy");
                carbohydrate[count] = finalObject.getInt("carbohydrate");
                finalBufferedData.append(id[count]).append(" - ").append(name[count]).append(" - ").append(ingredient[count]).append(" - ").append(description[count]).append(" - ").append(energy[count]).append(" - ").append(carbohydrate[count]).append("\n");

                FoodData tempFoodData = new FoodData(String.valueOf(name[count]), String.valueOf(ingredient[count]), String.valueOf(description[count]), String.valueOf(energy[count]), String.valueOf(carbohydrate[count]), R.drawable.tomato_soup);
                Log.d("TEMPFOODDATA", tempFoodData.toString());
                myFoodList.add(tempFoodData);
                count++;
            }
            myAdapter myAdapter = new myAdapter(activity_recept.this, myFoodList);
            mRecyclerView.setAdapter(myAdapter);
            Log.d("JSONDATA", finalBufferedData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}