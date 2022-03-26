package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminSeeOrderActivity extends AppCompatActivity {
    
    private TextView O_Name, O_Number, O_Address, O_ONO, O_From, O_To, O_DT, O_DetailDate, O_Return;
    private TextView PP_Name, PP_Price, PP_Qut, PP_Color, PP_SnR;
    private ImageView PP_Image;
    private ImageView O_Name_Copy, O_Number_Copy, O_Address_Copy, O_ONO_Copy, O_From_Copy, O_To_Copy, O_DT_Copy, O_DetailDate_Copy, O_Return_Copy;
    private String PID, Type, From, To, Name, NNO, ONO, Address, Amount, City, Pin, Return, DD, PPPP;
    private ClipboardManager clipboardManager;
    private Button OrDele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_order);

        O_Name = findViewById(R.id.see_order_name);
        O_Number = findViewById(R.id.see_order_num);
        O_Address = findViewById(R.id.see_order_address);
        O_ONO = findViewById(R.id.see_order_Onum);
        O_From = findViewById(R.id.see_order_from);
        O_To = findViewById(R.id.see_order_to);
        O_DT = findViewById(R.id.see_order_pid);
        O_DetailDate = findViewById(R.id.see_order_detaildate);
        O_Return = findViewById(R.id.see_order_return);

        OrDele = findViewById(R.id.Dele_Order);

        O_Name_Copy = findViewById(R.id.Name_C);
        O_Number_Copy = findViewById(R.id.num_C);
        O_Address_Copy = findViewById(R.id.address_C);
        O_ONO_Copy = findViewById(R.id.Onum_C);
        O_From_Copy = findViewById(R.id.from_C);
        O_To_Copy = findViewById(R.id.to_C);
        O_DT_Copy = findViewById(R.id.pid_C);
        O_DetailDate_Copy = findViewById(R.id.detaildate_C);
        O_Return_Copy = findViewById(R.id.return_C);

        PP_Name = findViewById(R.id.Product_Name_IIOOO);
        PP_Price = findViewById(R.id.Product_Price_IIOOO);
        PP_Qut = findViewById(R.id.Product_Qun_IIOOO);
        PP_Color = findViewById(R.id.Color_PP_BBBB_IIOOO);
        PP_SnR = findViewById(R.id.Snr_PP_BBBB_IIOOO);
        PP_Image = findViewById(R.id.Product_Image_IIOOO);
        
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        PID = getIntent().getStringExtra("uid");

        SetOrderDetail(PID);

        OrDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference ProfileRef = FirebaseDatabase.getInstance().getReference().child("Order").child(PID);

                android.app.AlertDialog.Builder aD = new android.app.AlertDialog.Builder(AdminSeeOrderActivity.this);
                aD.setMessage("Are You Sure, Your Wanna Delete this Order..");
                aD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ProfileRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AdminSeeOrderActivity.this, "Remove Successfully", Toast.LENGTH_SHORT).show();
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

    private void SetOrderDetail(String pid) {

        final DatabaseReference oAdminA = FirebaseDatabase.getInstance().getReference()
                .child("Order").child(pid);

        oAdminA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    try {

                        Name = dataSnapshot.child("Name").getValue().toString();
                        NNO = dataSnapshot.child("PhoneNumber").getValue().toString();
                        ONO = dataSnapshot.child("ONO").getValue().toString();
                        Address = dataSnapshot.child("Address").getValue().toString();
                        PID = dataSnapshot.child("PID").getValue().toString();
                        Amount = dataSnapshot.child("TotalAmount").getValue().toString();
                        City = dataSnapshot.child("City").getValue().toString();
                        Pin = dataSnapshot.child("Pin").getValue().toString();
                        Return = dataSnapshot.child("Return").getValue().toString();
                        DD = dataSnapshot.child("DeliveryDetail").getValue().toString();
                        From = dataSnapshot.child("FromUser").getValue().toString();
                        To = dataSnapshot.child("ToUser").getValue().toString();
                        PPPP = dataSnapshot.child("ProductPID").getValue().toString();
                        String QQQP = dataSnapshot.child("Quantity").getValue().toString();

                        O_Name.setText("Name: " + Name);
                        O_Number.setText("Orignal No: " + NNO);
                        O_Address.setText("Address: " + Address);
                        O_ONO.setText("Order No: " + ONO);
                        O_DT.setText("PID: " + PID);
                        O_DetailDate.setText("Delivery Date: " + DD);
                        O_Return.setText("Return: " + Return);

                        PP_Qut.setText("Qut: " + QQQP);
                        
                        AddFT(From, To);

                      
                    }catch (Exception e){
                        Toast.makeText(AdminSeeOrderActivity.this, "aaaaa " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void AddFT(String from, String to) {

        final DatabaseReference oA = FirebaseDatabase.getInstance().getReference()
                .child("User").child(from);

        oA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String nn = snapshot.child("Name").getValue().toString();

                    O_From.setText("From: " + nn);

                    AddT(to);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void AddT(String to) {

        final DatabaseReference aoA = FirebaseDatabase.getInstance().getReference()
                .child("User").child(to);

        aoA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String jnn = snapshot.child("Name").getValue().toString();

                    O_To.setText("To: " + jnn);

                    ProductPPID();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ProductPPID() {

        final DatabaseReference aoAPP = FirebaseDatabase.getInstance().getReference()
                .child("Products").child(PPPP);

        aoAPP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String Pname = snapshot.child("ProductName").getValue().toString();
                    String Pprice = snapshot.child("Price").getValue().toString();
                    String Pcolor = snapshot.child("Color").getValue().toString();
                    String Psnr = snapshot.child("SnR").getValue().toString();
                    String Pimage = snapshot.child("Image1").getValue().toString();

                    PP_Name.setText(Pname);
                    PP_Price.setText("₹" + Pprice);
                    PP_Color.setText(Pcolor);
                    PP_SnR.setText(Psnr);
                    Picasso.get().load(Pimage).fit().centerCrop().into(PP_Image);

                    CopyT();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CopyT() {

        O_Name_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = Name;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_Number_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = NNO;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_Address_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = Address;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_ONO_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = ONO;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_From_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = From;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_To_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = To;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_DT_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = PID;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_DetailDate_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = DD;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_Return_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = Return;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(AdminSeeOrderActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });


    }

}