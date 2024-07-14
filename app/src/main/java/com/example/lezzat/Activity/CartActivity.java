package com.example.lezzat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lezzat.Adapter.CartAdapter;
import com.example.lezzat.Helper.ChangeNumberItemsListener;
import com.example.lezzat.Helper.ManagmentCart;
import com.example.lezzat.R;
import com.example.lezzat.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart=new ManagmentCart(CartActivity.this);

        setVariable();
        calculateCart();
        initList();
    }

    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.empyTxt.setVisibility(View.VISIBLE);
            binding.scrollerViewCart.setVisibility(View.INVISIBLE);
        }else{
            binding.empyTxt.setVisibility(View.INVISIBLE);
            binding.scrollerViewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CartActivity.this,
                LinearLayoutManager.VERTICAL,false);
        binding.cardView.setLayoutManager(linearLayoutManager);

        adapter=new CartAdapter(managmentCart.getListCart(),
                CartActivity.this, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        });

        binding.cardView.setAdapter(adapter);

    }

    private void calculateCart() {
        double percentTax=0.02;//2 percent tax
        double delivery =10; // delivery 10 dollar
        tax =Math.round(managmentCart.getTotalFee()*percentTax*100.0)/100;

        double total=Math.round((managmentCart.getTotalFee() +delivery+tax)*100)/100;
        double itemTotal=Math.round(managmentCart.getTotalFee() *100)/100;
        binding.totalFeeTxt.setText(itemTotal +"$");
        binding.taxTxt.setText(tax+"$");
        binding.deliveryTxt.setText(delivery+"$");
        binding.totalTxt.setText(total+"$");
    }

    private void setVariable() {
        binding.backBtnCartNew.setOnClickListener(view -> finish()
        );
    }
}