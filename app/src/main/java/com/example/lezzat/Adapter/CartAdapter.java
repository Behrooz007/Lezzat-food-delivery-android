package com.example.lezzat.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.lezzat.Helper.ChangeNumberItemsListener;
import com.example.lezzat.Helper.ManagmentCart;
import com.example.lezzat.Model.Foods;
import com.example.lezzat.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    private ArrayList<Foods> list;
    private ManagmentCart managmentCart;

    ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<Foods> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        this.managmentCart =new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_cart,parent,false);
        return new CartViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Foods currentFood=list.get(position);

        holder.title.setText(currentFood.getTitle());
        holder.feeEachItem.setText(currentFood.getPrice() +"$");
        holder.totalEachItem.setText(currentFood.getNumberInCart() +"*$"+
                (currentFood.getNumberInCart()*currentFood.getPrice()));
        holder.numItems.setText(currentFood.getNumberInCart()+"");

        Glide.with(holder.itemView.getContext())
                .load(currentFood.getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.pic);

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managmentCart.plusNumberItem(list, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                managmentCart.minusNumberItem(list, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() !=0){
            return list.size();
        }else{
            return 0;
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView title,totalEachItem,numItems,feeEachItem
                ,plusBtn,minusBtn;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pic=itemView.findViewById(R.id.picCart);
            this.title=itemView.findViewById(R.id.titleTxtCart);
            this.totalEachItem=itemView.findViewById(R.id.totalEachItem);
            this.feeEachItem=itemView.findViewById(R.id.feeEachItem);
            this.numItems=itemView.findViewById(R.id.num_cartTxt);
            this.plusBtn=itemView.findViewById(R.id.plus_CartBtn4);
            this.minusBtn=itemView.findViewById(R.id.minus_cartBtn);


        }
    }
}
