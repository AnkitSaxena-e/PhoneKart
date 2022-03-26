package com.example.phonekart.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Modal.Order;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.R;
import com.example.phonekart.SeeBuyingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VHolder> {

    Context c;
    ArrayList<Order> categoryModals;

    public OrderAdapter(Context con, ArrayList<Order> categoryModa) {

        c = con;
        categoryModals = categoryModa;

    }

    @NonNull
    @Override
    public OrderAdapter.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_buying_layout, parent, false);
        return new OrderAdapter.VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.VHolder holder, int position) {

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

                    holder.txtEditO.setText("Cancel Order");

                    Picasso.get().load(iiii).resize(300,200).centerCrop().into(holder.imageViewO);
                    holder.txtPriceO.setText("₹" + categoryModals.get(position).getTotalAmount());
                    holder.txtProductNameO.setText(nnnn);
                    holder.txtColorO.setText(cccc);
                    holder.txtSnrO.setText(ssss);

                    holder.AddressSSO.setText(categoryModals.get(position).getAddress());

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent(c, SeeBuyingActivity.class);
                            i.putExtra("PPIdd", pppp);
                            i.putExtra("OOPPIIDd", categoryModals.get(position).getPID());
                            i.putExtra("T", "O");
                            c.startActivity(i);

                        }
                    });

                    holder.txtEditO.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog.Builder aD = new AlertDialog.Builder(c);
                            aD.setMessage("Are You Sure, Your Wanna Cancel The Order..");
                            aD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    DatabaseReference rreeff = FirebaseDatabase.getInstance().getReference().child("Order");
                                    rreeff.child(categoryModals.get(position).getPID());

                                    rreeff.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(c, "Remove Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                            aD.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alertDialog = aD.create();
                            alertDialog.show();

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

    class VHolder extends RecyclerView.ViewHolder{

        public TextView txtProductNameO;
        public TextView txtPriceO;
        public TextView AddressSSO;
        public TextView txtSnrO;
        public TextView txtColorO;
        public ImageView imageViewO;
        public TextView txtEditO;

        public VHolder(@NonNull View itemview) {
            super(itemview);

            imageViewO = itemview.findViewById(R.id.Product_Image_PP_SS);
            txtProductNameO = itemview.findViewById(R.id.Product_Name_PP_SS);
            txtPriceO = itemview.findViewById(R.id.setDasiReading_PS_PP_SS);
            txtSnrO = itemview.findViewById(R.id.Snr_PP_SS);
            txtColorO = itemview.findViewById(R.id.Color_PP_SS);
            AddressSSO = itemview.findViewById(R.id.Address_BB_SS);

            txtEditO = itemview.findViewById(R.id.Edit_PP_SS);

        }
    }
}
