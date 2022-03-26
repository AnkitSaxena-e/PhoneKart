package com.example.phonekart.View_Holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Interface.itermClickListner;
import com.example.phonekart.R;

public class AdResourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageViewAd;
    private itermClickListner itermClickListner;

    public AdResourceViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewAd = itemView.findViewById(R.id.iv_auto_image_slider);

    }

    @Override
    public void onClick(View v) {
        com.example.phonekart.Interface.itermClickListner.onClick(v, getAdapterPosition(),false);
    }

    public void setItermClickListner(com.example.phonekart.Interface.itermClickListner itermClickListner) {
        this.itermClickListner = itermClickListner;
    }
}
