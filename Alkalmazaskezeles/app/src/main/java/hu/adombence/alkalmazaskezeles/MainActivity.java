package hu.adombence.alkalmazaskezeles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    EditText bemenet;
    Button gomb;
    TextView eredmeny;
    String talalat = "találatok száma= ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bemenet = (EditText) this.findViewById(R.id.inputText);
        gomb = (Button) this.findViewById(R.id.button);
        eredmeny = (TextView) this.findViewById(R.id.tv_eredmeny);

        gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seged = bemenet.getText().toString();
                //int x = Integer.parseInt(seged);
                Lekerdezo lekerdezo = new Lekerdezo();
                lekerdezo.execute(seged);

                XMLLekeredezo xmles = new XMLLekeredezo();
                xmles.execute(seged);
            }
        });

    }


    public class Lekerdezo extends AsyncTask<String, Void, String> {

        String eredeti = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eredeti = eredmeny.getText().toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "Válasz kód rendben", Toast.LENGTH_SHORT).show();
            eredmeny.setText(eredeti + "\n" + s);

        }

        @Override
        protected String doInBackground(String... strings) {

            String azonosito = strings[0];
            HttpURLConnection con = null;
            URL url = null;
            StringBuffer valasz = new StringBuffer();

            try {
                //url= new URL("https://studentlab.nye.hu/lekerdezes.php");
                url = new URL("https://studentlab.nye.hu/xml_lekerdezes.php");
                con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStreamWriter ki = new OutputStreamWriter(con.getOutputStream());
                ki.write("keresett=" + azonosito);
                ki.flush();
                ki.close();

                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("SAJAT", "Válasz kód rendben");
                }

                InputStream be = con.getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(be);
                NodeList nl = doc.getElementsByTagName("ember");
                int szam = nl.getLength();
                valasz.append("A találatok száma: " + szam);
                /*BufferedReader be= new BufferedReader(new InputStreamReader(con.getInputStream()));
                String sor=null;
                while((sor=be.readLine())!=null){
                    valasz.append(sor+"\n");
                }*/

                be.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
/*
            StringTokenizer st = new StringTokenizer(valasz.toString(),";#");

            StringBuffer sb = new StringBuffer();
            try {
                while (st.hasMoreTokens()) {
                    //Log.d("SAJAT", st.nextToken());
                    sb.append("Név: " + st.nextToken()+"\t");
                    sb.append("Születési év: " + st.nextToken()+"\n");
                }
            }catch (NoSuchElementException ex){

            }

            return sb.toString();

 */
            return valasz.toString();
        }

    }

    public class XMLLekeredezo extends AsyncTask<String, Void, String> {

        String eredeti = null;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            eredmeny.setText(eredeti + "\n" + s);
        }

        @Override
        protected String doInBackground(String... strings) {

            String azonosito = strings[0];
            HttpURLConnection con = null;
            URL url = null;
            StringBuffer valasz = new StringBuffer();

            try {
                //url= new URL("https://studentlab.nye.hu/lekerdezes.php");
                url = new URL("https://studentlab.nye.hu/xml_lekerdezes.php");
                con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStreamWriter ki = new OutputStreamWriter(con.getOutputStream());
                ki.write("keresett=" + azonosito);
                ki.flush();
                ki.close();

                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("SAJAT", "Válasz kód rendben");
                }

                InputStream be = con.getInputStream();

                XmlPullParser praser = Xml.newPullParser();
                praser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                praser.setInput(be, null);
                praser.nextTag();

                praser.require(XmlPullParser.START_TAG, null, "felhasznalok");
                while (praser.next() != XmlPullParser.END_TAG) {
                    if (praser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    praser.require(XmlPullParser.START_TAG, null, "ember");
                    String elemNev = praser.getName();
                    if (elemNev.equals("ember")) {
                        praser.nextTag();
                        praser.require(XmlPullParser.START_TAG, null, "nev");
                        if (praser.next() == XmlPullParser.TEXT) {
                            valasz.append("Név: " + praser.getText() + "\n");
                            praser.nextTag();
                            praser.require(XmlPullParser.END_TAG, null, "nev");
                        }

                        praser.nextTag();
                        praser.require(XmlPullParser.START_TAG, null, "szuletesiev");
                        if (praser.next() == XmlPullParser.TEXT) {
                            valasz.append("Születési év: " + praser.getText() + "\n");
                            praser.nextTag();
                            praser.require(XmlPullParser.END_TAG, null, "szuletesiev");
                        }
                    }
                    praser.nextTag();
                    praser.require(XmlPullParser.END_TAG, null, "ember");
                    valasz.append("----------------------------------\n");
                }
                praser.require(XmlPullParser.END_TAG, null, "felhasznalo");
                be.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
/*
            StringTokenizer st = new StringTokenizer(valasz.toString(),";#");

            StringBuffer sb = new StringBuffer();
            try {
                while (st.hasMoreTokens()) {
                    //Log.d("SAJAT", st.nextToken());
                    sb.append("Név: " + st.nextToken()+"\t");
                    sb.append("Születési év: " + st.nextToken()+"\n");
                }
            }catch (NoSuchElementException ex){

            }

            return sb.toString();

 */
            return valasz.toString();
        }

    }
}