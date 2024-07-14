package com.example.lezzat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lezzat.Adapter.BestFoodsAdapter;
import com.example.lezzat.Adapter.CategoryAdapter;
import com.example.lezzat.Model.Category;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.Model.Location;
import com.example.lezzat.Model.Price;
import com.example.lezzat.Model.Time;
import com.example.lezzat.R;
import com.example.lezzat.databinding.ActivityMainBinding;
import com.example.lezzat.lezzatViewModel.MyViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyViewModel viewModel;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(Color.parseColor("#BAB7B7"));
        viewModel=new ViewModelProvider(MainActivity.this).get(MyViewModel.class);

        initVariable();
        initLocation();
        initTime();
        initPrice();
        initBestFood();
        initCategory();

    }

    private void initVariable() {
        binding.logoutBtn.setOnClickListener(view -> viewModel.singOut(MainActivity.this));


        binding.searchLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, SearchFoodsActivity.class);
                startActivity(intent);
            }
        });

        binding.cartMainBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =new Intent(MainActivity.this, CartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

        /*
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent =new Intent(MainActivity.this, SearchFoodsActivity.class);
                //intent.putExtra("isSearch",true);
                String searchTxt=binding.searchEdt.getText().toString().trim();
                Intent intent=new Intent(MainActivity.this, ListFoodsActivity.class);
                intent.putExtra("isSearch",true);
                intent.putExtra("text",searchTxt);
                startActivity(intent);
            }
        });

         */
        /*
        binding.searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchTxt=binding.searchEdt.getText().toString().trim();
                Intent intent=new Intent(MainActivity.this, ListFoodsActivity.class);
                intent.putExtra("isSearch",true);
                intent.putExtra("text",searchTxt);
                startActivity(intent);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

         */

    }

    private void initCategory() {
        ArrayList<Category> categoryList=new ArrayList<>();
        binding.progressCategory.setVisibility(View.VISIBLE);

        viewModel.getLiveDataCategory().observe(MainActivity.this, new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                categoryList.addAll(categories);
                //Toast.makeText(MainActivity.this, ""+categoryList.get(1).getId(), Toast.LENGTH_SHORT).show();
                //Log.v("Tag",categoryList.get(1).getName());

               // Toast.makeText(MainActivity.this, ""+categories.get(0).getId(), Toast.LENGTH_SHORT).show();
                CategoryAdapter adapter=new CategoryAdapter(categoryList);

                binding.categoryView.setLayoutManager(new GridLayoutManager
                        (MainActivity.this,4));


                if(categories.size()>0){
                    binding.categoryView.setAdapter(adapter);
                }

                binding.progressCategory.setVisibility(View.GONE);
            }
        });

    }

    private void initBestFood() {
        ArrayList<Foods> bestFoodList=new ArrayList<>();
        binding.progressBarBestFood.setVisibility(View.VISIBLE);

        viewModel.getLiveDataBestFood().observe(MainActivity.this, new Observer<ArrayList<Foods>>() {
            @Override
            public void onChanged(ArrayList<Foods> foods) {
                bestFoodList.addAll(foods);
                BestFoodsAdapter adapter=new BestFoodsAdapter(bestFoodList);
                if(bestFoodList.size()>0){
                    binding.BestFoodView.setLayoutManager(new LinearLayoutManager(
                            MainActivity.this, LinearLayoutManager.HORIZONTAL,false));
                    binding.BestFoodView.setAdapter(adapter);
                    binding.BestFoodView.smoothScrollBy(10,0);
                 //   ));
                }
                binding.progressBarBestFood.setVisibility(View.INVISIBLE);
            }
        });

    }

    //initializing  the price spinner
    private void initPrice() {
        ArrayList<Price> priceList =new ArrayList<>();

        viewModel.getLiveDataPrice().observe(MainActivity.this
                , new Observer<ArrayList<Price>>() {
                    @Override
                    public void onChanged(ArrayList<Price> prices) {
                        priceList.clear();
                        for(Price p:prices){
                           priceList.add(p);
                        }

                        ArrayAdapter<Price> priceAdapter=new ArrayAdapter<>(
                                MainActivity.this,R.layout.sp_items,priceList
                        );

                        priceAdapter.setDropDownViewResource(android.R.layout.
                                simple_spinner_dropdown_item);
                        binding.dollarSp.setAdapter(priceAdapter);
                    }
                });
    }
    //initializing the Time spinner
    private void initTime() {
        ArrayList<Time> timeList=new ArrayList<>();

        viewModel.getLiveDataTime().observe(MainActivity.this, new Observer<ArrayList<Time>>() {
            @Override
            public void onChanged(ArrayList<Time> times) {
               // Toast.makeText(MainActivity.this, ""+times.get(0).getValue(), Toast.LENGTH_SHORT).show();
                timeList.clear();
                for(Time t:times){
                    timeList.add(t);
                }

                ArrayAdapter<Time> timeAdapter=new ArrayAdapter<>(
                        MainActivity.this,R.layout.sp_items,timeList
                );
                timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                timeAdapter.notifyDataSetChanged();
                binding.timeSP.setAdapter(timeAdapter);

            }
        });
    }

   // initializing the Location spinner
    private void initLocation() {
        List<Location> locationList=new ArrayList<>();
        viewModel.getLiveDataLocation().observe(MainActivity.this, new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                locationList.clear();
                for (Location l:locations){
                    locationList.add(l);
                }
                ArrayAdapter<Location> locationAdapter=new ArrayAdapter<>(MainActivity.this,R.layout.sp_items
                        ,locationList);
                locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                locationAdapter.notifyDataSetChanged();
                binding.locationSp.setAdapter(locationAdapter);
            }
        });
    }
}