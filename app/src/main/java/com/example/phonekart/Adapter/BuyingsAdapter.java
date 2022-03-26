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
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Modal.Order;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.R;
import com.example.phonekart.SeeBuyingActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BuyingsAdapter extends RecyclerView.Adapter<BuyingsAdapter.Viewholder> {

    private ArrayList<Order> categoryModals;
    private String CheckIt;
    private String orderNo;
    private Context context;

    public BuyingsAdapter(Context con, ArrayList<Order> categoryModals, String s, String OrderNo) {
        this.categoryModals = categoryModals;
        CheckIt = s;
        orderNo = OrderNo;
        context = con;
    }

    @NonNull
    @Override
    public BuyingsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_buying_layout, parent, false);
        return new BuyingsAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyingsAdapter.Viewholder holder, int position) {

        String pppp = categoryModals.get(position).getProductPID();

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(pppp).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    Product product = snapshot.getValue(Product.class);

                    assert product != null;
                    String nnnn = product.getProductName();
                    String iiii = product.getImage1();
                    String cccc = product.getColor();
                    String ssss = product.getSnR();

                    Picasso.get().load(iiii).resize(300,200).centerCrop().into(holder.imageView);
                    holder.txtPrice.setText("₹" + categoryModals.get(position).getTotalAmount());
                    holder.txtProductName.setText(nnnn);
                    holder.txtColor.setText(cccc);
                    holder.txtSnr.setText(ssss);

                    holder.AddressSS.setText(categoryModals.get(position).getAddress());

                    holder.txtEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent(context, SeeBuyingActivity.class);
                            i.putExtra("PPIdd", pppp);
                            i.putExtra("OOPPIIDd", categoryModals.get(position).getPID());
                            i.putExtra("T", "B");
                            context.startActivity(i);

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModals.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{

        public TextView txtProductName;
        public TextView txtPrice;
        public TextView AddressSS;
        public TextView txtSnr;
        public TextView txtColor;
        public ImageView imageView;
        public TextView txtEdit;

        public Viewholder(View itemview){
            super(itemview);
            imageView = itemview.findViewById(R.id.Product_Image_PP_SS);
            txtProductName = itemview.findViewById(R.id.Product_Name_PP_SS);
            txtPrice = itemview.findViewById(R.id.setDasiReading_PS_PP_SS);
            txtSnr = itemview.findViewById(R.id.Snr_PP_SS);
            txtColor = itemview.findViewById(R.id.Color_PP_SS);
            AddressSS = itemview.findViewById(R.id.Address_BB_SS);

            txtEdit = itemview.findViewById(R.id.Edit_PP_SS);
        }
    }

}