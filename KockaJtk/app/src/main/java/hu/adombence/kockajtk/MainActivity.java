package hu.adombence.kockajtk;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int penz = 100;
    int tet = 1;

    TextView osszespenz;
    TextView maxpenz;
    TextView tetertek;
    SeekBar tetbar;
    ImageView kep;
    RadioButton gomb1;
    RadioButton gomb2;
    RadioButton gomb3;
    RadioButton gomb4;
    RadioButton gomb5;
    RadioButton gomb6;
    RadioGroup gombCsoport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        osszespenz = this.findViewById(R.id.textView_osszpenz);
        maxpenz = this.findViewById(R.id.textView_max);
        tetertek = this.findViewById(R.id.textView_tet);

        osszespenz.setText(getString(R.string.osszespenzed) + " " + penz);
        tetertek.setText(getString(R.string.tet) + " " + 1);


        tetbar = this.findViewById(R.id.seekBar);
        tetbar.setMax(penz);
        tetbar.setMin(1);

        kep = this.findViewById(R.id.kocka);

        gombCsoport = this.findViewById(R.id.radioGroup);

        gomb1 = this.findViewById(R.id.egy);
        gomb2 = this.findViewById(R.id.ketto);
        gomb3 = this.findViewById(R.id.harom);
        gomb4 = this.findViewById(R.id.negy);
        gomb5 = this.findViewById(R.id.ot);
        gomb6 = this.findViewById(R.id.hat);

        gomb1.setSelected(true);

        tetbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tet = i;
                tetertek.setText(getString(R.string.tet) + " " + tet);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        kep.setOnClickListener(view -> {
            int tipp = 1;
            int veletlen = 1;


            int seged = gombCsoport.getAutofillType();

            if (seged == gomb1.getId()) {
                tipp = 1;
            } else if (seged == gomb2.getId()) {
                tipp = 2;
            } else if (seged == gomb3.getId()) {
                tipp = 3;
            } else if (seged == gomb4.getId()) {
                tipp = 4;
            } else if (seged == gomb5.getId()) {
                tipp = 5;
            } else if (seged == gomb6.getId()) {
                tipp = 6;
            }


            AnimationDrawable veletlenanim = new AnimationDrawable();
            veletlenanim.setOneShot(true);
            for (int i = 1; i < 11; i++) {
                int vsz = (int) (Math.random() * 6) + 1;
                switch (vsz) {
                    case 1:
                        veletlenanim.addFrame(getResources().getDrawable(R.drawable.kocka1), 5000);
                        break;
                    case 2:
                        veletlenanim.addFrame(getResources().getDrawable(R.drawable.kocka2), 5000);
                        break;
                    case 3:
                        veletlenanim.addFrame(getResources().getDrawable(R.drawable.kocka3), 5000);
                        break;
                    case 4:
                        veletlenanim.addFrame(getResources().getDrawable(R.drawable.kocka4), 5000);
                        break;
                    case 5:
                        veletlenanim.addFrame(getResources().getDrawable(R.drawable.kocka5), 5000);
                        break;
                    case 6:
                        veletlenanim.addFrame(getResources().getDrawable(R.drawable.kocka6), 5000);
                        break;
                }
            }

            kep.setImageResource(veletlenanim);
            veletlenanim.setVisible(true, true);
            veletlenanim.start();

            switch (veletlen) {
                case 1:
                    kep.setImageResource(R.drawable.kocka1);
                    break;
                case 2:
                    kep.setImageResource(R.drawable.kocka2);
                    break;
                case 3:
                    kep.setImageResource(R.drawable.kocka3);
                    break;
                case 4:
                    kep.setImageResource(R.drawable.kocka4);
                    break;
                case 5:
                    kep.setImageResource(R.drawable.kocka5);
                    break;
                case 6:
                    kep.setImageResource(R.drawable.kocka6);
                    break;

            }

            if (tipp == veletlen) {
                penz += (tet * 2);
                osszespenz.setText(R.string.osszespenzed + " " + penz);
            } else {
                penz -= tet;
                osszespenz.setText(R.string.osszespenzed + " " + penz);
            }

            if (penz == 0) {
                AlertDialog.Builder figyelmeztetes = new AlertDialog.Builder(MainActivity.this);
                figyelmeztetes.setTitle(getString(R.string.vesztett));
                figyelmeztetes.setMessage(getString(R.string.vesztettUzenet));
                figyelmeztetes.setPositiveButton(getString(R.string.ujra), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        penz = 100;
                        tet = 1;
                        tetbar.setMax(penz);
                        osszespenz.setText(getString(R.string.osszespenzed) + " " + penz);
                    }
                });
                figyelmeztetes.setNegativeButton(getString(R.string.kilepes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(-1);
                    }

                });
                figyelmeztetes.show();
            }

            osszespenz.setText(getString(R.string.osszespenzed) + " " + penz);
            tetbar.setMax(penz);
            maxpenz.setText(" " + penz);

        });


    }
}