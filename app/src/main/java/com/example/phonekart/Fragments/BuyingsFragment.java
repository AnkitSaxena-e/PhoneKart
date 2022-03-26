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

import com.example.phonekart.Adapter.BuyingsAdapter;
import com.example.phonekart.Modal.Order;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class BuyingsFragment extends Fragment {

    private SearchView searchViewBuyings;
    private DatabaseReference SearchRefB;
    private RecyclerView recyclerViewBuyings;
    private RecyclerView.LayoutManager layoutManagerBBBB;
    private ArrayList<Order> PList, FList;
    private String Input_Search, Name, admin, Order, Check, Print = "", Type = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buyings, container, false);

        searchViewBuyings = root.findViewById(R.id.search_view_buying);
        recyclerViewBuyings = root.findViewById(R.id.Buying_Search);

        recyclerViewBuyings.setHasFixedSize(true);
        recyclerViewBuyings.setItemViewCacheSize(20);
        recyclerViewBuyings.setDrawingCacheEnabled(true);
        recyclerViewBuyings.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerBBBB = new LinearLayoutManager(getActivity());
        recyclerViewBuyings.setLayoutManager(layoutManagerBBBB);

        Paper.book().write(Prevalant.FAD, "BuyingA");

        SearchRefB = FirebaseDatabase.getInstance().getReference().child("Order");

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();

        if(SearchRefB != null)

            SearchRefB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){

                        PList = new ArrayList<>();
                        FList = new ArrayList<>();
                        for(DataSnapshot ss : dataSnapshot.getChildren()){
                            PList.add(ss.getValue(Order.class));
                        }

                        for (int g = 0; g < PList.size(); g++) {

                            String h = PList.get(g).getToUser();

                            if (h.equals(Paper.book().read(Prevalant.UserIdA))) {
                                FList.add(PList.get(g));
                            }

                        }

                        BuyingsAdapter adapter = new BuyingsAdapter(getActivity(), FList, Check, Order);
                        recyclerViewBuyings.setAdapter(adapter);

                        if(searchViewBuyings != null){

                            searchViewBuyings.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        ArrayList<Order> Newlist = new ArrayList<>();

        for(Order product : FList){
            if(product.getKeyWord().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
        BuyingsAdapter adapter = new BuyingsAdapter(getActivity(), Newlist, Check, Order);
        recyclerViewBuyings.setAdapter(adapter);
    }

}
