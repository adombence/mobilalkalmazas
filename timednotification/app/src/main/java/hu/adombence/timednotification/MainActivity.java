package hu.adombence.timednotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button createEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createEvent = this.findViewById(R.id.btn_create_event);

        createEvent.setOnClickListener(view -> {
            goToCreateEventActivity();
        });
    }


    private void goToCreateEventActivity(){
        Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
        startActivity(intent);
    }
}