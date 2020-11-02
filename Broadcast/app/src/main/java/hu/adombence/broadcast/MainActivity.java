package hu.adombence.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    AudioManager manager;
    ImageView kep;
    TextView allapot;
    TextView szam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kep = this.findViewById(R.id.imageView_kerdojel);
        allapot = this.findViewById(R.id.textView_allapot);
        szam = this.findViewById(R.id.textView_szamol);

        manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        AudioDeviceInfo[] eszkozok = manager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);

        for (AudioDeviceInfo aud : eszkozok) {
            if (aud.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES || aud.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET) {
                kep.setImageResource(R.drawable.headset);
                allapot.setText(R.string.bedugva);
            }
        }
    }


}