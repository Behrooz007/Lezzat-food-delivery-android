package com.example.lezzat.Repository;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lezzat.Activity.IntroActivity;
import com.example.lezzat.Activity.MainActivity;
import com.example.lezzat.Model.Category;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.Model.Location;
import com.example.lezzat.Model.Price;
import com.example.lezzat.Model.Time;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyRepository {

    private FirebaseAuth mAuth;
    private Context context;
    private FirebaseDatabase database;

    private MutableLiveData<ArrayList<Location>> liveDataLocation;
    private  MutableLiveData<ArrayList<Time>> livaDataTime;
    private MutableLiveData<ArrayList<Price>> liveDataPrice;
    private MutableLiveData<ArrayList<Foods>> liveDataBestFood;
    private MutableLiveData<ArrayList<Category>> liveDataCategory;
    private MutableLiveData<ArrayList<Foods>> getCategoryUsingNameId;



    public MyRepository() {

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        liveDataLocation=new MutableLiveData<>();
        livaDataTime=new MutableLiveData<>();
        liveDataPrice=new MutableLiveData<>();
        liveDataBestFood=new MutableLiveData<>();
        liveDataCategory=new MutableLiveData<>();
        getCategoryUsingNameId=new MutableLiveData<>();
    }

    //signUp with email and password
    public void  signUpWithEmailPass(String email,String password,Context context){

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(context, "successfully created", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(context,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    //signIn
    public void signInWithEmailPassword(String email,String password,Context context){

        mAuth.signInWithEmailAndPassword(email,password).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Sign out

    public void singOut(Context context){
        mAuth.signOut();

        Intent intent =new Intent(context, IntroActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }




    //get Locations From Firebase
    public MutableLiveData<ArrayList<Location>> getLiveDataLocation() {
        DatabaseReference ref=database.getReference("Location");
        ArrayList<Location> locationList=new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){
                       locationList.clear();
                    for (DataSnapshot s: snapshot.getChildren()){
                        Location currentLocation=s.getValue(Location.class);
                        locationList.add(currentLocation);
                    }

                }
                liveDataLocation.postValue(locationList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return liveDataLocation;
    }

    //get time from Firebase

    public MutableLiveData<ArrayList<Time>> getLivaDataTime() {

        DatabaseReference ref=database.getReference("Time");
        ArrayList<Time> timeList=new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              timeList.clear();
              for(DataSnapshot times:snapshot.getChildren()){
                  Time currentTime=times.getValue(Time.class);
                  timeList.add(currentTime);

              }
              livaDataTime.postValue(timeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return livaDataTime;
    }

    //get the list of price from database


    public MutableLiveData<ArrayList<Price>> getLiveDataPrice() {
        ArrayList<Price> pricesList=new ArrayList<>();
        DatabaseReference ref=database.getReference("Price");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                pricesList.clear();
                for(DataSnapshot p:snapshot.getChildren()){
                    Price currentPrice=p.getValue(Price.class);
                    pricesList.add(currentPrice);
                }
                liveDataPrice.postValue(pricesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return liveDataPrice;
    }


    //get best foods from firebase

    public MutableLiveData<ArrayList<Foods>> getLiveDataBestFood() {

        ArrayList<Foods> bestFoodList=new ArrayList<>();
        DatabaseReference ref=database.getReference("Foods");
        Query bestFood=ref.orderByChild("BestFood").equalTo(true);

        bestFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    bestFoodList.clear();
                    for (DataSnapshot bs:snapshot.getChildren()){
                        Foods currentFood=bs.getValue(Foods.class);
                        bestFoodList.add(currentFood);
                    }
                    liveDataBestFood.postValue(bestFoodList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return liveDataBestFood;
    }

    //get list of Category from firebase
    public MutableLiveData<ArrayList<Category>> getLiveDataCategory() {

        //category list
        ArrayList<Category> categoryList=new ArrayList<>();
        //Firebase database reference
        DatabaseReference ref=database.getReference().child("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    categoryList.clear();
                   for(DataSnapshot cat:snapshot.getChildren()){
                    Category currentCategory=cat.getValue(Category.class);
                    categoryList.add(currentCategory);
                   }
                   // Log.v("Tag",categoryList.get(1).getName());
                }
                liveDataCategory.postValue(categoryList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return liveDataCategory;
    }

    //get category list using name or ID
    public MutableLiveData<ArrayList<Foods>> getGetCategoryUsingNameId(String searchTxt,boolean isSearch,int CategoryId) {
        ArrayList<Foods> list=new ArrayList<>();
        DatabaseReference ref=database.getReference("Foods");

        Query query;
        if(isSearch) {
           query = ref.orderByChild("Title").startAt(searchTxt)
                   .endAt(searchTxt + '\uf8ff');
        }else {
          query = ref.orderByChild("CategoryId").equalTo(CategoryId);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    list.clear();
                    for (DataSnapshot s:snapshot.getChildren()){
                        Foods currentCategory=s.getValue(Foods.class);
                        list.add(currentCategory);

                    }
                    for(Foods d:list){
                        Log.v("Tag",d.getTitle());
                    }

                    getCategoryUsingNameId.postValue(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return getCategoryUsingNameId;
    }
}
