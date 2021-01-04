package hu.adombence.cukorbeteg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class activity_post_vercukor extends AppCompatActivity {

    public static final String ID = "id";
    public static final String VALUE = "value";
    public static final String ETKEZESELOTT = "etkezeselott";
    public static final String ENERGY = "energy";
    public static final String CARBOHYDRATE = "$carbonhydrate";

    RadioGroup etkezesElott;
    RadioButton igen;
    RadioButton nem;
    EditText et_ertek;
    EditText et_kaloria;
    EditText et_szenhidrat;
    Button buttonKesz;

    boolean gEtkezes;
    double gValue;
    double gEnergy;
    double gCarbohydrate;
    public String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_vercukor);
        init();
        etkezesElott = this.findViewById(R.id.rg_etkezesek);
        igen = this.findViewById(R.id.evesElott);
        nem = this.findViewById(R.id.evesUtan);
        et_ertek = this.findViewById(R.id.etErtek);
        et_kaloria = this.findViewById(R.id.et_kaloria);
        et_szenhidrat = this.findViewById(R.id.et_szenhidrat);
        buttonKesz = this.findViewById(R.id.buttonKesz);

        buttonKesz.setOnClickListener(view -> {
            gEtkezes = isChecked();

            Toast toast;
            if (gEtkezes) {
                toast = Toast.makeText(getApplicationContext(), "igen", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "nem", Toast.LENGTH_SHORT);
            }
            toast.show();

            gValue = Double.parseDouble(et_ertek.getText().toString());
            gEnergy = Double.parseDouble(et_kaloria.getText().toString());
            gCarbohydrate = Double.parseDouble(et_szenhidrat.getText().toString());


            //executing the async task
            activity_post_vercukor.userDatas ud = new activity_post_vercukor.userDatas(Integer.parseInt(userId), gEtkezes, gValue, gEnergy, gCarbohydrate);
            ud.execute();
        });
    }

    void init() {
        //getting the current user
        User user = PrefManager.getInstance(this).getUser();

        //setting the value to the variable
        userId = String.valueOf(user.getId());
    }

    Boolean isChecked() {
        boolean ret = false;
        if (igen.isChecked()) {
            ret = true;
        }
        return ret;
    }


    static class userDatas extends AsyncTask<Void, Void, String> {

        private final int id;
        private final boolean etkezesElott;
        private final double value;
        private final double energy;
        private final double carbohydrate;

        userDatas(int id, boolean etkezesElott, double value, double energy, double carbohydrate) {
            this.id = id;
            this.etkezesElott = etkezesElott;
            this.value = value;
            this.energy = energy;
            this.carbohydrate = carbohydrate;
        }


        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();

            int i = etkezesElott ? 1 : 0;
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            Log.d("DEBUG", String.valueOf(id));
            Log.d("DEBUG", String.valueOf(i));
            Log.d("DEBUG", String.valueOf(value));
            Log.d("DEBUG", String.valueOf(energy));
            Log.d("DEBUG", String.valueOf(carbohydrate));

            params.put("action", "POST");
            params.put(ID, String.valueOf(id));
            params.put(ETKEZESELOTT, String.valueOf(i));
            params.put(VALUE, String.valueOf(value));
            params.put(ENERGY, String.valueOf(energy));
            params.put(CARBOHYDRATE, String.valueOf(carbohydrate));

            Log.d("HASHTERKEP", String.valueOf(params));
            //returing the response
            return requestHandler.sendPostRequest(URLS.URL_POST_SUGAR, params);
        }
    }
}