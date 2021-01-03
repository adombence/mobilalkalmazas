package hu.adombence.receptapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Upload_Recipe extends AppCompatActivity {

    ImageView receptImage;
    Uri uri;
    EditText txt_name, txt_description, txt_price;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__recipe);

        receptImage = this.findViewById(R.id.iv_foodImage);
        txt_name = this.findViewById(R.id.txt_recipe_name);
        txt_description = this.findViewById(R.id.txt_recipe_description);
        txt_price = this.findViewById(R.id.txt_recipe_price);
    }

    public void btnSelectImage(View view) {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            uri = data.getData();
            receptImage.setImageURI(uri);
        } else {
            Toast.makeText(this, "Nem válaszottál képet", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadImage() {
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("RecipeImage").child(uri.getLastPathSegment());

        storageReference.putFile(uri).addOnSuccessListener(runnable -> {

            Task<Uri> uriTask = UploadTask.TaskSnapshot.getMetadata().getReference().getDownloadUrl().toString()
            while (!uriTask.isComplete()) ;
            Uri uriImage = uriTask.getResult();
            imageUrl = uriImage.toString();

            Toast.makeText(Upload_Recipe.this, "image uploaded", Toast.LENGTH_SHORT).show();



        });
    }


}