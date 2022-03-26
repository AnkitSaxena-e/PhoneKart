package com.example.phonekart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Adapter.SellingAdapter;
import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SellingsFragment extends Fragment {

    private SearchView S_PhoneS;
    private RecyclerView R_PhoneS;
    private RecyclerView.LayoutManager layoutManagerSS;
    private DatabaseReference SearchRef;
    private int chasma;
    private String Admin;
    private ArrayList<Product> PList, FList;
    private String Input_Search, Name, admin, Order, Check, Print = "", Type = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sellings, container, false);

        Paper.init(requireActivity());

        S_PhoneS = root.findViewById(R.id.search_view_selling);
        R_PhoneS = root.findViewById(R.id.Selling_Search);

        R_PhoneS.setHasFixedSize(true);
        R_PhoneS.setItemViewCacheSize(20);
        R_PhoneS.setDrawingCacheEnabled(true);
        R_PhoneS.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSS = new LinearLayoutManager(getActivity());
        R_PhoneS.setLayoutManager(layoutManagerSS);

        Paper.book().write(Prevalant.FAD, "SellingA");

        SearchRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Check =  Paper.book().read(Prevalant.CheckAdmin).toString();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(SearchRef != null)

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

                            String h = PList.get(g).getSellerId();

                            if (h.equals(Paper.book().read(Prevalant.UserIdA))) {
                                FList.add(PList.get(g));
                            }

                        }

                        SellingAdapter adapter = new SellingAdapter(getActivity(), FList, Check);
                        R_PhoneS.setAdapter(adapter);

                        if(S_PhoneS != null){

                            S_PhoneS.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void searchIt(String newText) {

        ArrayList<Product> Newlist = new ArrayList<>();

        for(Product product : FList){
            if(product.getKeyward().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }

        SellingAdapter adapter = new SellingAdapter(getActivity(), Newlist, Check);
        R_PhoneS.setAdapter(adapter);

    }

}
