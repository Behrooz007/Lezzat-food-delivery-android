package com.example.lezzat.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.lezzat.R;
import com.example.lezzat.databinding.ActivityLoginBinding;
import com.example.lezzat.lezzatViewModel.MyViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    MyViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
        viewModel=new ViewModelProvider(LoginActivity.this).get(MyViewModel.class);

    }

    private void setVariable() {
        binding.moveToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=binding.emailEditLogin
                                .getText().toString().trim();
                String password=binding.passwordEdit.getText()
                                .toString().trim();
                if(!TextUtils.isEmpty(email) && password.length()>=6){
                    viewModel.signInWithEmailPassword(email,password,LoginActivity.this);
                }else {
                    Toast.makeText(LoginActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}