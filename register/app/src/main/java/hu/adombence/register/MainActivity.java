package hu.adombence.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button belepes;
    private CheckBox checkBox;

    boolean isValid = false;
    private int counter = 5;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        belepes = this.findViewById(R.id.belepes);
        Button registration = this.findViewById(R.id.regisztracio);

        email = this.findViewById(R.id.editTextTextEmailAddress);
        password = this.findViewById(R.id.editTextTextPassword);

        checkBox = this.findViewById(R.id.checkBox);

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if (sharedPreferences != null) {
            String savedUsername = sharedPreferences.getString("UserName", "");
            String savedPassword = sharedPreferences.getString("Password", "");


            SignUp.credentials = new Credentials(savedUsername, savedPassword);

            if (sharedPreferences.getBoolean("RememberMeCheckbox", false)) {
                email.setText(savedUsername);
                password.setText(savedPassword);
                checkBox.setChecked(true);
            }

        }


        checkBox.setOnClickListener(view -> {
            sharedPreferencesEditor.putBoolean("RememberMeCheckbox", checkBox.isChecked());
            sharedPreferencesEditor.apply();
        });


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


        registration.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });
    }

    private boolean validate(String userName, String pass) {
        if (SignUp.credentials != null) {
            return userName.equals(SignUp.credentials.getUserName()) && pass.equals(SignUp.credentials.getPassword());
        } else {
            return false;
        }
    }

    private HashMap<String, byte[]> encryptBytes(byte[] plainTextBytes, String passwordString) {
        HashMap<String, byte[]> map = new HashMap<>();

        try {
            //Random salt for next step
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[256];
            random.nextBytes(salt);

            //PBKDF2 - derive the key from the password, don't use passwords directly
            char[] passwordChar = passwordString.toCharArray(); //Turn password into char[] array
            PBEKeySpec pbKeySpec = new PBEKeySpec(passwordChar, salt, 1324, 256); //1324 iterations
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] keyBytes = secretKeyFactory.generateSecret(pbKeySpec).getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            //Create initialization vector for AES
            SecureRandom ivRandom = new SecureRandom(); //not caching previous seeded instance of SecureRandom
            byte[] iv = new byte[16];
            ivRandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            //Encrypt
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plainTextBytes);

            map.put("salt", salt);
            map.put("iv", iv);
            map.put("encrypted", encrypted);
        } catch (Exception e) {
            Log.e("MYAPP", "encryption exception", e);
        }

        return map;
    }

    private byte[] decryptData(HashMap<String, byte[]> map, String passwordString) {
        byte[] decrypted = null;
        try {
            byte salt[] = map.get("salt");
            byte iv[] = map.get("iv");
            byte encrypted[] = map.get("encrypted");

            //regenerate key from password
            char[] passwordChar = passwordString.toCharArray();
            PBEKeySpec pbKeySpec = new PBEKeySpec(passwordChar, salt, 1324, 256);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] keyBytes = secretKeyFactory.generateSecret(pbKeySpec).getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            //Decrypt
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            decrypted = cipher.doFinal(encrypted);
        } catch (Exception e) {
            Log.e("MYAPP", "decryption exception", e);
        }

        return decrypted;
    }

}