package com.example.phonekart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Adapter.CategoryAdapter;
import com.example.phonekart.Modal.ProductPhone;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AddPhoneFragment extends Fragment {

    private SearchView S_Phone;
    private RecyclerView R_Phone;
    private DatabaseReference SearchRef;
    private String Admin;
    private ArrayList<ProductPhone> PList;
    private RecyclerView.LayoutManager lm;
    String Input_Search, Name, admin, Order, Check, Print = "", Type = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_phone, container, false);

        S_Phone = root.findViewById(R.id.search_view_phone_add);

        R_Phone = root.findViewById(R.id.AddPhon_Search);
        R_Phone.setHasFixedSize(true);
        R_Phone.setItemViewCacheSize(20);
        R_Phone.setDrawingCacheEnabled(true);
        R_Phone.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        lm = new LinearLayoutManager(getActivity());
        R_Phone.setLayoutManager(lm);
        Paper.book().write(Prevalant.FAD, "AddPhoneA");

        SearchRef = FirebaseDatabase.getInstance().getReference().child("ProductsPhones");
        Check =  Paper.book().read(Prevalant.CheckAdmin).toString();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(SearchRef != null) {

            SearchRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();
                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(ProductPhone.class));
                        }

                        CategoryAdapter adapter = new CategoryAdapter(getActivity(), PList, Check);
                        R_Phone.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(S_Phone != null){

            S_Phone.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        ArrayList<ProductPhone> Newlist = new ArrayList<>();

        for(ProductPhone product : PList){
            if(product.getKeyward().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), Newlist, "Admin");
        R_Phone.setAdapter(adapter);
    }

}








