package hu.adombence.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button belepes;
    private Button regisztracio;

    boolean isValid = false;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        belepes = this.findViewById(R.id.belepes);
        regisztracio = this.findViewById(R.id.regisztracio);

        email = this.findViewById(R.id.editTextTextEmailAddress);
        password = this.findViewById(R.id.editTextTextPassword);

        belepes.setOnClickListener(view -> {
            String inputEmail = email.getText().toString();
            String inputPassword = password.getText().toString();

            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(MainActivity.this, "Kérem ne hagyjon üres mezőt", Toast.LENGTH_SHORT).show();
            } else {
                isValid = validate(inputEmail, inputPassword);

                if (!isValid) {
                    counter--;
                    Toast.makeText(MainActivity.this, "Kérem próbálja újra!", Toast.LENGTH_SHORT).show();
                    if (counter == 0) {
                        belepes.setEnabled(false);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Siker!", Toast.LENGTH_SHORT).show();

                    //Add the go to new activity
                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }
            }
        });

        regisztracio.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });
    }

    private boolean validate(String userName, String pass) {
        if(SignUp.credentials != null){
            return userName.equals(SignUp.credentials.getUserName()) && pass.equals(SignUp.credentials.getPassword());
        }else{
            return  false;
        }
    }

}