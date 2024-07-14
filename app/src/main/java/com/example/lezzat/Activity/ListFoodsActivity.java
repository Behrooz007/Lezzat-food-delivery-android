package com.example.lezzat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.lezzat.Adapter.CategoryListAdapter;
import com.example.lezzat.Model.Category;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.R;
import com.example.lezzat.databinding.ActivityListFoodsBinding;
import com.example.lezzat.lezzatViewModel.MyViewModel;

import java.util.ArrayList;

public class ListFoodsActivity extends AppCompatActivity {

    private ActivityListFoodsBinding binding;
    private CategoryListAdapter adapter;
    private int categoryID;
    private String categoryName;
    private String searchText;
    private boolean isSearch;
    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(Color.parseColor("#BAB7B7"));

        viewModel= new  ViewModelProvider(ListFoodsActivity.this).get(MyViewModel.class);
        binding.backBtn.setOnClickListener(view -> finish());

        getIntentExtra();


    }

    private void getIntentExtra() {
        ArrayList<Foods> categoryList=new ArrayList<>();
        categoryID=getIntent().getIntExtra("CategoryId",0);
        //Toast.makeText(this, ""+categoryID, Toast.LENGTH_SHORT).show();
        categoryName=getIntent().getStringExtra("CategoryName");
        searchText=getIntent().getStringExtra("text");
        isSearch=getIntent().getBooleanExtra("isSearch",false);
       // Toast.makeText(this, ""+searchText, Toast.LENGTH_SHORT).show();
        binding.progressBarList.setVisibility(View.VISIBLE);
        binding.titleTxt.setText(categoryName);



        viewModel.getGetCategoryUsingNameId(searchText,isSearch,categoryID)
                .observe(ListFoodsActivity.this, new Observer<ArrayList<Foods>>() {
            @Override
            public void onChanged(ArrayList<Foods> categories) {
                categoryList.addAll(categories);

                adapter=new CategoryListAdapter(categoryList);

                binding.listFoodView.setLayoutManager(new GridLayoutManager(ListFoodsActivity.this
                ,2));
                binding.listFoodView.setAdapter(adapter);
                binding.progressBarList.setVisibility(View.INVISIBLE);


                //Log.v("Tag",categoryList.get(0).getName());

            }
        });

    }
}