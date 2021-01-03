package hu.adombence.cukorbeteg.recept;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import hu.adombence.cukorbeteg.R;

public class activity_recept extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<FoodData> myFoodList;
    FoodData mFoodData;
    FloatingActionButton btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        //btn_upload = this.findViewById(R.id.btn_upload);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity_recept.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();

        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);
        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);
        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);
        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);
        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);
        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);
        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);
        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);
        mFoodData = new FoodData("Burgonyaleves", "200 g burgonya, ½ fej vöröshagyma, petrezselyemzöld, zellerzöld, 1 dl joghurt, só.", "118", R.drawable.tomato_soup);
        myFoodList.add(mFoodData);

        myAdapter myAdapter = new myAdapter(activity_recept.this,myFoodList);
        mRecyclerView.setAdapter(myAdapter);
        /*btn_upload.setOnClickListener(view -> {
            btn_uploadActivity(view);
        }*/
    }
}