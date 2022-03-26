package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonekart.Adapter.ProductImageSlider;
import com.example.phonekart.Modal.ProductPhone;
import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class AddProductDataActivity extends AppCompatActivity {

    private String PIDD, SeleColor, SeleSnR;
    private TextView productName;
    private float Total_S;
    private String[] urls;
    private EditText SEPr, SESt;
    private static ViewPager mPager;
    private CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private Spinner ColorSp, SnRSp;
    private String Image1;
    private Dialog LoadingBar;
    private ImageView back;
    private TextView AddPro;
    private ArrayList<String> spC, spSR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_data);

        try {

            PIDD = getIntent().getStringExtra("pid");

            productName = findViewById(R.id.Product_Name_dataA);

            SEPr = findViewById(R.id.SPriceAA);
            SESt = findViewById(R.id.SStockAA);

            ColorSp = findViewById(R.id.colorSpinner);
            SnRSp = findViewById(R.id.snrSpinner);

            mPager = findViewById(R.id.viewPagerShow_Add_P_Data);

            indicator = findViewById(R.id.indicator_Add_P_Data);
            AddPro = findViewById(R.id.SubButtAdd);

            back = findViewById(R.id.back_sett_add_pro);

            LoadingBar = new Dialog(AddProductDataActivity.this);
            LoadingBar.setContentView(R.layout.loading_dialog);
            LoadingBar.setCancelable(false);

            spC = new ArrayList<>();
            spSR = new ArrayList<>();

            getProductData(PIDD);

            Window window = AddProductDataActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(android.graphics.Color.parseColor("#303F9F"));

            ColorSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SeleColor = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            SnRSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SeleSnR = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ColorSp.setSelection(1);
            SnRSp.setSelection(1);

            AddPro.setOnClickListener(v -> {

                String Colo = SeleColor;
                String SnRa = SeleSnR;
                String SelPri = SEPr.getText().toString();
                String SelQua = SESt.getText().toString();

                LoadingBar.show();

                if (TextUtils.isEmpty(SelPri)) {
                    SEPr.setError("Please Enter");
                }
                if (TextUtils.isEmpty(SelQua)) {
                    SESt.setError("Please Enter");
                } else {
                    AdProduDa(Colo, SnRa, SelPri, PIDD, SelQua);
                }
            });

            back.setOnClickListener(v -> {

                Intent i = new Intent(AddProductDataActivity.this, HomeActivity.class);
                i.putExtra("eeee", "APA");
                startActivity(i);

            });

        } catch (Exception e) {
            Toast.makeText(this, "jj" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void AdProduDa(String colo, String snRa, String selPri, String PIDD, String selQua) {

        DatabaseReference productRefRe = FirebaseDatabase.getInstance().getReference().child("ProductsPhones");

        productRefRe.child(PIDD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    ProductPhone productPhone = snapshot.getValue(ProductPhone.class);

                    DatabaseReference PPRRef = FirebaseDatabase.getInstance().getReference().child("Products");

                    Calendar calendar = Calendar.getInstance();

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
                    String storeCurruntDate = currentDate.format(calendar.getTime());

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
                    String storeCurruntTime = currentTime.format(calendar.getTime());

                    String productRandomKey = storeCurruntDate + storeCurruntTime;

                    HashMap<String, Object> productMapAA = new HashMap<>();

                    assert productPhone != null;
                    productMapAA.put("Pid", productRandomKey);
                    productMapAA.put("Date", storeCurruntDate);
                    productMapAA.put("Time", storeCurruntTime);
                    productMapAA.put("Price", selPri);
                    productMapAA.put("TName", productPhone.getTName());
                    productMapAA.put("TDetail", productPhone.getTDetail());
                    productMapAA.put("TBrand", productPhone.getTBrand());
                    productMapAA.put("Keyward", productPhone.getKeyward() + " " + colo + " " + snRa + " " + selPri);
                    productMapAA.put("Color", colo);
                    productMapAA.put("Quantity", selQua);
                    productMapAA.put("SearchP", productPhone.getSearchP());
                    productMapAA.put("SellerId", Paper.book().read(Prevalant.UserIdA));
                    productMapAA.put("SnR", snRa);
                    productMapAA.put("Image1", productPhone.getImage1());
                    productMapAA.put("ProductName", productPhone.getProductName());

                    PPRRef.child(productRandomKey).updateChildren(productMapAA).addOnCompleteListener(task -> {

                        Intent i = new Intent(AddProductDataActivity.this, HomeActivity.class);
                        i.putExtra("eeee", "lolololo");
                        startActivity(i);
                        LoadingBar.dismiss();
                        Toast.makeText(AddProductDataActivity.this, "Your Product is added successfully.......", Toast.LENGTH_LONG).show();

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getProductData(String productId) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("ProductsPhones");

        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String gggggg = Objects.requireNonNull(dataSnapshot.child("ProductName").getValue()).toString();
                    productName.setText(gggggg);

                    Image1 = Objects.requireNonNull(dataSnapshot.child("Image1").getValue()).toString();

                    String cheC = Objects.requireNonNull(dataSnapshot.child("NOCo").getValue()).toString();
                    String cheSR = Objects.requireNonNull(dataSnapshot.child("NOSnR").getValue()).toString();

                    String Color1 = Objects.requireNonNull(dataSnapshot.child("Color1").getValue()).toString();
                    String Color2 = Objects.requireNonNull(dataSnapshot.child("Color2").getValue()).toString();
                    String Color3 = Objects.requireNonNull(dataSnapshot.child("Color3").getValue()).toString();
                    String Color4 = Objects.requireNonNull(dataSnapshot.child("Color4").getValue()).toString();
                    String Color5 = Objects.requireNonNull(dataSnapshot.child("Color5").getValue()).toString();
                    String Color6 = Objects.requireNonNull(dataSnapshot.child("Color6").getValue()).toString();
                    String Color7 = Objects.requireNonNull(dataSnapshot.child("Color7").getValue()).toString();
                    String Color8 = Objects.requireNonNull(dataSnapshot.child("Color8").getValue()).toString();
                    String Color9 = Objects.requireNonNull(dataSnapshot.child("Color9").getValue()).toString();
                    String Color10 = Objects.requireNonNull(dataSnapshot.child("Color10").getValue()).toString();

                    String SnR1 = Objects.requireNonNull(dataSnapshot.child("SnR1").getValue()).toString();
                    String SnR2 = Objects.requireNonNull(dataSnapshot.child("SnR2").getValue()).toString();
                    String SnR3 = Objects.requireNonNull(dataSnapshot.child("SnR3").getValue()).toString();
                    String SnR4 = Objects.requireNonNull(dataSnapshot.child("SnR4").getValue()).toString();
                    String SnR5 = Objects.requireNonNull(dataSnapshot.child("SnR5").getValue()).toString();
                    String SnR6 = Objects.requireNonNull(dataSnapshot.child("SnR6").getValue()).toString();
                    String SnR7 = Objects.requireNonNull(dataSnapshot.child("SnR7").getValue()).toString();
                    String SnR8 = Objects.requireNonNull(dataSnapshot.child("SnR8").getValue()).toString();
                    String SnR9 = Objects.requireNonNull(dataSnapshot.child("SnR9").getValue()).toString();
                    String SnR10 = Objects.requireNonNull(dataSnapshot.child("SnR10").getValue()).toString();

                    urls = new String[]{
                            Image1
                    };

                    Flip();

                        int IcheC = Integer.parseInt(cheC);
                        int IcheSR = Integer.parseInt(cheSR);


                        if (IcheC >= 1) {
                            spC.add(Color1);
                        }
                        if (IcheC >= 2) {
                            spC.add(Color2);
                        }
                        if (IcheC >= 3) {
                            spC.add(Color3);
                        }
                        if (IcheC >= 4) {
                            spC.add(Color4);
                        }
                        if (IcheC >= 5) {
                            spC.add(Color5);
                        }
                        if (IcheC >= 6) {
                            spC.add(Color6);
                        }
                        if (IcheC >= 7) {
                            spC.add(Color7);
                        }
                        if (IcheC >= 8) {
                            spC.add(Color8);
                        }
                        if (IcheC >= 9) {
                            spC.add(Color9);
                        }
                        if (IcheC >= 10) {
                            spC.add(Color10);
                        }


                        if (IcheSR >= 1) {
                            spSR.add(SnR1);
                        }
                        if (IcheSR >= 2) {
                            spSR.add(SnR2);
                        }
                        if (IcheSR >= 3) {
                            spSR.add(SnR3);
                        }
                        if (IcheSR >= 4) {
                            spSR.add(SnR4);
                        }
                        if (IcheSR >= 5) {
                            spSR.add(SnR5);
                        }
                        if (IcheSR >= 6) {
                            spSR.add(SnR6);
                        }
                        if (IcheSR >= 7) {
                            spSR.add(SnR7);
                        }
                        if (IcheSR >= 8) {
                            spSR.add(SnR8);
                        }
                        if (IcheSR >= 9) {
                            spSR.add(SnR9);
                        }
                        if (IcheSR >= 10) {
                            spSR.add(SnR10);
                        }

                        ArrayAdapter<String> arrayC = new ArrayAdapter<>(AddProductDataActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, spC);

                        ArrayAdapter<String> arraySR = new ArrayAdapter<>(AddProductDataActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, spSR);

                        arrayC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        arraySR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        ColorSp.setAdapter(arrayC);
                        SnRSp.setAdapter(arraySR);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Flip() {

        try {
            mPager.setAdapter(new ProductImageSlider(AddProductDataActivity.this, urls));

            indicator.setViewPager(mPager);

            final float density = getResources().getDisplayMetrics().density;

            indicator.setRadius(5 * density);

            NUM_PAGES = urls.length;

            final Handler handler = new Handler();
            final Runnable Update = () -> {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 6000, 3000);

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(AddProductDataActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddProductDataActivity.this, HomeActivity.class);
        i.putExtra("eeee", "APA");
        startActivity(i);
    }

}