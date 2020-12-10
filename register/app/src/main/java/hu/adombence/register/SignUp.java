package hu.adombence.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private Button regisztracio;
    private EditText regName;
    private EditText regPass;

    public static Credentials credentials;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regisztracio = this.findViewById(R.id.reg);
        regName = this.findViewById(R.id.etRegEmail);
        regPass = this.findViewById(R.id.etRegPassword);

        regisztracio.setOnClickListener(view -> {
            String regUsername = regName.getText().toString();
            String regPassword = regPass.getText().toString();

            if(validate(regUsername, regPassword)){
                credentials = new Credentials(regUsername, regPassword);
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