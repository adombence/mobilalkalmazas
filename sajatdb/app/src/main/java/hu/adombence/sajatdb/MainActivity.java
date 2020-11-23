package hu.adombence.sajatdb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.net.ConnectivityManagerCompat;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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

    final static String DB_NAME = "adatbazis";
    final static String TABLE_NAME = "emberek";
    final static String COL_NEV = "nev";
    final static String COL_SZEV = "szev";
    static SQLiteDatabase adatbazis;
    ProgressBar pb_xml;
    Button tovabb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tovabb = this.findViewById(R.id.button);
        tovabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hivas = new Intent(getApplicationContext(), LekerdezoActivity.class);
                startActivity(hivas);
            }
        });
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
                XMLLekerdezo lekerdezo = new XMLLekerdezo();
                lekerdezo.execute();
            }

        }
    }

    public class XMLLekerdezo extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (adatbazisLetrehozo()) {
                Toast.makeText(MainActivity.this, "Adatbázis sikeresen létrehozva", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            HttpURLConnection con = null;
            URL url = null;
            int valasz = 0;
            int darab = 0;

            String nev_adat = "";
            int szev_adat = -1;

            try {
                url = new URL("https://studentlab.nye.hu/xml_lekerdezes.php");
                con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStreamWriter ki = new OutputStreamWriter(con.getOutputStream());
                ki.write("keresett=" + " ");
                ki.flush();
                ki.close();

                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    valasz = -1;

                } else {
                    InputStream be = con.getInputStream();
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(be, null);
                    parser.nextTag();

                    parser.require(XmlPullParser.START_TAG, null, "felhasznalok");

                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        String name = parser.getName();
                        if (name.equals("ember")) {
                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG, null, "nev");
                            if (parser.next() == XmlPullParser.TEXT) {
                                nev_adat = parser.getText();
                                parser.nextTag();
                                parser.require(XmlPullParser.END_TAG, null, "nev");
                            }

                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG, null, "szuletesiev");
                            if (parser.next() == XmlPullParser.TEXT) {
                                szev_adat = Integer.parseInt(parser.getText());
                                parser.nextTag();
                                parser.require(XmlPullParser.END_TAG, null, "szuletesiev");
                            }
                        }
                        parser.nextTag();
                        parser.require(XmlPullParser.END_TAG, null, "ember");

                        //adatázis beszúrás
                        if (adatbazis_beszuro(nev_adat, szev_adat)) {
                            darab++;
                            publishProgress(darab);
                        }
                    }
                    parser.require(XmlPullParser.END_TAG, null, "felhasznalok");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb_xml.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Toast.makeText(MainActivity.this, "Az adatbázis feltöltése sikeres", Toast.LENGTH_LONG).show();
            tovabb.setVisibility(Button.VISIBLE);
        }
    }

    public boolean adatbazisLetrehozo() {
        try {
            adatbazis = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            adatbazis.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            adatbazis.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NEV + " TEXT, " + COL_SZEV + " INTEGER);");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean adatbazis_beszuro(String nev, int szev) {
        try {
            //adatbazis.execSQL("INSERT INTO "+TABLE_NAME+"("+COL_NEV+", "+COL_SZEV+") VALUES ("+nev+", "+szev+");");
            ContentValues ctv = new ContentValues();
            ctv.put(COL_NEV, nev);
            ctv.put(COL_SZEV, szev);
            long sorszam = adatbazis.insert(TABLE_NAME, null, ctv);
            if (sorszam != -1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }

    }
}