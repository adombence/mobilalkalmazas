package hu.adombence.repeatingnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import static hu.adombence.repeatingnotification.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private EditText editTextTilte;
    private EditText editTextMessage;
    TimePicker timePicker;
    Button setAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTilte = this.findViewById(R.id.edit_text_title);
        editTextMessage = this.findViewById(R.id.edit_text_title);

        timePicker = this.findViewById(R.id.timepicker);

        setAlarm = this.findViewById(R.id.setalarm);

        setAlarm.setOnClickListener(view -> {
            submit();
        });
    }

    private void submit() {
        String text = editTextMessage.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Please Enter or record the text", Toast.LENGTH_SHORT).show();
        } else {
            if (btn_time.getText().toString().equals("Select Time") || btn_date.getText().toString().equals("Select date")) {
                Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show();
            } else {
                EntityClass entityClass = new EntityClass();
                String value = (editext_message.getText().toString().trim());
                String date = (btn_date.getText().toString().trim());
                String time = (btn_time.getText().toString().trim());
                entityClass.setEventdate(date);
                entityClass.setEventname(value);
                entityClass.setEventtime(time);
                databaseClass.EventDao().insertAll(entityClass);
                setAlarm(value, date, time);
            }
        }
    }

    public void sendOnChannel1(View view) {
        String title = editTextTilte.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }
}