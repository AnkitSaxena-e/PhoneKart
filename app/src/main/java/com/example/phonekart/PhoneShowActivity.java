package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phonekart.Adapter.SearchAdapter;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import io.paperdb.Paper;

public class PhoneShowActivity extends AppCompatActivity {

    private RecyclerView FiPho;
    private RecyclerView.LayoutManager layoutManagerFiPho;
    private String type, CCCC;
    private ArrayList<Product> PList, QList, RList,FList;
    private int a = 0;
    private ImageView BackB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_show);

        type = Paper.book().read(Prevalant.CheckAdmin);
        CCCC = getIntent().getStringExtra("category");
        BackB = findViewById(R.id.back_sett_phone_show);

        Prevalant.SuspendUser = new ArrayList<>();

        Window window = PhoneShowActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        FiPho = findViewById(R.id.Show_Phone_reC);
        FiPho.setHasFixedSize(true);
        FiPho.setItemViewCacheSize(20);
        FiPho.setDrawingCacheEnabled(true);
        FiPho.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFiPho = new LinearLayoutManager(PhoneShowActivity.this);
        FiPho.setLayoutManager(layoutManagerFiPho);

        TakeUser();

        TakePID(CCCC);

        BackB.setOnClickListener(v -> {

            Intent i = new Intent(PhoneShowActivity.this, HomeActivity.class);
            i.putExtra("eeee", "HA");
            startActivity(i);

        });

    }

    public void TakeUser() {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("User");

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    try {

                        CId((Map<String, Object>) snapshot.getValue());

                    } catch (Exception e) {

                        Toast.makeText(PhoneShowActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CId(Map<String, Object> snapshot) {

        ArrayList<String> PriUser = new ArrayList<>();
        ArrayList<String> InPric = new ArrayList<>();

        for (Map.Entry<String, Object> en : snapshot.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("Suspend"));

            if (oo.equals("Suspended")) {

                String sss = String.valueOf(SPro.get("UserId"));

                Prevalant.SuspendUser.add(String.valueOf(sss));
            }
        }

        Toast.makeText(PhoneShowActivity.this, "S Size - " + Prevalant.SuspendUser.size(), Toast.LENGTH_SHORT).show();

    }

    public void TakePID(String UU) {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("Products");

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    try {

                        CPrice((Map<String, Object>) snapshot.getValue(), UU);

                    } catch (Exception e) {

                        Toast.makeText(PhoneShowActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CPrice(Map<String, Object> snapshot, String uu) {

        Prevalant.SuspendUser.clear();
        ArrayList<Integer> Pric = new ArrayList<>();
        ArrayList<Integer> InPric = new ArrayList<>();

        for (Map.Entry<String, Object> en : snapshot.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("ProductName"));

            if (oo.equals(uu)) {

                String sss = String.valueOf(SPro.get("Price"));

                Pric.add(Integer.parseInt(sss));
            }
        }

        LowHigh(Pric, uu);

    }

    private void LowHigh(ArrayList<Integer> priceFi, String gg) {

        Collections.sort(priceFi);

        GetListfi(priceFi, gg);

    }

    private void GetListfi(ArrayList<Integer> fPriceFi, String j) {

        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        ProductRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    PList = new ArrayList<>();
                    QList = new ArrayList<>();
                    RList = new ArrayList<>();
                    FList = new ArrayList<>();

                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        PList.add(ss.getValue(Product.class));
                    }

                    for (int g = 0; g < PList.size(); g++) {

                        String h = PList.get(g).getProductName().toString();

                        if (h.equals(j)) {
                            QList.add(PList.get(g));
                        }

                    }

                    for (int t = 0; t < fPriceFi.size(); t++) {

                        for (int s = 0; s < QList.size(); s++) {

                            String ll = QList.get(s).getPrice();
                            String ff = String.valueOf(fPriceFi.get(t));

                            if (ll.equals(ff)) {

                                RList.add(QList.get(s));

                            }

                        }

                    }

                    int oo = RList.size();

                    int z = Prevalant.SuspendUser.size();
                    if(z == 0){
                        Prevalant.SuspendUser.add("AAAA");
                    }

                    for(int tt = 0; tt < oo; tt++){

                        String as = RList.get(tt).getSellerId();

                        int zs = Prevalant.SuspendUser.size();

                        for(int az = 0; az < zs; az++){

                            String lo = Prevalant.SuspendUser.get(az);

                            if(lo.equals(as)){
                                a = 90;
                            }else{
                                FList.add(RList.get(tt));
                            }

                        }

                    }

                        SearchAdapter adapter = new SearchAdapter(PhoneShowActivity.this, FList, type);

                        FiPho.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PhoneShowActivity.this, HomeActivity.class);
        Paper.book().write(Prevalant.FAD, "HomeA");
        i.putExtra("eeee", "HA");
        startActivity(i);
    }

}