package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.phonekart.Adapter.SellingAdapter;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AdminSeeUserAddedPhoneActivity extends AppCompatActivity {

    private SearchView searchViewAS;
    private DatabaseReference SearchRef;
    private RecyclerView recyclerViewAS;
    private RecyclerView.LayoutManager layoutManagerSS;
    private ArrayList<Product> PList, FList;
    private String Input_Search, Name, admin, Order, Check, Print = "", Type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_user_added_phone);

        Check = Paper.book().read(Prevalant.ACAdmin);

        Toast.makeText(this, Check, Toast.LENGTH_SHORT).show();

        searchViewAS = findViewById(R.id.search_view_see_user_phones);
        recyclerViewAS = findViewById(R.id.See_User_Product_Search);

        recyclerViewAS.setHasFixedSize(true);
        recyclerViewAS.setItemViewCacheSize(20);
        recyclerViewAS.setDrawingCacheEnabled(true);
        recyclerViewAS.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSS = new LinearLayoutManager(AdminSeeUserAddedPhoneActivity.this);
        recyclerViewAS.setLayoutManager(layoutManagerSS);

        Paper.init(AdminSeeUserAddedPhoneActivity.this);

        SearchRef = FirebaseDatabase.getInstance().getReference().child("Products");

    }


    @Override
    public void onStart() {
        super.onStart();

        if(!SearchRef.equals(null))

            SearchRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){

                        PList = new ArrayList<>();
                        FList = new ArrayList<>();

                        for(DataSnapshot ss : dataSnapshot.getChildren()){
                            PList.add(ss.getValue(Product.class));
                        }

                        for (int g = 0; g < PList.size(); g++) {

                            String h = PList.get(g).getSellerId().toString();

                            if (h.equals(Check)) {
                                FList.add(PList.get(g));
                            }

                        }

                        SellingAdapter adapter = new SellingAdapter(AdminSeeUserAddedPhoneActivity.this, FList, "LOLO");
                        recyclerViewAS.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        if(!searchViewAS.equals(null)){

            searchViewAS.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchIt(newText);
                    return false;
                }
            });
        }
    }

    private void searchIt(String newText) {

        ArrayList<Product> Newlist = new ArrayList<>();

        for(Product product : FList){
            if(product.getKeyward().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
        SellingAdapter adapter = new SellingAdapter(AdminSeeUserAddedPhoneActivity.this, Newlist, "LOLO");
        recyclerViewAS.setAdapter(adapter);

    }

}