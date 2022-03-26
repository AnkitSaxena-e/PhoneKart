package com.example.phonekart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Adapter.SearchAdapter;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import io.paperdb.Paper;

public class GalleryFragment extends Fragment {

    private SearchView searchViewA;
    private RecyclerView ReSear;
    private ArrayList<Product> PList, FList;
    private String Check;
    private int j = 0;
    private RecyclerView.LayoutManager layoutManagerSer;
    private DatabaseReference refgty;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        searchViewA = root.findViewById(R.id.search_view_search);

        ReSear = root.findViewById(R.id.Search_Search);
        ReSear.setHasFixedSize(true);
        ReSear.setItemViewCacheSize(20);
        ReSear.setDrawingCacheEnabled(true);
        ReSear.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSer = new LinearLayoutManager(getActivity());
        ReSear.setLayoutManager(layoutManagerSer);

        TakeUser();

        Prevalant.SuspendUser = new ArrayList<>();

        Paper.book().write(Prevalant.FAD, "GalleryA");

        Check = Paper.book().read(Prevalant.CheckAdmin);

        return root;
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

                        Toast.makeText(getActivity(), "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CId(Map<String, Object> snapshot) {

        Prevalant.SuspendUser.clear();

        for (Map.Entry<String, Object> en : snapshot.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("Suspend"));

            if (oo.equals("Suspended")) {

                String sss = String.valueOf(SPro.get("UserId"));

                Prevalant.SuspendUser.add(sss);
            }
        }

        Toast.makeText(getActivity(), "l.o " + Prevalant.SuspendUser.size(), Toast.LENGTH_SHORT).show();

        ShowPP();

    }

    private void ShowPP() {

        refgty = FirebaseDatabase.getInstance().getReference().child("Products");

        refgty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    PList = new ArrayList<>();
                    FList = new ArrayList<>();

                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        PList.add(ss.getValue(Product.class));
                    }

                    int pp = PList.size();

                    int z = Prevalant.SuspendUser.size();

                    if(z ==  0){
                        Prevalant.SuspendUser.add("AAAA");
                    }

                    for (int y = 0; y < pp; y++) {

                        String as = PList.get(y).getSellerId();

                        for (int az = 0; az < z; az++) {

                            String lo = Prevalant.SuspendUser.get(az);

                            if (lo.equals(as)) {
                                j = 90;
                            } else {
                                FList.add(PList.get(y));
                            }

                        }

                    }

                    SearchAdapter adapter = new SearchAdapter(getActivity(), FList, Check);
                    ReSear.setAdapter(adapter);

                    if (searchViewA != null) {

                        searchViewA.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        TakeUser();

    }

    private void searchIt(String newText) {

        ArrayList<Product> Newlist = new ArrayList<>();

        for(Product product : FList){
            if(product.getKeyward().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
        SearchAdapter adapter = new SearchAdapter(getActivity(), Newlist, Check);
        ReSear.setAdapter(adapter);
    }

}