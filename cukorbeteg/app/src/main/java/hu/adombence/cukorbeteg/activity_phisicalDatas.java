package hu.adombence.cukorbeteg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class activity_phisicalDatas extends AppCompatActivity {

    public static final String DATAS = "Datas";
    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String WEIGHT = "weight";
    public static final String HEIGHT = "height";
    public static final String BMI = "bmi";
    public static final String IDEAL = "ideal";
    public static final String MIN = "min";
    public static final String MAX = "max";


    RadioGroup nem;
    RadioButton ferfi;
    RadioButton no;
    EditText et_kor;
    EditText et_suly;
    EditText et_magassag;
    Button buttonKesz;

    String userId;
    float gTomeg;
    float gMagassag;
    int gKor;
    public double bmi;
    public double kivanatosAtlag;
    public double min;
    public double max;
    Boolean gNem;

    TextView tv_bmi_result, tv_bmi_result_txt, tv_kivanatos_atl, tv_szelsoertek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phisical_datas);
        init();
        nem = this.findViewById(R.id.rg_nem);
        ferfi = this.findViewById(R.id.rb_ferfi);
        no = this.findViewById(R.id.rb_no);
        et_kor = this.findViewById(R.id.etKor);
        et_suly = this.findViewById(R.id.et_suly);
        et_magassag = this.findViewById(R.id.et_magassag);
        buttonKesz = this.findViewById(R.id.buttonKesz);

        tv_bmi_result = this.findViewById(R.id.tv_bmi_result);
        tv_bmi_result_txt = this.findViewById(R.id.tv_bmi_result_txt);
        tv_kivanatos_atl = this.findViewById(R.id.tv_kivanatos_atl);
        tv_szelsoertek = this.findViewById(R.id.tv_szelsoertek);

        SharedPreferences sharedPreferences = getSharedPreferences(DATAS, Context.MODE_PRIVATE);

        buttonKesz.setOnClickListener(view -> {
            gNem = isChecked();

            Toast toast;
            if (gNem) {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.NemNo), Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.NemFerfi), Toast.LENGTH_SHORT);
            }
            toast.show();

            gTomeg = Float.parseFloat(et_suly.getText().toString());
            gMagassag = Float.parseFloat(et_magassag.getText().toString());
            gKor = Integer.parseInt(et_kor.getText().toString());

            bmi = getBMI(gTomeg, gMagassag);
            kivanatosAtlag = getkivanatosAtlag(gMagassag, gNem);
            min = getMin(gMagassag, gNem);
            max = getMax(gMagassag, gNem);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(GENDER, gNem);
            editor.putInt(AGE, gKor);
            editor.putString(WEIGHT, String.valueOf(gTomeg));
            editor.putString(HEIGHT, String.valueOf(gMagassag));
            editor.putString(BMI, String.valueOf(bmi));
            editor.putString(IDEAL, String.valueOf(kivanatosAtlag));
            editor.putString(MIN, String.valueOf(min));
            editor.putString(MAX, String.valueOf(max));

            editor.apply();

            tv_bmi_result.setText(String.valueOf(result(bmi)));
            resultColor(bmi);
            tv_bmi_result_txt.setText(resultText(bmi));
            tv_kivanatos_atl.setText(String.format("%.1f", kivanatosAtlag));
            tv_szelsoertek.setText(String.format("%.1f", min) + " kg - " + String.format("%.1f", max) + " kg");

            Toast.makeText(activity_phisicalDatas.this, getString(R.string.commited), Toast.LENGTH_SHORT).show();

            //executing the async task
            activity_phisicalDatas.userDatas ud = new userDatas(Integer.parseInt(userId), gNem, gKor, gTomeg, gMagassag);
            ud.execute();
        });
    }

    @SuppressLint("ResourceAsColor")
    private void resultColor(double bmi) {
        if (bmi < 18.5) {
            tv_bmi_result.setTextColor(Color.parseColor("#FF4CAF50"));
        } else if (bmi >= 18.5 && bmi < 25) {
            tv_bmi_result.setTextColor(Color.parseColor("#ff9800"));
        } else if (bmi >= 25 && bmi < 30) {
            tv_bmi_result.setTextColor(Color.parseColor("#ff5722"));
        } else {
            tv_bmi_result.setTextColor(Color.parseColor("#C50E29"));
        }
    }

    private String result(double bmi) {
        String vissza;
        if (bmi < 18.5) {
            vissza = String.format("%.1f", bmi);
        } else if (bmi >= 18.5 && bmi < 25) {
            vissza = String.format("%.1f", bmi);
        } else if (bmi >= 25 && bmi < 30) {
            vissza = String.format("%.1f", bmi);
        } else {
            vissza = String.format("%.1f", bmi);
        }
        return vissza;
    }

    private String resultText(double bmi) {
        String vissza;
        if (bmi < 18.5) {
            vissza = getString(R.string.sovany);
        } else if (bmi >= 18.5 && bmi < 25) {
            vissza = getString(R.string.normal);
        } else if (bmi >= 25 && bmi < 30) {
            vissza = getString(R.string.tulsuly);
        } else {
            vissza = getString(R.string.elhizas);
        }
        return vissza;
    }

    Boolean isChecked() {
        boolean ret = false;
        if (no.isChecked()) {
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

    void init() {
        //getting the current user
        User user = PrefManager.getInstance(this).getUser();

        //setting the value to the variable
        userId = String.valueOf(user.getId());
    }

    static class userDatas extends AsyncTask<Void, Void, String> {

        private final int id;
        private final boolean gender;
        private final double age;
        private final double weight;
        private final double height;

        public userDatas(int id, boolean gender, int age, double weight, double height) {
            this.id = id;
            this.gender = gender;
            this.age = age;
            this.weight = weight;
            this.height = height;
        }

        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            int i = gender ? 1 : 0;
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            Log.d("DEBUG", String.valueOf(id));
            Log.d("DEBUG", String.valueOf(i));
            Log.d("DEBUG", String.valueOf(age));
            Log.d("DEBUG", String.valueOf(weight));

            params.put("action", "POST");
            params.put("id", String.valueOf(id));
            params.put(GENDER, String.valueOf(i));
            params.put(AGE, String.valueOf(age));
            params.put(WEIGHT, String.valueOf(weight));
            params.put(HEIGHT, String.valueOf(height));

            Log.d("HASHTERKEP", String.valueOf(params));
            //returing the response
            return requestHandler.sendPostRequest(URLS.URL_POST_DATAS, params);
        }
    }
}