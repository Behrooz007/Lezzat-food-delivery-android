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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.lezzat.Activity.DetailActivity;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.R;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    Context context;
    ArrayList<Foods> categoryList;

    public CategoryListAdapter(ArrayList<Foods> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        View view= LayoutInflater.from(context).
                inflate(R.layout.viewholder_list_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Foods currentFood=categoryList.get(position);

        holder.title.setText(currentFood.getTitle());
        holder.time.setText(currentFood.getTimeValue()+" min");
        holder.price.setText(currentFood.getPrice()+"$");
        holder.rate.setText(currentFood.getStar()+"");

        Glide.with(context).
                load(currentFood.getImagePath()).
                transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("food",currentFood);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        if(categoryList != null){
         return categoryList.size();
        }else{
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,time,price,rate;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title=itemView.findViewById(R.id.titleTxtList);
            this.time=itemView.findViewById(R.id.timeTxtList);
            this.price=itemView.findViewById(R.id.priceTxtList);
            this.rate=itemView.findViewById(R.id.rateTxtList);
            this.image=itemView.findViewById(R.id.image);

        }
    }
}
