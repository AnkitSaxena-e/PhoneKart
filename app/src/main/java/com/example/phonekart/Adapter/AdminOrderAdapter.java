 package com.example.phonekart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.AdminSeeOrderActivity;
import com.example.phonekart.Modal.Order;
import com.example.phonekart.R;

import java.util.ArrayList;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.Viewholder>{

    private ArrayList<Order> als;
    private Context context;

    public AdminOrderAdapter(FragmentActivity activity, ArrayList<Order> PList) {

        als = PList;
        context = activity;

    }

    @NonNull
    @Override
    public AdminOrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_order_details, parent, false);
        return new AdminOrderAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderAdapter.Viewholder Holder, int position) {

        Holder.tytProductFrom.setText(als.get(position).getFromUserN());
        Holder.tytProductTo.setText(als.get(position).getToUserN());
        Holder.tytProductPrice.setText(als.get(position).getTotalAmount());
        Holder.tytProductProd.setText(als.get(position).getProductN());

        Holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdminSeeOrderActivity.class);
                i.putExtra("uid", als.get(position).getPID());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return als.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        public TextView tytProductFrom, tytProductTo, tytProductPrice, tytProductProd;
        public RelativeLayout Change_Back;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tytProductFrom = itemView.findViewById(R.id.O_username);
            tytProductTo = itemView.findViewById(R.id.O_number);
            tytProductPrice = itemView.findViewById(R.id.O_price);
            tytProductProd = itemView.findViewById(R.id.O_product);
            Change_Back = itemView.findViewById(R.id.change_back);

        }
    }
}
