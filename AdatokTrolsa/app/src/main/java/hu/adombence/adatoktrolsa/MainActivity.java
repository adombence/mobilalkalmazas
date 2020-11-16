package hu.adombence.adatoktrolsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText et_nev;
    Button bt_tarol;
    TextView tv_tartalom;
    int darab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nev = this.findViewById(R.id.editTextTextNev);
        tv_tartalom = this.findViewById(R.id.textView_tartalom);
        bt_tarol = this.findViewById(R.id.button_tarol);

        SharedPreferences sp = getSharedPreferences("adatok", Context.MODE_PRIVATE);
        String beolvasotNev = sp.getString("nev", "nem volt még elmentve név");
        darab = sp.getInt("darab", 0);

        et_nev.setText(beolvasotNev + " / " + darab);

        bt_tarol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seged = et_nev.getText().toString();
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("nev", seged);
                darab++;
                ed.putInt("darab", darab);
                ed.commit();
            }
        });

        try {

            InputStream be = getResources().openRawResource(R.raw.eloregyartot);
            BufferedReader beolvaso = new BufferedReader(new InputStreamReader(be));
            String sor = "";
            StringBuilder sb = new StringBuilder();

            while ((sor = beolvaso.readLine()) != null) {
                sb.append(sor);
            }

            beolvaso.close();
            tv_tartalom.setText(sb.toString());


            File f = getFilesDir();
            Log.d("sajat", f.getAbsolutePath());


            FileOutputStream ki;
            ki = openFileOutput("kiirt.txt", Context.MODE_PRIVATE);
            ki.write("Ez egy kiirandó szöveg".getBytes());
            ki.write("Ez egy másik sor".getBytes());
            ki.flush();
            ki.close();

            FileInputStream bebe;
            bebe = openFileInput("kiirt.txt");
            beolvaso = new BufferedReader(new InputStreamReader(bebe));
            sor = "";
            sb = new StringBuilder();

            while ((sor = beolvaso.readLine()) != null) {
                sb.append(sor + "\n");
            }
            beolvaso.close();

            String eredeti = tv_tartalom.getText().toString();
            tv_tartalom.setText(eredeti + "\n" + sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}