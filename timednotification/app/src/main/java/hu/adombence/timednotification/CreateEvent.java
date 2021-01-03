package hu.adombence.timednotification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateEvent extends AppCompatActivity {
    Button btn_record, btn_time, btn_date, btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        btn_record = this.findViewById(R.id.btn_record);
        btn_time = this.findViewById(R.id.btn_time);
        btn_date = this.findViewById(R.id.btn_date);
        btn_done = this.findViewById(R.id.btn_done);

        btn_record.setOnClickListener(view -> {
            recordSpeech();
        });

        btn_time.setOnClickListener(view -> {

        });

        btn_date.setOnClickListener(view -> {

        });

        btn_done.setOnClickListener(view -> {

        });
    }

    private void recordSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        try {
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            Toast.makeText(this, "Your device does not support Speech recognizer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            }
        }
    }
}