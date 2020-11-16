package hu.adombence.adatoktrolsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


        bt_tarol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seged = et_nev.getText().toString();
                SharedPreferences sp = getSharedPreferences("adatok", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("nev", seged);
                ed.putInt("darab", darab++);
                ed.commit();
            }
        });


    }
}