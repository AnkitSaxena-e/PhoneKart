package com.example.phonekart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.HomeActivity;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Viewholder> {

    private ArrayList<Product> Modals;
    private String CheckIt;
    private String orderNo;
    private Context context;

    public SearchAdapter(Context con, ArrayList<Product> categoryModals, String s) {
        this.Modals = categoryModals;
        CheckIt = s;
        context = con;
    }

    @NonNull
    @Override
    public SearchAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_layout, parent, false);
        return new SearchAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.Viewholder holder, int position) {

        try {

            holder.BtatProductName.setText(Modals.get(position).getProductName());
            holder.BtatProductPrice.setText(Modals.get(position).getPrice());
            holder.BtatProductSnR.setText(Modals.get(position).getSnR());
            holder.BtatProductColor.setText(Modals.get(position).getColor());
            holder.BtatDRating.setVisibility(View.INVISIBLE);
            Picasso.get().load(Modals.get(position).getImage1()).resize(300,200).centerCrop().into(holder.BimageaView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Paper.book().write(Prevalant.ProductId, Modals.get(position).getPid());
                    Intent i = new Intent(context, HomeActivity.class);
                    i.putExtra("eeee", "PDA");
                    context.startActivity(i);

                }
            });

        }catch (Exception e){
            Toast.makeText(context, "ghghghgh" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return Modals.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{

        public TextView BtatProductName, BtatProductColor, BtatProductSnR, BtatProductPrice, BtatDRating;
        public ImageView BimageaView;

        public Viewholder(View itemview){
            super(itemview);
            BimageaView = itemView.findViewById(R.id.Product_Image_II_PPPP);

            BtatProductPrice = itemview.findViewById(R.id.Product_Price_II_PPPP);
            BtatProductName = itemView.findViewById(R.id.Product_Name_II_PPPP);
            BtatDRating = itemView.findViewById(R.id.setDasiReading_PS_II_PPPP);
            BtatProductSnR = itemView.findViewById(R.id.Snr_PP_BBBB_II_PPPP);
            BtatProductColor = itemView.findViewById(R.id.Color_PP_BBBB_II_PPPP);
        }
    }

}