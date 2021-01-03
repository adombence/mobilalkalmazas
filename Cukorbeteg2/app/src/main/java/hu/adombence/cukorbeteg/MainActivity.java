package hu.adombence.cukorbeteg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button profile, login, sign_up, fooldal, adatok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = this.findViewById(R.id.btnProfile);
        login = this.findViewById(R.id.btn_login);
        sign_up = this.findViewById(R.id.btn_sign_up);
        fooldal = this.findViewById(R.id.fooldal);
        adatok = this.findViewById(R.id.adatok);

        init();
    }

    private void init() {
        PrefManager prefManager = PrefManager.getInstance(MainActivity.this);

        if (prefManager.isLoggedIn()) {
            login.setVisibility(View.GONE);
            sign_up.setVisibility(View.GONE);
            profile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_profile.class)));
            //fooldal.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, MainActivity1.class)));
            //adatok.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, activity_phisicalDatas.class)));
        } else {
            profile.setVisibility(View.GONE);
            fooldal.setVisibility(View.GONE);
            adatok.setVisibility(View.GONE);
            login.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_login.class)));
            sign_up.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, activity_signup.class)));
        }
    }
}