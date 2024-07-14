package com.example.lezzat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lezzat.Adapter.CategoryListAdapter;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.R;
import com.example.lezzat.databinding.ActivitySearchFoodsBinding;
import com.example.lezzat.lezzatViewModel.MyViewModel;

import java.util.ArrayList;

public class SearchFoodsActivity extends AppCompatActivity {
    ActivitySearchFoodsBinding binding;
    CategoryListAdapter adapter;
    MyViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.searchEdt.requestFocus();

        initVariable();
        initSearch();

    }

    private void initVariable() {
        viewModel=new ViewModelProvider(SearchFoodsActivity.this).get(MyViewModel.class);
        binding.backBtnSearch.setOnClickListener(view -> finish());
    }

    private void initSearch() {
        ArrayList<Foods> list=new ArrayList<>();
        binding.searchProgressBar.setVisibility(View.VISIBLE);

        binding.searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchTxt=binding.searchEdt.getText().toString().trim();
                list.clear();
                viewModel.getGetCategoryUsingNameId(searchTxt,true,-1)
                        .observe(SearchFoodsActivity.this, new Observer<ArrayList<Foods>>() {
                            @Override
                            public void onChanged(ArrayList<Foods> foods) {
                                list.addAll(foods);
                            }
                        });
                adapter=new CategoryListAdapter(list);

                //init recycler view
                binding.recyclerSearchFoods.setLayoutManager(new LinearLayoutManager(SearchFoodsActivity.this,
                        LinearLayoutManager.VERTICAL,false));
                binding.recyclerSearchFoods.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.searchProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
}