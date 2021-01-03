package hu.adombence.cukorbeteg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_profile extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewEmail, textViewGender;
    Button buttonFooldal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        buttonFooldal = this.findViewById(R.id.buttonFooldal);

        buttonFooldal.setOnClickListener(view -> startActivity(new Intent(activity_profile.this, MainActivity.class)));

        init();

    }

    void init() {
        textViewId = findViewById(R.id.textViewId);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        buttonFooldal = this.findViewById(R.id.fooldal);
        //getting the current user
        User user = PrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());

        //when the user presses logout button calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(view -> {
            finish();
            PrefManager.getInstance(getApplicationContext()).logout();
        });
    }
}