package hu.adombence.bmi_kalkultor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText testtomeg;
    EditText testmagassag;
    Button szamol;
    TextView tv_eredmeny;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testtomeg = this.findViewById(R.id.testtomeg);
        testmagassag = this.findViewById(R.id.testmagassag);
        szamol = this.findViewById(R.id.button);
        tv_eredmeny = this.findViewById(R.id.tv_eredmeny);

        szamol.setOnClickListener(view -> {
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
            tv_eredmeny.setText(vissza);
        });
    }
}