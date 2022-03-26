package com.example.phonekart.View_Holder;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Interface.itermClickListner;
import com.example.phonekart.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView CatEditButton, CatRemoveButton;
    public TextView CatName, CatNumber, CatPin, CatAddress;
    public RelativeLayout CatCard;
    public com.example.phonekart.Interface.itermClickListner itermClickListner;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        CatName = itemView.findViewById(R.id.resourseName);
        CatAddress = itemView.findViewById(R.id.resourseAddress);
        CatNumber = itemView.findViewById(R.id.resourseNumber);
        CatPin = itemView.findViewById(R.id.resoursePin);

        CatCard = itemView.findViewById(R.id.addressCard);

        CatEditButton = itemView.findViewById(R.id.resourseEdit);
        CatRemoveButton = itemView.findViewById(R.id.resourseRemove);

    }
    @Override
    public void onClick(View v) {
        com.example.phonekart.Interface.itermClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItermClickListner(itermClickListner itermClickListner) {
        this.itermClickListner = itermClickListner;
    }
}
