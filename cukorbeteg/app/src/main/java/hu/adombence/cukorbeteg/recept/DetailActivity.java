package hu.adombence.cukorbeteg.recept;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import hu.adombence.cukorbeteg.R;

public class DetailActivity extends AppCompatActivity {

    TextView foodName, foodIngredients, foodDescription, foodEnergy, foodCarbohydrate;
    ImageView foodImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        foodImage = this.findViewById(R.id.ivImage2);

        foodName = this.findViewById(R.id.tvName);
        foodDescription = this.findViewById(R.id.txtDescription);
        foodIngredients = this.findViewById(R.id.tvIngredients);
        foodEnergy = this.findViewById(R.id.tvEnergy);
        foodCarbohydrate = this.findViewById(R.id.tvCarbohydrate);


        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {

            foodName.setText(mBundle.getString("Name"));
            foodIngredients.setText("Hozzávalók: \n" + mBundle.getString("Ingredients"));
            foodDescription.setText(mBundle.getString("Description"));
            foodEnergy.setText("Energia: \n" + mBundle.getString("Energy") + " kcal");
            foodCarbohydrate.setText("Szénhidrát: \n" + mBundle.getString("Carbohydrate") + " g");
            foodImage.setImageResource(mBundle.getInt("Image"));

        }
    }
}