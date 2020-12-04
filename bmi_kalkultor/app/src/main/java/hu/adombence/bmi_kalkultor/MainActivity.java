package hu.adombence.bmi_kalkultor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText testtomeg;
    EditText testmagassag;
    Button szamol;
    TextView tv_eredmeny;
    TextView tv_kivanatos;
    TextView tv_szelsoertekek;
    RadioButton ferfi;
    RadioButton no;
    boolean nem;

    @SuppressLint({"DefaultLocale", "ShowToast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testtomeg = this.findViewById(R.id.testtomeg);
        testmagassag = this.findViewById(R.id.testmagassag);
        szamol = this.findViewById(R.id.button);
        tv_eredmeny = this.findViewById(R.id.tv_eredmeny);
        tv_kivanatos = this.findViewById(R.id.tv_kivanatos);
        tv_szelsoertekek = this.findViewById(R.id.tv_szelsoertekek);
        ferfi = this.findViewById(R.id.ferfi);
        no = this.findViewById(R.id.no);

        szamol.setOnClickListener(view -> {
            if (ferfi.isChecked()) {
                nem = false;
            } else if (no.isChecked()) {
                nem = true;
            }

            Toast toast;
            if (nem) {
                toast = Toast.makeText(getApplicationContext(), R.string.no, Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), R.string.ferfi, Toast.LENGTH_SHORT);
            }
            toast.show();

            double tomeg = Double.parseDouble(testtomeg.getText().toString());
            double magassag = Double.parseDouble(testmagassag.getText().toString());
            double eredmeny = tomeg / ((magassag / 100) * (magassag / 100));

            String vissza;
            if (eredmeny < 18.5) {
                vissza = String.format("%.1f", eredmeny) + getString(R.string.sovany);
            } else if (eredmeny >= 18.5 && eredmeny < 25) {
                vissza = String.format("%.1f", eredmeny) + getString(R.string.normal);
            } else if (eredmeny >= 25 && eredmeny < 30) {
                vissza = String.format("%.1f", eredmeny) + getString(R.string.tulsuly);
            } else {
                vissza = String.format("%.1f", eredmeny) + getString(R.string.elhizas);
            }

            double kivanatosAtlag, min, max;
            String kivanatos, szelsoErtekek;
            if (nem) {
                kivanatosAtlag = 20.8 * ((magassag / 100) * (magassag / 100));
                min = 18.7 * ((magassag / 100) * (magassag / 100));
                max = 23.8 * ((magassag / 100) * (magassag / 100));
            } else {
                kivanatosAtlag = 22 * ((magassag / 100) * (magassag / 100));
                min = 20.1 * ((magassag / 100) * (magassag / 100));
                max = 25 * ((magassag / 100) * (magassag / 100));
            }

            kivanatos = getString(R.string.kivanatos) + " " + String.format("%.1f", kivanatosAtlag) + getString(R.string.mertekegyseg_kg);
            szelsoErtekek = getString(R.string.szelsoertek) + " " + String.format("%.1f", min) + getString(R.string.mertekegyseg_kg) + "-" + String.format("%.1f", max) + getString(R.string.mertekegyseg_kg);

            tv_eredmeny.setText(vissza);
            tv_kivanatos.setText(kivanatos);
            tv_szelsoertekek.setText(szelsoErtekek);

        });
    }
}