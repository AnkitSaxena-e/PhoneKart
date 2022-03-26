package com.example.phonekart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.EditProductActivity;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;
import com.example.phonekart.Fragments.ProductDataFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SellingAdapter extends RecyclerView.Adapter<SellingAdapter.Viewholder> {

    private ArrayList<Product> categoryModals;
    private String CheckIt;
    private Context context;

    public SellingAdapter(Context con, ArrayList<Product> categoryModals, String s) {
        this.categoryModals = categoryModals;
        CheckIt = s;
        context = con;
    }

    @NonNull
    @Override
    public SellingAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_sellings_layout, parent, false);
        return new SellingAdapter.Viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SellingAdapter.Viewholder holder, int position) {

        Picasso.get().load(categoryModals.get(position).getImage1()).resize(300,200).centerCrop().into(holder.imageView);
        holder.txtPRating.setVisibility(View.INVISIBLE);
        holder.txtProductName.setText(categoryModals.get(position).getProductName());
        holder.txtColor.setText(categoryModals.get(position).getColor());
        holder.txtPrice.setText("₹" + categoryModals.get(position).getPrice());
        holder.txtSnr.setText(categoryModals.get(position).getSnR());
        holder.txtTotalRating.setVisibility(View.INVISIBLE);

        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckIt.equals("LOLO")){
                    Intent i = new Intent(context, EditProductActivity.class);
                    i.putExtra("pihd", categoryModals.get(position).getPid());
                    i.putExtra("Admin", "Admin");
                    context.startActivity(i);
                }else {
                    Intent i = new Intent(context, EditProductActivity.class);
                    i.putExtra("pihd", categoryModals.get(position).getPid());
                    i.putExtra("Admin", "User");
                    context.startActivity(i);
                }

            }
        });

        if(CheckIt.equals("LOLO")){
            holder.txtSee.setVisibility(View.GONE);
        }

        holder.txtSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write(Prevalant.ProductId, categoryModals.get(position).getPid());
                FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new ProductDataFragment());
                ft.addToBackStack(null);
                ft.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryModals.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{

        public TextView txtProductName, txtPRating, txtTotalRating, txtSnr, txtColor, txtPrice;
        public ImageView imageView;
        public TextView txtEdit, txtSee;

        public Viewholder(View itemview){
            super(itemview);
            imageView = itemview.findViewById(R.id.Product_Image_PP);
            txtProductName = itemview.findViewById(R.id.Product_Name_PP);
            txtPRating = itemview.findViewById(R.id.setDasiReading_PS_PP);
            txtTotalRating = itemview.findViewById(R.id.setTotalRating_PS_PP);
            txtSnr = itemview.findViewById(R.id.Snr_PP);
            txtPrice = itemview.findViewById(R.id.Price_Name_PPVV);
            txtColor = itemview.findViewById(R.id.Color_PP);

            txtEdit = itemview.findViewById(R.id.Edit_PP);
            txtSee = itemview.findViewById(R.id.See_PP);
        }
    }

}