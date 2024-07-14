package com.example.lezzat.lezzatViewModel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.lezzat.Model.Category;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.Model.Location;
import com.example.lezzat.Model.Price;
import com.example.lezzat.Model.Time;
import com.example.lezzat.Repository.MyRepository;

import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {

    MyRepository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository();
    }

    //Sign Up with email and password
    public void signUpWithEmailPass(String email, String password, Context context ){

            repository.signUpWithEmailPass(email, password, context);

    }

    //SingIn with email and password
    public void signInWithEmailPassword(String email,String password,Context context){

        if(!TextUtils.isEmpty(email) && password.length()>=6){
            repository.signInWithEmailPassword(email,password,context);
           // Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplication(), "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    //signOut
    public void singOut(Context context){
        repository.singOut(context);
    }

    //get All location from repo
    public MutableLiveData<ArrayList<Location>> getLiveDataLocation() {
        return repository.getLiveDataLocation();
    }

    //get All times list of times from repo
    public MutableLiveData<ArrayList<Time>> getLiveDataTime() {
        return repository.getLivaDataTime();
    }

    //get all data about prices from repo
    public MutableLiveData<ArrayList<Price>> getLiveDataPrice() {
        return repository.getLiveDataPrice();
    }

    //get all best foods from repo
    public MutableLiveData<ArrayList<Foods>> getLiveDataBestFood(){
       return repository.getLiveDataBestFood();
    }

    //get all the categories from repo
    public MutableLiveData<ArrayList<Category>> getLiveDataCategory(){
        return repository.getLiveDataCategory();

    }

    //get the category list Using search text or its id
    public MutableLiveData<ArrayList<Foods>> getGetCategoryUsingNameId(String searchTxt,boolean isSearch,int CategoryId){
       return repository.getGetCategoryUsingNameId(searchTxt,isSearch,CategoryId);
    }

}