package com.example.phonekart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.AddProductDataActivity;
import com.example.phonekart.AdminMaintainProductActivity;
import com.example.phonekart.Modal.ProductPhone;
import com.example.phonekart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {

    private ArrayList<ProductPhone> categoryModals;
    private String CheckIt;
    private Context context;

    public CategoryAdapter(Context con, ArrayList<ProductPhone> categoryModals, String s) {
        this.categoryModals = categoryModals;
        CheckIt = s;
        context = con;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Picasso.get().load(categoryModals.get(position).getImage1()).resize(300,200).centerCrop().into(holder.imageView);
        holder.txtPRating.setVisibility(View.INVISIBLE);
        holder.txtProductName.setText(categoryModals.get(position).getProductName());
        holder.txtSnR.setVisibility(View.INVISIBLE);
        holder.txtColor.setVisibility(View.INVISIBLE);
        holder.txtPricee.setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!CheckIt.equals("Admin")){
                    Intent i = new Intent(context, AddProductDataActivity.class);
                    i.putExtra("pid", categoryModals.get(position).getPid());
                    context.startActivity(i);
                }else{
                    Intent i = new Intent(context, AdminMaintainProductActivity.class);
                    i.putExtra("pid", categoryModals.get(position).getPid());
                    context.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModals.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        public TextView txtProductName, txtPRating, txtSnR, txtColor, txtPricee;
        public ImageView imageView;

        public Viewholder(View itemview) {
            super(itemview);
            imageView = itemview.findViewById(R.id.Product_Image_II_PPPP);
            txtPricee = itemview.findViewById(R.id.Product_Price_II_PPPP);
            txtProductName = itemview.findViewById(R.id.Product_Name_II_PPPP);
            txtColor = itemview.findViewById(R.id.Color_PP_BBBB_II_PPPP);
            txtSnR = itemview.findViewById(R.id.Snr_PP_BBBB_II_PPPP);
            txtPRating = itemview.findViewById(R.id.setDasiReading_PS_II_PPPP);
        }
    }

}
