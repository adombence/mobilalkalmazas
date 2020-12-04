package hu.adombence.bmi_kalkultor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText testtomeg;
    EditText testmagassag;
    Button szamol;
    TextView tv_eredmeny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testtomeg = this.findViewById(R.id.testtomeg);
        testmagassag = this.findViewById(R.id.testmagassag);
        szamol = this.findViewById(R.id.button);
        tv_eredmeny = this.findViewById(R.id.tv_eredmeny);

        szamol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double tomeg = Double.parseDouble(testtomeg.getText().toString());
                double magassag = Double.parseDouble(testmagassag.getText().toString());
                double eredmeny = tomeg / ((magassag / 100) * (magassag / 100));

                String vissza = null;
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
            }
        });
    }
}