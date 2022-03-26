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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonekart.Adapter.ProductImageSlider;
import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class EditProductActivity extends AppCompatActivity {

    private String PIDD, SeleColor, SeleSnR, PPrice, PQuantity, CHE;
    private TextView productName, SnR, Color, Price, Stock;
    private static ViewPager mPager;
    private CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private int s1, s2, s3, s4, s5;
    private String[] urls;
    private String Image1;
    private float Total_S;
    private TextView TDRating, TNRating;
    private EditText SEPr, SEQu;
    private Spinner ColorSp, SnRSp;
    private Dialog LoadingBar;
    private Button AddPro;
    private ArrayList<String> spC, spSR;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Paper.init(EditProductActivity.this);

        PIDD = getIntent().getStringExtra("pihd");
        CHE = getIntent().getStringExtra("Admin");

        back = findViewById(R.id.back_sett_edit_pro);
        productName = findViewById(R.id.Product_Name_dataA_CC);
        SnR = findViewById(R.id.snrSpinner_CCJ);
        Color = findViewById(R.id.colorSpinner_CCJ);
        Price = findViewById(R.id.Price_CCJ);
        Stock = findViewById(R.id.Stock_CCJ);

        mPager = findViewById(R.id.viewPagerShow_Edit_Pro);

        indicator = findViewById(R.id.indicator_Edit_Pro);

        SEPr = findViewById(R.id.EditPriceAA_CCJ);
        SEQu = findViewById(R.id.EditStockAA_CCJ);

        AddPro = findViewById(R.id.SubButtAddJ);

        LoadingBar = new Dialog(EditProductActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        Window window = EditProductActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        getProductData(PIDD);

        AddPro.setOnClickListener(v -> {

            String SelPri = SEPr.getText().toString();
            String SelQut = SEQu.getText().toString();

            LoadingBar.show();

            if(TextUtils.isEmpty(SelPri)){

                SelPri = PPrice;

            }

            if(TextUtils.isEmpty(SelQut)){

                SelQut = PQuantity;

            }

            AdProduDa(PIDD, SelPri, SelQut);

        });

        back.setOnClickListener(v -> {

            String AA = Paper.book().read(Prevalant.AC);

            if(CHE.equals("Admin")){
                Intent i = new Intent(EditProductActivity.this, AdminUserInfoDetailActivity.class);
                startActivity(i);
            }else{
                Intent i = new Intent(EditProductActivity.this, HomeActivity.class);
                i.putExtra("eeee", "EPA");
                startActivity(i);
            }

        });

    }

    private void AdProduDa(String PIDD, String selPri, String selQut) {

        DatabaseReference productRefRe = FirebaseDatabase.getInstance().getReference().child("Products");


            HashMap<String, Object> productMapAA = new HashMap<>();
            productMapAA.put("Price", selPri);
            productMapAA.put("Quantity", selQut);

            productRefRe.child(PIDD).updateChildren(productMapAA).addOnCompleteListener(task -> {

                LoadingBar.dismiss();
                Toast.makeText(EditProductActivity.this,"Updated successfully.......",Toast.LENGTH_LONG).show();

                if(CHE.equals("Admin")){
                    Intent i = new Intent(EditProductActivity.this, AdminMainActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(EditProductActivity.this, HomeActivity.class);
                    i.putExtra("eeee", "EPA");
                    startActivity(i);
                }
            });

    }

    private void getProductData(String productId) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    String gggggg = Objects.requireNonNull(dataSnapshot.child("ProductName").getValue()).toString();
                    productName.setText(gggggg);

                    Image1 = Objects.requireNonNull(dataSnapshot.child("Image1").getValue()).toString();


                    String Pric = Objects.requireNonNull(dataSnapshot.child("Price").getValue()).toString();

                    String Col = Objects.requireNonNull(dataSnapshot.child("Color").getValue()).toString();

                    String SR = Objects.requireNonNull(dataSnapshot.child("SnR").getValue()).toString();

                    String SellerId = Objects.requireNonNull(dataSnapshot.child("SellerId").getValue()).toString();

                    String Qut = Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString();

                    urls = new String[]{
                            Image1
                    };

                    Flip();

                    try {

                        SnR.setText(SR);
                        Color.setText(Col);
                        Price.setText("₹" +Pric);
                        Stock.setText("Qut: " + Qut);

                        PPrice = Pric;
                        PQuantity = Qut;

                    }catch (Exception e){
                        Toast.makeText(EditProductActivity.this, "loloolol" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Flip() {

        try {

            mPager.setAdapter(new ProductImageSlider(EditProductActivity.this, urls));

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
            Toast.makeText(EditProductActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        String AA = Paper.book().read(Prevalant.AC);

        if(CHE.equals("Admin")){
            Intent i = new Intent(EditProductActivity.this, AdminUserInfoDetailActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(EditProductActivity.this, HomeActivity.class);
            i.putExtra("eeee", "EPA");
            startActivity(i);
        }
    }

}