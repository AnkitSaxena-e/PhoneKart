package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phonekart.Modal.AdModalClass;
import com.example.phonekart.View_Holder.AdResourceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ADSeeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSeeAd;
    private RecyclerView.LayoutManager layoutManagerSeeAd;
    private String AdAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsee);

        try {

            recyclerViewSeeAd = findViewById(R.id.recycler_adsee);
            recyclerViewSeeAd.setHasFixedSize(true);
            recyclerViewSeeAd.setItemViewCacheSize(20);
            recyclerViewSeeAd.setDrawingCacheEnabled(true);
            recyclerViewSeeAd.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            layoutManagerSeeAd = new LinearLayoutManager(ADSeeActivity.this);
            recyclerViewSeeAd.setLayoutManager(layoutManagerSeeAd);
        }
        catch (Exception e){
            Toast.makeText(ADSeeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        onStart();

    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference AdSeeRef = FirebaseDatabase.getInstance().getReference().child("Ads");

        FirebaseRecyclerOptions<AdModalClass> optionsad =
                new FirebaseRecyclerOptions.Builder<AdModalClass>()
                .setQuery(AdSeeRef, AdModalClass.class).build();

        FirebaseRecyclerAdapter<AdModalClass, AdResourceViewHolder> adapterAd =
                new FirebaseRecyclerAdapter<AdModalClass, AdResourceViewHolder>(optionsad) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdResourceViewHolder holder, final int j, @NonNull final AdModalClass adModalClass) {

                        Picasso.get().load(adModalClass.getImage()).fit().centerCrop().into(holder.imageViewAd);

                        holder.itemView.setOnClickListener(v -> {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(ADSeeActivity.this);
                                builder.setTitle("Remove Ad");

                                builder.setItems(options, (dialog, i) -> {

                                    if (i == 0) {
                                        String UID = getRef(j).getKey();

                                        AdSeeRef.child(UID).removeValue();
                                    }

                                    if (i == 1) {
                                        finish();
                                    }
                                });
                        });
                    }

                    @NonNull
                    @Override
                    public AdResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, parent, false);
                        AdResourceViewHolder holder = new AdResourceViewHolder(view);
                        return holder;
                    }
                };

        recyclerViewSeeAd.setAdapter(adapterAd);
        adapterAd.startListening();
    }
}
