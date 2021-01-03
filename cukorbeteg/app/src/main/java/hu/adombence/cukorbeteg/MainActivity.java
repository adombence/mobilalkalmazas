package hu.adombence.cukorbeteg;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        Button profile = findViewById(R.id.btnProfile);
        Button login = findViewById(R.id.btn_login);
        Button sign_up = findViewById(R.id.btn_sign_up);
        Button foodlal = findViewById(R.id.fooldal);
        Button adatok = findViewById(R.id.adatok);
        Button vercukor = findViewById(R.id.vercukor);
        Button receptek = findViewById(R.id.receptek);

        PrefManager prefManager = PrefManager.getInstance(MainActivity.this);

        if (prefManager.isLoggedIn()) {
            login.setVisibility(View.GONE);
            sign_up.setVisibility(View.GONE);
            profile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_profile.class)));
            foodlal.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, MainActivity1.class)));
            adatok.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, activity_phisicalDatas.class)));
            vercukor.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, activity_cukor.class)));
            receptek.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, hu.adombence.cukorbeteg.recept.activity_recept.class)));
        } else {
            profile.setVisibility(View.GONE);
            foodlal.setVisibility(View.GONE);
            adatok.setVisibility(View.GONE);
            vercukor.setVisibility(View.GONE);
            receptek.setVisibility(View.GONE);
            login.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_login.class)));
            sign_up.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_signup.class)));
        }
    }
}