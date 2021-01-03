package hu.adombence.cukorbeteg.recept;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import hu.adombence.cukorbeteg.R;

public class DetailActivity extends AppCompatActivity {

    TextView foodDescription;
    ImageView foodImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        foodDescription = this.findViewById(R.id.txtDescription);
        foodImage = this.findViewById(R.id.ivImage2);

        Bundle mBundle = getIntent().getExtras();

        if(mBundle != null){

            foodDescription.setText(mBundle.getString("Description"));
            foodImage.setImageResource(mBundle.getInt("Image"));

        }
    }
}