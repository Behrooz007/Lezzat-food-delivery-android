package com.example.lezzat.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.lezzat.Helper.ManagmentCart;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.R;
import com.example.lezzat.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private Foods food;
    private int num=1;
    ActivityDetailBinding binding;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(Color.parseColor("#BAB7B7"));

        getIntentExtra();
        initVariable();

    }

    private void initVariable() {
        managmentCart=new ManagmentCart(DetailActivity.this);
        binding.backBtnDetail.setOnClickListener(view -> finish());
        Glide.with(DetailActivity.this)
                .load(food.getImagePath()).into(binding.picSearch);
        binding.titleSearch.setText(food.getTitle());
        binding.ratingBar.setRating((float) food.getStar());
        binding.rateTxtSearch.setText(food.getStar()+" rating");
        binding.timeTxtSearch.setText(food.getTimeValue()+" min");
        binding.totalPriceSearch.setText(num * food.getPrice()+"$");
        binding.descriptionTxtSearch.setText(food.getDescription());

        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num=num+1;
                binding.numTxt.setText(num+"");
                binding.totalPriceSearch.setText(num*food.getPrice()+"$");
            }
        });

        binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(num>1){
                    num=num-1;
                    binding.numTxt.setText(num+"");
                    binding.totalPriceSearch.setText(num* food.getPrice()+"$");
                }
            }
        });

        binding.AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food.setNumberInCart(num);
                managmentCart.insertFood(food);
            }
        });


    }

    private void getIntentExtra() {
        food=(Foods) getIntent().getSerializableExtra("food");
    }
}