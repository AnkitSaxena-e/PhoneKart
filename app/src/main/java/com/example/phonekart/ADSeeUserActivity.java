package com.example.phonekart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.phonekart.Modal.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ADSeeUserActivity extends AppCompatActivity {

    private RecyclerView ReLayout;
    private DatabaseReference SearchRef;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> PList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsee_user);

        SearchRef = FirebaseDatabase.getInstance().getReference().child("Products");

        searchView = findViewById(R.id.search_view_adv_);

        ReLayout = findViewById(R.id.Rerererererer);
        ReLayout.setHasFixedSize(true);
        ReLayout.setItemViewCacheSize(20);
        ReLayout.setDrawingCacheEnabled(true);
        ReLayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager = new LinearLayoutManager(this);
        ReLayout.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!SearchRef.equals(null))

//        SearchRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if(dataSnapshot.exists()){
//
//                    PList = new ArrayList<>();
//                    for(DataSnapshot ss : dataSnapshot.getChildren()){
//                        PList.add(ss.getValue(Product.class));
//                    }
//
//                    CategoryAdapter adapter = new CategoryAdapter(PList);
//                    ReLayout.setAdapter(adapter);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        if(!searchView.equals(null)){

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        for(Product product : PList){
            if(product.getProductName().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
//        CategoryAdapter adapter = new CategoryAdapter(Newlist);
//        ReLayout.setAdapter(adapter);
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//
//        try {
//
//            final DatabaseReference AdSeeRef = FirebaseDatabase.getInstance().getReference().child("Ads");
//
//            FirebaseRecyclerOptions<AdModalClass> optionsad =
//                    new FirebaseRecyclerOptions.Builder<AdModalClass>()
//                            .setQuery(AdSeeRef, AdModalClass.class).build();
//
//            FirebaseRecyclerAdapter<AdModalClass, AdResourceViewHolder> adapterAd =
//                    new FirebaseRecyclerAdapter<AdModalClass, AdResourceViewHolder>(optionsad) {
//                        @Override
//                        protected void onBindViewHolder(@NonNull AdResourceViewHolder holder, final int j, @NonNull final AdModalClass adModalClass) {
//
//                            try {
//                                Picasso.get().load(adModalClass.getImage()).fit().centerCrop().into(holder.imageViewAd);
//                                String Link = adModalClass.getLink();
//
//                                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//
//                                        if(!Link.equals("AS")) {
//                                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(adModalClass.getLink()));
//                                            startActivity(i);
//                                        }
//                                        else {
//                                         finish();
//                                        }
//                                    }
//                                });
//                            }
//                            catch (Exception e){
//                                Toast.makeText(ADSeeUserActivity.this, "2"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @NonNull
//                        @Override
//                        public AdResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, parent, false);
//                            AdResourceViewHolder holder = new AdResourceViewHolder(view);
//                            return holder;
//                        }
//                    };
//
//            recyclerViewSeeAdU.setAdapter(adapterAd);
//            adapterAd.startListening();
//
//        }
//        catch (Exception e){
//            Toast.makeText(ADSeeUserActivity.this, "3"+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

}

//
//        try {
//            recyclerViewSeeAdU = findViewById(R.id.recycler_adsee_user);
//            recyclerViewSeeAdU.setHasFixedSize(true);
//            recyclerViewSeeAdU.setItemViewCacheSize(20);
//            recyclerViewSeeAdU.setDrawingCacheEnabled(true);
//            recyclerViewSeeAdU.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//            layoutManagerSeeAdU = new LinearLayoutManager(ADSeeUserActivity.this);
//            recyclerViewSeeAdU.setLayoutManager(layoutManagerSeeAdU);
//        }
//        catch (Exception e){
//            Toast.makeText(ADSeeUserActivity.this, "1"+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        onStart();