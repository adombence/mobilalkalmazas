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
        PrefManager prefManager = PrefManager.getInstance(MainActivity.this);
        if (prefManager.isLoggedIn()) {
            login.setVisibility(View.GONE);
            sign_up.setVisibility(View.GONE);
            profile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_profile.class)));
            foodlal.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, MainActivity1.class)));
        } else {
            profile.setVisibility(View.GONE);
            foodlal.setVisibility(View.GONE);
            login.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_login.class)));
            sign_up.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_signup.class)));
        }
    }
}