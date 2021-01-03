package hu.adombence.alarmteszt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import hu.adombence.alarmteszt.broadcast.MyBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;
    TextView time;
    Button setalarm;
    int mHour, mMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = this.findViewById(R.id.timepicker);
        time = this.findViewById(R.id.tv_time);
        setalarm = this.findViewById(R.id.button_setAlarm);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                mHour = i;
                mMin = i1;
                time.setText(time.getText().toString() + " " + mHour + ":" + mMin);
            }
        });

        setalarm.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "tick-tack",Toast.LENGTH_LONG);
        });
    }

    public void setTimer(View v) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();

        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY, mHour);
        cal_alarm.set(Calendar.MINUTE, mMin);
        cal_alarm.set(Calendar.SECOND, 0);

        if (cal_alarm.before(cal_now)) {
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(MainActivity.this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 24444, i, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),pendingIntent);

    }
}