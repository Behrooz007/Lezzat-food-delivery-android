package com.example.lezzat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lezzat.Activity.ListFoodsActivity;
import com.example.lezzat.Model.Category;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.catViewHolder> {

    Context context;
    ArrayList<Category> categoryList;

    public CategoryAdapter(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public catViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.viewholder_category,parent,false);
        return new catViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull catViewHolder holder, int position) {

        Category currentCat=categoryList.get(position);
        holder.catName.setText(currentCat.getName());

        switch (position){
            case 0:holder.catImg.setBackgroundResource(R.drawable.cat_0_background);
            break;
            case 1:holder.catImg.setBackgroundResource(R.drawable.cat_1_background);
                break;
            case 2:holder.catImg.setBackgroundResource(R.drawable.cat_2_background);
                break;
            case 3:holder.catImg.setBackgroundResource(R.drawable.cat_3_background);
                break;
            case 4:holder.catImg.setBackgroundResource(R.drawable.cat_4_background);
                break;
            case 5:holder.catImg.setBackgroundResource(R.drawable.cat_5_background);
                break;
            case 6:holder.catImg.setBackgroundResource(R.drawable.cat_6_background);
                break;
            case 7:holder.catImg.setBackgroundResource(R.drawable.cat_7_background);
                break;
        }
        int getIdentifier=context.getResources().getIdentifier
                (currentCat.getImagePath(),"drawable",holder.catImg.getContext().getPackageName());


        Glide.with(context).load(getIdentifier).into(holder.catImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, ListFoodsActivity.class);
                intent.putExtra("CategoryId",categoryList.get(position).getId());
                intent.putExtra("CategoryName",currentCat.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        if (categoryList != null){
            return  categoryList.size();
        }else {
            return 0;
        }
    }

    public class catViewHolder extends RecyclerView.ViewHolder{

        ImageView catImg;
        TextView catName;
        public catViewHolder(@NonNull View itemView) {
            super(itemView);
            catImg=itemView.findViewById(R.id.catPic);
            catName=itemView.findViewById(R.id.catName);


        }
    }
}
