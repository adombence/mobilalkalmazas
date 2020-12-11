package hu.adombence.cukorbeteg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity1 extends AppCompatActivity {

    LinearLayout etkezesek;
    Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        buttons = new Button[5];
        etkezesek = findViewById(R.id.etkezesek);

        /*for (int i = 0; i < 5; i++) {
            buttons[i] = new Button(this);
            buttons[i].setText("Button" + i);
            etkezesek.addView(buttons[i]);

        }*/

    }
}