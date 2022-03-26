package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonekart.Modal.ProductPhone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.paperdb.Paper;

public class AdminMaintainProductActivity extends AppCompatActivity {

    private String ProductName, ProductKeyward, storeCurruntDate, storeCurruntTime;
    private String ColorT1 = "A", ColorT2 = "A", ColorT3 = "A", ColorT4 = "A", ColorT5 = "A", ColorT6 = "A", ColorT7 = "A", ColorT8 = "A", ColorT9 = "A", ColorT10 = "A",
            SnRT1 = "A", SnRT2 = "A", SnRT3 = "A", SnRT4 = "A", SnRT5 = "A", SnRT6 = "A", SnRT7 = "A", SnRT8 = "A", SnRT9 = "A", SnRT10 = "A";
    private String Table_Name, Table_Detail, Table_Brand;
    private EditText productName, productKeyeard;
    private EditText Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9, Color10, SnR1, SnR2, SnR3, SnR4, SnR5, SnR6, SnR7, SnR8, SnR9, SnR10;
    private EditText Table_N, Table_D, Table_B;
    private String PPPIIIDDD = "";
    private ImageView picPhoto1;
    private Button addProduct, deleteProduct;
    private DatabaseReference ProductRef;
    private String productRandomKey;
    private Dialog LoadingBar;
    private String Chh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);

        PPPIIIDDD = getIntent().getStringExtra("pid");

        Toast.makeText(this, PPPIIIDDD, Toast.LENGTH_SHORT).show();

        Paper.init(AdminMaintainProductActivity.this);

        productName = findViewById(R.id.ProductName_MMMM);
        productKeyeard = findViewById(R.id.ProductKeyward_MMMM);

        picPhoto1 = findViewById(R.id.PicPhoto1_MMMM);

        Table_N = findViewById(R.id.name_T_A_MMMM);
        Table_D = findViewById(R.id.Detail_T_A_MMMM);
        Table_B = findViewById(R.id.brand_T_A_MMMM);

        Color1 = findViewById(R.id.ProductColor1_MMMM);
        Color2 = findViewById(R.id.ProductColor2_MMMM);
        Color3 = findViewById(R.id.ProductColor3_MMMM);
        Color4 = findViewById(R.id.ProductColor4_MMMM);
        Color5 = findViewById(R.id.ProductColor5_MMMM);
        Color6 = findViewById(R.id.ProductColor6_MMMM);
        Color7 = findViewById(R.id.ProductColor7_MMMM);
        Color8 = findViewById(R.id.ProductColor8_MMMM);
        Color9 = findViewById(R.id.ProductColor9_MMMM);
        Color10 = findViewById(R.id.ProductColor10_MMMM);

        SnR1 = findViewById(R.id.ProductSR1_MMMM);
        SnR2 = findViewById(R.id.ProductSR2_MMMM);
        SnR3 = findViewById(R.id.ProductSR3_MMMM);
        SnR4 = findViewById(R.id.ProductSR4_MMMM);
        SnR5 = findViewById(R.id.ProductSR5_MMMM);
        SnR6 = findViewById(R.id.ProductSR6_MMMM);
        SnR7 = findViewById(R.id.ProductSR7_MMMM);
        SnR8 = findViewById(R.id.ProductSR8_MMMM);
        SnR9 = findViewById(R.id.ProductSR9_MMMM);
        SnR10 = findViewById(R.id.ProductSR10_MMMM);

        LoadingBar = new Dialog(AdminMaintainProductActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);
        addProduct = findViewById(R.id.AddProduct_MMMM);
        deleteProduct = findViewById(R.id.DeleteProduct_MMMM);

        ProductRef = FirebaseDatabase.getInstance().getReference().child("ProductsPhones");

        SetDataEE(PPPIIIDDD);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();
            }
        });

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteProduct();
            }
        });

    }

    private void DeleteProduct() {

        DatabaseReference productRefD = FirebaseDatabase.getInstance().getReference().child("ProductsPhones");

        productRefD.child(PPPIIIDDD).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Intent i = new Intent(AdminMaintainProductActivity.this, AdminMainActivity.class);
                    startActivity(i);
                    LoadingBar.dismiss();
                    Toast.makeText(AdminMaintainProductActivity.this, "Product Removed successfully.......", Toast.LENGTH_LONG).show();
                } else {
                    String massage = task.getException().toString();
                    Toast.makeText(AdminMaintainProductActivity.this, "Error...." + massage, Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void ValidateProduct() {

        try {

            ProductName = productName.getText().toString();
            ProductKeyward = productKeyeard.getText().toString();
            Table_Name = Table_N.getText().toString();
            Table_Detail = Table_D.getText().toString();
            Table_Brand = Table_B.getText().toString();

            ColorT1 = Color1.getText().toString();
            ColorT2 = Color2.getText().toString();
            ColorT3 = Color3.getText().toString();
            ColorT4 = Color4.getText().toString();
            ColorT5 = Color5.getText().toString();
            ColorT6 = Color6.getText().toString();
            ColorT7 = Color7.getText().toString();
            ColorT8 = Color8.getText().toString();
            ColorT9 = Color9.getText().toString();
            ColorT10 = Color10.getText().toString();

            SnRT1 = SnR1.getText().toString();
            SnRT2 = SnR2.getText().toString();
            SnRT3 = SnR3.getText().toString();
            SnRT4 = SnR4.getText().toString();
            SnRT5 = SnR5.getText().toString();
            SnRT6 = SnR6.getText().toString();
            SnRT7 = SnR7.getText().toString();
            SnRT8 = SnR8.getText().toString();
            SnRT9 = SnR9.getText().toString();
            SnRT10 = SnR10.getText().toString();

            if (TextUtils.isEmpty(ProductName)) {
                productName.setError("Please Enter");
            } else if (TextUtils.isEmpty(ProductKeyward)) {
                productKeyeard.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Name)) {
                Table_N.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Brand)) {
                Table_B.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Detail)) {
                Table_D.setError("Please Enter");
            } else if (ColorT1.isEmpty()) {
                Color1.setError("Please Enter");
            } else if (SnRT1.isEmpty()) {
                SnR1.setError("Please Enter");
            } else if (ColorT2.isEmpty()) {
                Color2.setError("Please Enter");
            } else if (SnRT2.isEmpty()) {
                SnR2.setError("Please Enter");
            } else {

                if (ColorT3.isEmpty()) {
                    ColorT3 = "A";
                }
                if (ColorT4.isEmpty()) {
                    ColorT4 = "A";
                }
                if (ColorT5.isEmpty()) {
                    ColorT5 = "A";
                }
                if (ColorT6.isEmpty()) {
                    ColorT6 = "A";
                }
                if (ColorT7.isEmpty()) {
                    ColorT7 = "A";
                }
                if (ColorT8.isEmpty()) {
                    ColorT8 = "A";
                }
                if (ColorT9.isEmpty()) {
                    ColorT9 = "A";
                }
                if (ColorT10.isEmpty()) {
                    ColorT10 = "A";
                }
                if (SnRT3.isEmpty()) {
                    SnRT3 = "A";
                }
                if (SnRT4.isEmpty()) {
                    SnRT4 = "A";
                }
                if (SnRT5.isEmpty()) {
                    SnRT5 = "A";
                }
                if (SnRT6.isEmpty()) {
                    SnRT6 = "A";
                }
                if (SnRT7.isEmpty()) {
                    SnRT7 = "A";
                }
                if (SnRT8.isEmpty()) {
                    SnRT8 = "A";
                }
                if (SnRT9.isEmpty()) {
                    SnRT9 = "A";
                }
                if (SnRT10.isEmpty()) {
                    SnRT10 = "A";
                }

                LoadingBar.show();

                getON(ColorT1, ColorT2, ColorT3, ColorT4, ColorT5, ColorT6, ColorT7, ColorT8, ColorT9, ColorT10,
                        SnRT1, SnRT2, SnRT3, SnRT4, SnRT5, SnRT6, SnRT7, SnRT8, SnRT9, SnRT10);
            }

        }
        catch (Exception e){
            Toast.makeText(AdminMaintainProductActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getON(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                       String colorT8, String colorT9, String colorT10, String snRT1, String snRT2, String snRT3, String snRT4,
                       String snRT5, String snRT6, String snRT7, String snRT8, String snRT9, String snRT10) {

        int o = 0;
        int n = 0;

        if (!colorT1.equals("A") && colorT2.equals("A") && colorT3.equals("A") && colorT4.equals("A") && colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 1;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && colorT3.equals("A") && colorT4.equals("A") && colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 2;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && colorT4.equals("A") && colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 3;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 4;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 5;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 6;

        } else if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && !colorT7.equals("A") && colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 7;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && !colorT7.equals("A") && !colorT8.equals("A") && colorT9.equals("A") && colorT10.equals("A")) {

            o = 8;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && !colorT7.equals("A") && !colorT8.equals("A") && !colorT9.equals("A") && colorT10.equals("A")) {

            o = 9;

        }
        if (!colorT1.equals("A") && !colorT2.equals("A") && !colorT3.equals("A") && !colorT4.equals("A") && !colorT5.equals("A") &&
                !colorT6.equals("A") && !colorT7.equals("A") && !colorT8.equals("A") && !colorT9.equals("A") && !colorT10.equals("A")) {

            o = 10;

        }


        if (!snRT1.equals("A") && snRT2.equals("A") && snRT3.equals("A") && snRT4.equals("A") && snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 1;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && snRT3.equals("A") && snRT4.equals("A") && snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 2;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && snRT4.equals("A") && snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 3;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 4;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 5;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 6;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && !snRT7.equals("A") && snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 7;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && !snRT7.equals("A") && !snRT8.equals("A") && snRT9.equals("A") && snRT10.equals("A")) {

            n = 8;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && !snRT7.equals("A") && !snRT8.equals("A") && !snRT9.equals("A") && snRT10.equals("A")) {

            n = 9;

        }
        if (!snRT1.equals("A") && !snRT2.equals("A") && !snRT3.equals("A") && !snRT4.equals("A") && !snRT5.equals("A") &&
                !snRT6.equals("A") && !snRT7.equals("A") && !snRT8.equals("A") && !snRT9.equals("A") && !snRT10.equals("A")) {

            n = 10;

        }

        SaveProductInfo(ColorT1, ColorT2, ColorT3, ColorT4, ColorT5, ColorT6, ColorT7, ColorT8, ColorT9, ColorT10,
                SnRT1, SnRT2, SnRT3, SnRT4, SnRT5, SnRT6, SnRT7, SnRT8, SnRT9, SnRT10, o, n);

    }

    private void SaveProductInfo(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                                 String colorT8, String colorT9, String colorT10, String snRT1, String snRT2, String snRT3, String snRT4,
                                 String snRT5, String snRT6, String snRT7, String snRT8, String snRT9, String snRT10, int o, int n) {

        try {

            productRandomKey = PPPIIIDDD;

            HashMap<String, Object> productMap = new HashMap<>();

            productMap.put("TName", Table_Name);
            productMap.put("TDetail", Table_Detail);
            productMap.put("TBrand", Table_Brand);
            productMap.put("Keyward", ProductKeyward);
            productMap.put("Color1", colorT1);
            productMap.put("Color2", colorT2);
            productMap.put("Color3", colorT3);
            productMap.put("Color4", colorT4);
            productMap.put("Color5", colorT5);
            productMap.put("Color6", colorT6);
            productMap.put("Color7", colorT7);
            productMap.put("Color8", colorT8);
            productMap.put("Color9", colorT9);
            productMap.put("Color10", colorT10);
            productMap.put("SnR1", snRT1);
            productMap.put("SnR2", snRT2);
            productMap.put("SnR3", snRT3);
            productMap.put("SnR4", snRT4);
            productMap.put("SnR5", snRT5);
            productMap.put("SnR6", snRT6);
            productMap.put("SnR7", snRT7);
            productMap.put("SnR8", snRT8);
            productMap.put("SnR9", snRT9);
            productMap.put("SnR10", snRT10);
            productMap.put("NOCo", String.valueOf(o));
            productMap.put("NOSnR", String.valueOf(n));
            productMap.put("ProductName", ProductName);

            ProductRef.child(productRandomKey).updateChildren(productMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(AdminMaintainProductActivity.this, AdminMainActivity.class);
                                startActivity(i);
                                LoadingBar.dismiss();
                                Toast.makeText(AdminMaintainProductActivity.this, "Product is added successfully.......", Toast.LENGTH_LONG).show();
                            } else {
                                String massage = task.getException().toString();
                                Toast.makeText(AdminMaintainProductActivity.this, "Error...." + massage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
        catch (Exception e){
            Toast.makeText(AdminMaintainProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void SetDataEE(String pppiiiddd) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("ProductsPhones");

        productRef.child(pppiiiddd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    ProductPhone p = snapshot.getValue(ProductPhone.class);

                    Toast.makeText(AdminMaintainProductActivity.this, p.getProductName(), Toast.LENGTH_SHORT).show();

                    String Nameee = p.getProductName();
                    String TNameee = p.getTName();
                    String TDetaileee = p.getTDetail();
                    String TBrandeee = p.getTBrand();
                    String KeyWordeee = p.getKeyward();

                    String Coloreee1 = p.getColor1();
                    String Coloreee2 = p.getColor2();
                    String Coloreee3 = p.getColor3();
                    String Coloreee4 = p.getColor4();
                    String Coloreee5 = p.getColor5();
                    String Coloreee6 = p.getColor6();
                    String Coloreee7 = p.getColor7();
                    String Coloreee8 = p.getColor8();
                    String Coloreee9 = p.getColor9();
                    String Coloreee10 = p.getColor10();

                    String SnReee1 = p.getSnR1();
                    String SnReee2 = p.getSnR2();
                    String SnReee3 = p.getSnR3();
                    String SnReee4 = p.getSnR4();
                    String SnReee5 = p.getSnR5();
                    String SnReee6 = p.getSnR6();
                    String SnReee7 = p.getSnR7();
                    String SnReee8 = p.getSnR8();
                    String SnReee9 = p.getSnR9();
                    String SnReee10 = p.getSnR10();
                    String Image = p.getImage1();


                    productName.setText(Nameee, TextView.BufferType.EDITABLE);
                    productKeyeard.setText(KeyWordeee, TextView.BufferType.EDITABLE);
                    Picasso.get().load(Image).fit().centerCrop().into(picPhoto1);

                    Table_N.setText(TNameee, TextView.BufferType.EDITABLE);
                    Table_D.setText(TDetaileee, TextView.BufferType.EDITABLE);
                    Table_B.setText(TBrandeee, TextView.BufferType.EDITABLE);

                    Color1.setText(Coloreee1, TextView.BufferType.EDITABLE);
                    Color2.setText(Coloreee2, TextView.BufferType.EDITABLE);
                    Color3.setText(Coloreee3, TextView.BufferType.EDITABLE);
                    Color4.setText(Coloreee4, TextView.BufferType.EDITABLE);
                    Color5.setText(Coloreee5, TextView.BufferType.EDITABLE);
                    Color6.setText(Coloreee6, TextView.BufferType.EDITABLE);
                    Color7.setText(Coloreee7, TextView.BufferType.EDITABLE);
                    Color8.setText(Coloreee8, TextView.BufferType.EDITABLE);
                    Color9.setText(Coloreee9, TextView.BufferType.EDITABLE);
                    Color10.setText(Coloreee10, TextView.BufferType.EDITABLE);

                    SnR1.setText(SnReee1, TextView.BufferType.EDITABLE);
                    SnR2.setText(SnReee2, TextView.BufferType.EDITABLE);
                    SnR3.setText(SnReee3, TextView.BufferType.EDITABLE);
                    SnR4.setText(SnReee4, TextView.BufferType.EDITABLE);
                    SnR5.setText(SnReee5, TextView.BufferType.EDITABLE);
                    SnR6.setText(SnReee6, TextView.BufferType.EDITABLE);
                    SnR7.setText(SnReee7, TextView.BufferType.EDITABLE);
                    SnR8.setText(SnReee8, TextView.BufferType.EDITABLE);
                    SnR9.setText(SnReee9, TextView.BufferType.EDITABLE);
                    SnR10.setText(SnReee10, TextView.BufferType.EDITABLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}