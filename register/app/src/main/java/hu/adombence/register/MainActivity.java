package hu.adombence.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


     Button belepes;
     Button regisztracio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        belepes = this.findViewById(R.id.belepes);
        regisztracio = this.findViewById(R.id.regisztracio);
    }
}