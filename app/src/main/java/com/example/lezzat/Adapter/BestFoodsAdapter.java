package com.example.lezzat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.lezzat.Activity.DetailActivity;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.Model.Time;
import com.example.lezzat.R;

import java.util.ArrayList;

public class BestFoodsAdapter extends RecyclerView.Adapter<BestFoodsAdapter.MyViewHolder> {

    ArrayList<Foods> items;
    Context context;

    public BestFoodsAdapter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BestFoodsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_best_deal,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestFoodsAdapter.MyViewHolder holder, int position) {

        //binding all views with data in best food category
        Foods currentFood=items.get(position);

        if(currentFood.getTitle().length()>10){

            CharSequence text=currentFood.getTitle();

            String currentTxt=text.subSequence(0,10)+"...";
            holder.title.setText(currentTxt);
        }else {
            holder.title.setText(currentFood.getTitle());
        }

        //holder.title.setText(currentFood.getTitle());
        holder.TimeTxt.setText(currentFood.getTimeValue()+" min");
        holder.priceTxt.setText("$"+currentFood.getPrice());
        holder.starTxt.setText(""+currentFood.getStar());
        //glide for binding the image
        Glide.with(context).load(currentFood.getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.pic);

        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, DetailActivity.class);
                intent.putExtra("food",currentFood);
                context.startActivity(intent);
            }
        });

        holder.layClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, DetailActivity.class);
                intent.putExtra("food",currentFood);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        if(items != null){
            return items.size();
        }else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView pic;
        TextView title;
        TextView starTxt;
        TextView TimeTxt;
        TextView plusTxt;
        TextView priceTxt;
        ConstraintLayout layClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleTxt);
            starTxt=itemView.findViewById(R.id.starTxt);
            TimeTxt=itemView.findViewById(R.id.timeTxt);
            plusTxt=itemView.findViewById(R.id.plusTxt);
            pic=itemView.findViewById(R.id.pic);
            priceTxt=itemView.findViewById(R.id.priceTxt);
            layClick=itemView.findViewById(R.id.layoutClick);

        }
    }
}
