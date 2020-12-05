package hu.adombence.timernotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static hu.adombence.timernotification.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {
    TextView mTextField;
    Button startCount;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        mTextField = this.findViewById(R.id.mTextField);
        startCount = this.findViewById(R.id.start);

        startCount.setOnClickListener(view -> new CountDownTimer(30000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                mTextField.setText(getString(R.string.secLeft) + " " + l / 1000);
            }

            @Override
            public void onFinish() {
                mTextField.setText(getString(R.string.done));
                sendOnChannel1(view);
            }
        }.start());
    }

    public void sendOnChannel1(View view) {
        String title = getString(R.string.timer);
        String message = getString(R.string.finish);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_timer)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }
}