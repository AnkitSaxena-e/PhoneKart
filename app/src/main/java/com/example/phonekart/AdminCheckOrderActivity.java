package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phonekart.Modal.OrderDetailLayout;
import com.example.phonekart.View_Holder.catagorySearchVewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminCheckOrderActivity extends AppCompatActivity{

    private RecyclerView productList;
    private RecyclerView.LayoutManager layoutManager;
    View view;
    private DatabaseReference cartListRef;
    private String OrderNo = "", PID = "", Image = "", PhoneNo = "", Admin = "", PrintName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_order);

        try {
            OrderNo = getIntent().getStringExtra("OrderNo");
            Image = getIntent().getStringExtra("Image");
            PID = getIntent().getStringExtra("PID");
            Admin = getIntent().getStringExtra("Admin");
            PhoneNo = getIntent().getStringExtra("PN");
            PrintName = getIntent().getStringExtra("PrintName");

        }
        catch (Exception e){
            Toast.makeText(AdminCheckOrderActivity.this,"1"+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        productList = findViewById(R.id.Product_Detail_Activity);
        productList.setItemViewCacheSize(20);
        productList.setDrawingCacheEnabled(true);
        productList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);

        SetNotification();

    }

    private void SetNotification() {



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AdminCheckOrderActivity.this);

        mBuilder.setSmallIcon(R.drawable.profile);
        mBuilder.setContentTitle("!Order Alert!");
        mBuilder.setContentText("Hi, There is an Order Let's Check It....");

        Intent ii = new Intent(this, AdminCheckOrderActivity.class);
        PendingIntent iiii = PendingIntent.getActivity(this, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(iiii);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());

    }

    @Override
    protected void onStart() {
        super.onStart();

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Order List").child(PhoneNo).child("Products").child(OrderNo);

        FirebaseRecyclerOptions<OrderDetailLayout> options =
                new FirebaseRecyclerOptions.Builder<OrderDetailLayout>()
                        .setQuery(cartListRef, OrderDetailLayout.class)
                        .build();

        FirebaseRecyclerAdapter<OrderDetailLayout, catagorySearchVewHolder> adapter =
                new FirebaseRecyclerAdapter<OrderDetailLayout, catagorySearchVewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull catagorySearchVewHolder holderC, int i, @NonNull final OrderDetailLayout cart) {
                        holderC.tltProductName.setText(cart.getPname());
                        holderC.tltProductPrice.setText("Rs." + cart.getPprice());
                        holderC.tltProductQuantity.setText("Quantity = " + cart.getQuantity());
                        holderC.tltProductColor.setText(cart.getColor());
                        holderC.tltProductSR.setText(cart.getSnR());

                        Picasso.get().load(cart.getPimage()).fit().centerCrop().into(holderC.tltProductImage);

                        holderC.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(Admin.equals("Admin")){
                                    String PPIIDD = cart.getPid();

                                    Intent i = new Intent(AdminCheckOrderActivity.this, AdminProductInfoActivity.class);
                                    i.putExtra("PPIIDD", PPIIDD);
                                    i.putExtra("Price", PPIIDD);
                                    i.putExtra("Color", PPIIDD);
                                    i.putExtra("SnR", PPIIDD);
                                    startActivity(i);
                                }
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public catagorySearchVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detaile_layout_activity, parent, false);
                        catagorySearchVewHolder holder = new catagorySearchVewHolder(view);
                        return holder;
                    }
                };
        productList.setAdapter(adapter);
        adapter.startListening();
    }
}
