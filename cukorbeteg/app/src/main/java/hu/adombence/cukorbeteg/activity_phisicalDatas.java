package hu.adombence.cukorbeteg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_phisicalDatas extends AppCompatActivity {

    public static final String DATAS = "Datas";
    public static final String GENDER = "genderKey";
    public static final String AGE = "ageKey";
    public static final String WEIGHT = "weightKey";
    public static final String HEIGHT = "heightKey";
    public static final String BMI = "bmiKey";
    public static final String IDEAL = "idealKey";
    public static final String MIN = "minKey";
    public static final String MAX = "maxKey";


    RadioGroup nem;
    RadioButton ferfi;
    RadioButton no;
    EditText et_kor;
    EditText et_suly;
    EditText et_magassag;
    Button buttonKesz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phisical_datas);

        nem = this.findViewById(R.id.rg_nem);
        ferfi = this.findViewById(R.id.rb_ferfi);
        no = this.findViewById(R.id.rb_no);
        et_kor = this.findViewById(R.id.etKor);
        et_suly = this.findViewById(R.id.et_suly);
        et_magassag = this.findViewById(R.id.et_magassag);
        buttonKesz = this.findViewById(R.id.buttonKesz);

        SharedPreferences sharedPreferences = getSharedPreferences(DATAS, Context.MODE_PRIVATE);

        buttonKesz.setOnClickListener(view -> {
            Boolean gNem = isChecked();

            Toast toast;
            if (gNem) {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.NemNo), Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.NemFerfi), Toast.LENGTH_SHORT);
            }
            toast.show();

            float gTomeg = Float.parseFloat(et_suly.getText().toString());
            float gMagassag = Float.parseFloat(et_magassag.getText().toString());
            int gKor = Integer.parseInt(et_kor.getText().toString());

            double bmi = getBMI(gTomeg, gMagassag);
            double kivanatosAtlag = getkivanatosAtlag(gMagassag, gNem);
            double min = getMin(gMagassag, gNem);
            double max = getMax(gMagassag, gNem);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(GENDER, gNem);
            editor.putInt(AGE, gKor);
            editor.putString(WEIGHT, String.valueOf(gTomeg));
            editor.putString(HEIGHT, String.valueOf(gMagassag));
            editor.putString(BMI, String.valueOf(bmi));
            editor.putString(IDEAL, String.valueOf(kivanatosAtlag));
            editor.putString(MIN, String.valueOf(min));
            editor.putString(MAX, String.valueOf(max));

            editor.commit();

            Toast.makeText(activity_phisicalDatas.this, getString(R.string.commited), Toast.LENGTH_SHORT).show();

        });
    }

    Boolean isChecked() {
        boolean ret = false;
        if (ferfi.isChecked()) {
            ret = false;
        } else if (no.isChecked()) {
            ret = true;
        }
        return ret;
    }

    double getBMI(double weight, double height) {
        return weight / ((height / 100) * (height / 100));
    }

    Double getkivanatosAtlag(double height, boolean gender) {
        double ret;
        if (gender) {
            ret = 20.8 * ((height / 100) * (height / 100));
        } else {
            ret = 22 * ((height / 100) * (height / 100));
        }
        return ret;
    }

    Double getMin(double height, boolean gender) {
        double ret;
        if (gender) {
            ret = 18.7 * ((height / 100) * (height / 100));
        } else {
            ret = 20.1 * ((height / 100) * (height / 100));
        }
        return ret;
    }

    Double getMax(double height, boolean gender) {
        double ret;
        if (gender) {
            ret = 23.8 * ((height / 100) * (height / 100));
        } else {
            ret = 25 * ((height / 100) * (height / 100));
        }
        return ret;
    }
}