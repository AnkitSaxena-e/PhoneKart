package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phonekart.Modal.Order;
import com.example.phonekart.Modal.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SeeBuyingActivity extends AppCompatActivity {

    private ImageView PD_Image;
    private TextView PD_Name, PD_Quntity, PD_Price, PD_SnR, PD_Color;
    private TextView OD_Name, OD_Number, OD_Address, OD_City, OD_Pin, OD_TotalAmount, OD_Date, OD_Time, OD_DD;
    private Button ReturnBB;
    private String ProductPID, OrderPID, T;
    private ImageView back;
    private RelativeLayout llll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_buying);

        ProductPID = getIntent().getStringExtra("PPIdd");
        OrderPID = getIntent().getStringExtra("OOPPIIDd");
        T = getIntent().getStringExtra("T");

        PD_Image = findViewById(R.id.Image_OrderSS_BB);
        PD_Name = findViewById(R.id.Name_OrdereSS_BB);
        PD_Price = findViewById(R.id.Price_OrderSS_BB);
        PD_SnR = findViewById(R.id.SR_OrderSS_BB);
        PD_Quntity = findViewById(R.id.Quantity_OrderSS_BB);
        PD_Color = findViewById(R.id.Color_OrderSS_BB);

        back = findViewById(R.id.back_sett_seeeeee);

        OD_Name = findViewById(R.id.see_order_name_BB);
        OD_Number = findViewById(R.id.see_order_PID_BB);
        OD_Address = findViewById(R.id.see_order_order_number_BB);
        OD_City = findViewById(R.id.see_order_login_number_BB);
        OD_Pin = findViewById(R.id.see_order_address_BB);
        OD_TotalAmount = findViewById(R.id.see_order_amount_BB);
        OD_Date = findViewById(R.id.see_order_type_BB);
        OD_Time = findViewById(R.id.see_order_orderNo_BB);
        OD_DD = findViewById(R.id.see_order_status_BB);

        ReturnBB = findViewById(R.id.Return_BB);

        Window window = SeeBuyingActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        if(T.equals("B")){
            OD_DD.setVisibility(View.INVISIBLE);
            ReturnBB.setVisibility(View.INVISIBLE);
        }

        getOrr(OrderPID);

        back.setOnClickListener(v -> {

            Intent i = new Intent(SeeBuyingActivity.this, HomeActivity.class);
            i.putExtra("eeee", "BA");
            startActivity(i);

        });

    }

    private void getOrr(String orderPID) {

        DatabaseReference reffreref = FirebaseDatabase.getInstance().getReference().child("Order").child(orderPID);

        reffreref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    Order ooooo = snapshot.getValue(Order.class);

                    assert ooooo != null;
                    String Name = ooooo.getName();
                    String Number = ooooo.getPhoneNumber();
                    String Address = ooooo.getAddress();
                    String City = ooooo.getCity();
                    String Pin = ooooo.getPin();
                    String TotalAmount = ooooo.getTotalAmount();
                    String Date = ooooo.getDate();
                    String Time = ooooo.getTime();
                    String DD = ooooo.getDeliveryDetail();
                    String qqqq = ooooo.getQuantity();

                    OD_Name.setText("Name: " + Name);
                    OD_Number.setText("Number: " + Number);
                    OD_Address.setText("Address: " + Address);
                    OD_City.setText("City:" + City);
                    OD_Pin.setText("Pin: " + Pin);
                    OD_TotalAmount.setText("Total Amount: ₹" + TotalAmount);
                    OD_Date.setText("Date: " + Date);
                    OD_Time.setText("Time: " + Time);
                    PD_Quntity.setText("Qutewry:" + qqqq);

                    getProd(ProductPID);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getProd(String productPID) {


        DatabaseReference reffer = FirebaseDatabase.getInstance().getReference().child("Products").child(productPID);

        reffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    Product pppp = snapshot.getValue(Product.class);

                    String Name = Objects.requireNonNull(snapshot.child("ProductName").getValue()).toString();
                    String Price = Objects.requireNonNull(snapshot.child("Price").getValue()).toString();
                    String SnR = Objects.requireNonNull(snapshot.child("SnR").getValue()).toString();
                    String Color = Objects.requireNonNull(snapshot.child("Color").getValue()).toString();
                    String Image = Objects.requireNonNull(snapshot.child("Image1").getValue()).toString();

                    PD_Name.setText(Name);
                    PD_Price.setText(Price);
                    PD_Color.setText(Color);
                    PD_SnR.setText(SnR);
                    Picasso.get().load(Image).fit().centerCrop().into(PD_Image);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SeeBuyingActivity.this, HomeActivity.class);
        i.putExtra("eeee", "BA");
        startActivity(i);
    }
}