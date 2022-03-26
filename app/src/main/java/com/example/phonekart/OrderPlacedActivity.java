package com.example.phonekart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonekart.Prevalant.Prevalant;

import io.paperdb.Paper;

public class OrderPlacedActivity extends AppCompatActivity {

    private String ON;
    private TextView or, TH;
    private ImageView TTHH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        or = findViewById(R.id.orderId);
        TH = findViewById(R.id.ssssssss);
        TTHH = findViewById(R.id.contiImage);

        or.setText(ON);

        Window window = OrderPlacedActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        TH.setOnClickListener(v -> {

            Intent i  = new Intent(OrderPlacedActivity.this, HomeActivity.class);
            Paper.book().write(Prevalant.FAD, "HomeA");
            i.putExtra("eeee", "OPA");
            startActivity(i);

        });

        TTHH.setOnClickListener(v -> {

            Intent i  = new Intent(OrderPlacedActivity.this, HomeActivity.class);
            Paper.book().write(Prevalant.FAD, "HomeA");
            i.putExtra("eeee", "OPA");
            startActivity(i);

        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(OrderPlacedActivity.this, HomeActivity.class);
        Paper.book().write(Prevalant.FAD, "HomeA");
        i.putExtra("eeee", "HA");
        startActivity(i);
    }

}