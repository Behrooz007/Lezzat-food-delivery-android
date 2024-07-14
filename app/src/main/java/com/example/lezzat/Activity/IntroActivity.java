package com.example.lezzat.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.lezzat.R;
import com.example.lezzat.databinding.ActivityIntroBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroActivity extends BaseActivity{

    ActivityIntroBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent =new Intent(IntroActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));

        setVariable();

    }

    private void setVariable() {


        binding.logIN.setOnClickListener(view -> {
            Intent intent =new Intent(IntroActivity.this,LoginActivity.class);
            startActivity(intent);
        });

        binding.signIn.setOnClickListener(view -> {
            Intent intent =new Intent(IntroActivity.this,SignUpActivity.class);
            startActivity(intent);
        });


    }
}