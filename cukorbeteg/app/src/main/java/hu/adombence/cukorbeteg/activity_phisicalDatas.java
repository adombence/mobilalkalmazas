package hu.adombence.cukorbeteg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
    double bmi;
    double kivanatosAtlag;
    double min;
    double max;
    Boolean gNem;

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

            Toast.makeText(activity_phisicalDatas.this, getString(R.string.commited), Toast.LENGTH_SHORT).show();

            //executing the async task
            activity_phisicalDatas.userDatas ru = new userDatas(Integer.parseInt(userId), gNem, gKor, gTomeg);
            ru.execute();
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

    void init() {
        //getting the current user
        User user = PrefManager.getInstance(this).getUser();

        //setting the value to the variable
        userId = String.valueOf(user.getId());
    }

    class userDatas extends AsyncTask<Void, Void, String> {

        private int id;
        private boolean gender;
        private double age, weight;

        public userDatas(int id, boolean gender, int age, double weight) {
            this.id = id;
            this.gender = gender;
            this.age = age;
            this.weight = weight;
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

            Log.d("HASHTERKEP", String.valueOf(params));
            //returing the response
            return requestHandler.sendPostRequest(URLS.URL_POST_DATAS, params);
        }
    }
}