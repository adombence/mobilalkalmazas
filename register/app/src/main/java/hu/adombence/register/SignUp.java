package hu.adombence.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private EditText regName;
    private EditText regPass;

    public static Credentials credentials;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button regisztracio = this.findViewById(R.id.reg);
        regName = this.findViewById(R.id.etRegEmail);
        regPass = this.findViewById(R.id.etRegPassword);

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();


        regisztracio.setOnClickListener(view -> {
            String regUsername = regName.getText().toString();
            String regPassword = regPass.getText().toString();

            if (validate(regUsername, regPassword)) {
                credentials = new Credentials(regUsername, regPassword);

                /* Store the Credentials */
                sharedPreferencesEditor.putString("UserName", regUsername);
                sharedPreferencesEditor.putString("Password", regPassword);
                sharedPreferencesEditor.apply();

                startActivity(new Intent(SignUp.this, MainActivity.class));
                Toast.makeText(SignUp.this, "regisztráció sikeres!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validate(String name, String password) {
        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Kérem töltösön ki minden mezőt", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}