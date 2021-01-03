package hu.adombence.receptapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<FoodData> myFoodList;
    FoodData mFoodData;
    FloatingActionButton btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_upload = this.findViewById(R.id.btn_upload);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();



        /*myFoodList.add(mFoodData);
        myAdapter myAdapter = new myAdapter(MainActivity.this, myFoodList);
        mRecyclerView.setAdapter(myAdapter);*/

        btn_upload.setOnClickListener(view -> {
            btn_uploadActivity(view);
        });
    }

    private void btn_uploadActivity(View view) {
        startActivity(new Intent(this, Upload_Recipe.class));
    }
}