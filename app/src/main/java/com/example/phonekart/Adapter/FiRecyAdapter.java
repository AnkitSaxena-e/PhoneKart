package com.example.phonekart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Fragments.ProductDataFragment;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class FiRecyAdapter extends RecyclerView.Adapter<FiRecyAdapter.PProductGridLayout> {

    private ArrayList<Product> modal;
    private String CheckIt;
    private Context context;

    public FiRecyAdapter(Context con, ArrayList<Product> categoryModals, String s) {

        context = con;
        CheckIt = s;
        modal = categoryModals;

    }

    @NonNull
    @Override
    public PProductGridLayout onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_grid_layout, parent, false);
        return new PProductGridLayout(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PProductGridLayout holder, int position) {

        Picasso.get().load(modal.get(position).getImage1()).resize(300,200).centerCrop().into(holder.imageaView);

        holder.tatProductName.setText(modal.get(position).getProductName());
        holder.tatProductPrice.setText("₹" + modal.get(position).getPrice());
        holder.tatProductColor.setText(modal.get(position).getColor());
        holder.tatProductRating.setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(v -> {
                Paper.book().write(Prevalant.ProductId, modal.get(position).getPid());
                FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new ProductDataFragment());
                ft.addToBackStack(null);
                ft.commit();
        });

    }

    @Override
    public int getItemCount() {
        return modal.size();
    }


    static class PProductGridLayout extends RecyclerView.ViewHolder {

        public TextView tatProductName;
        public TextView tatProductPrice;
        public TextView tatProductColor;
        public TextView tatProductRating;
        public ImageView imageaView;

        public PProductGridLayout(@NonNull View itemView) {
            super(itemView);

            imageaView = itemView.findViewById(R.id.image_Product);
            tatProductName = itemView.findViewById(R.id.name_Product);
            tatProductRating = itemView.findViewById(R.id.setDasiReading_Proo);
            tatProductPrice = itemView.findViewById(R.id.price_Product);
            tatProductColor = itemView.findViewById(R.id.colo_product);
        }
    }

}
