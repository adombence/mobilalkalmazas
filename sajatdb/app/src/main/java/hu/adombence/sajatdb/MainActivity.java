package hu.adombence.sajatdb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.net.ConnectivityManagerCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb_xml;
    Button tovabb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pb_xml = findViewById(R.id.progressBar);
        //tovabb = findViewById(R.id.button);

        ConnectivityManager cman = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwi = cman.getActiveNetworkInfo();
        if (nwi == null || nwi.isConnected()) {
            AlertDialog.Builder figyelmeztetes = new AlertDialog.Builder(this);
            figyelmeztetes.setMessage("Nem érhető el hálózat");
            figyelmeztetes.setTitle("Hálózat elérési hiba");
            figyelmeztetes.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            figyelmeztetes.show();
        }
        XMLDarabLekerdezo darabLekerdezo = new XMLDarabLekerdezo();
        darabLekerdezo.execute();
    }

    public class XMLDarabLekerdezo extends AsyncTask<Void, Void, Integer> {

        int valasz;

        @Override
        protected Integer doInBackground(Void... voids) {

            HttpURLConnection con = null;
            URL url = null;

            try {
                url = new URL("https://studentlab.nye.hu/xml_lekerdezes.php");

                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");

                OutputStreamWriter ki = new OutputStreamWriter(con.getOutputStream());
                ki.write("keresett=" + " ");
                ki.flush();
                ki.close();

                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    valasz = -1;
                } else {
                    InputStream be = con.getInputStream();

                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(be);

                    NodeList nl = doc.getElementsByTagName("ember");
                    valasz = nl.getLength();
                }
                return valasz;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (valasz == -1) {
                AlertDialog.Builder figyelmeztetes = new AlertDialog.Builder(MainActivity.this);
                figyelmeztetes.setMessage("Hiva az XML lekérdezésnél");
                figyelmeztetes.setTitle("Lekérdezési hiba, vagy ");
                figyelmeztetes.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        valasz = 0;
                    }
                });
                figyelmeztetes.show();
            }

            if (valasz != 0) {
                Log.d("SAJAT", "DARABSZAM: " + integer);
                pb_xml.setMax(integer);
            }

        }
    }

}