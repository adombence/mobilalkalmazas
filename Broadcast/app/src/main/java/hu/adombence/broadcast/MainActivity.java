package hu.adombence.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

        IntentFilter szuro = new IntentFilter(Intent.ACTION_HEADSET_PLUG);

        SajatFulesReceiver rec = new SajatFulesReceiver();
        this.registerReceiver(rec, szuro);

    }

    public class SajatFulesReceiver extends BroadcastReceiver {

        public int szamoljuk = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            AudioDeviceInfo[] eszkozok = manager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);

            boolean bentvan = false;

            for (AudioDeviceInfo aud : eszkozok) {
                if (aud.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES || aud.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET) {
                    bentvan = true;
                }
            }
            if (bentvan) {
                allapot.setText(R.string.bedugva);
                kep.setImageResource(R.drawable.headset);
            } else {
                allapot.setText(R.string.nincsbedugva);
                kep.setImageResource(R.drawable.ic_baseline_help_24);
            }
            szamoljuk++;
            szam.setText(getString(R.string.tv_szamol) + " " + szamoljuk);

        }
    }


}