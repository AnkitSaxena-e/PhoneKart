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

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView FiPhonb;
    private RecyclerView.LayoutManager layoutManagerFiPhonb;
    private String type, CCCC;
    private int h;
    private ImageView back;
    private ArrayList<Product> PList, QList, RList, FList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        type = Paper.book().read(Prevalant.CheckAdmin);
        CCCC = getIntent().getStringExtra("CCAATT");

        Prevalant.SuspendUser = new ArrayList<>();

        back = findViewById(R.id.back_sett_cat);

        FiPhonb = findViewById(R.id.reCattttLL);
        FiPhonb.setHasFixedSize(true);
        FiPhonb.setItemViewCacheSize(20);
        FiPhonb.setDrawingCacheEnabled(true);
        FiPhonb.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFiPhonb = new LinearLayoutManager(CategoryActivity.this);
        FiPhonb.setLayoutManager(layoutManagerFiPhonb);

        Window window = CategoryActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        TakeUser(CCCC);

        back.setOnClickListener(v -> {

            Intent i = new Intent(CategoryActivity.this, HomeActivity.class);
            i.putExtra("eeee", "CA");
            startActivity(i);

        });

    }



    public void TakeUser(String CCCC) {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("User");

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    try {

                        CId((Map<String, Object>) snapshot.getValue(), CCCC);

                    } catch (Exception e) {

                        Toast.makeText(CategoryActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CId(Map<String, Object> snapshot, String CCCC) {

        Prevalant.SuspendUser.clear();
        ArrayList<String> PriUser = new ArrayList<>();
        ArrayList<String> InPric = new ArrayList<>();

        for (Map.Entry<String, Object> en : snapshot.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("Suspend"));

            if (oo.equals("Suspended")) {

                String sss = String.valueOf(SPro.get("UserId"));

                Prevalant.SuspendUser.add(sss);
            }
        }

        TakePID(CCCC);

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

                        Toast.makeText(CategoryActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CPrice(Map<String, Object> snapshot, String uu) {

        ArrayList<Integer> Pric = new ArrayList<>();
        ArrayList<Integer> InPric = new ArrayList<>();

        for (Map.Entry<String, Object> en : snapshot.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("SnR"));

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

                        String h = PList.get(g).getSnR();

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

                    int pp = RList.size();

                    int z = Prevalant.SuspendUser.size();
                    if(z == 0){
                        Prevalant.SuspendUser.add("AAAA");
                    }

                    int q = Prevalant.SuspendUser.size();

                    for(int y = 0; y < pp; y++){

                        String as = PList.get(y).getSellerId();

                        for(int az = 0; az < q; az++){

                            String lo = Prevalant.SuspendUser.get(az);

                            if(lo.equals(as)){
                                h = 90;
                            }else{
                                FList.add(PList.get(y));
                            }

                        }

                    }

                    SearchAdapter adapter = new SearchAdapter(CategoryActivity.this, FList, type);

                    FiPhonb.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(CategoryActivity.this, HomeActivity.class);
        i.putExtra("eeee", "CA");
        startActivity(i);
    }

}