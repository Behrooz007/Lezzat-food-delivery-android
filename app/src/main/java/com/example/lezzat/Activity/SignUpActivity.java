package com.example.lezzat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import com.example.lezzat.databinding.ActivitySignUpBinding;
import com.example.lezzat.lezzatViewModel.MyViewModel;


public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel=new ViewModelProvider(SignUpActivity.this).get(MyViewModel.class);
        setVariable();


    }

    private void setVariable() {

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SignUpActivity.this, "hhhh", Toast.LENGTH_SHORT).show();
                String email=binding.emailEdit.getText().toString().trim();
                String password=binding.passwordEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && password.length() >=6){
                    viewModel.signUpWithEmailPass(email,password,SignUpActivity.this);
                }

            }
        });

        binding.moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    //validation
    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}