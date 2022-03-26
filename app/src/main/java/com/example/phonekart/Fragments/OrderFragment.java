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

import com.example.phonekart.Adapter.OrderAdapter;
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

public class OrderFragment extends Fragment {

    private SearchView S_PhoneS;
    private RecyclerView R_PhoneS;
    private RecyclerView.LayoutManager layoutManagerSS;
    private DatabaseReference SearchRef;

    private String Admin;
    private ArrayList<Order> PList, FList;
    private String Input_Search, Name, admin, Order, Check, Print = "", Type = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        Paper.init(requireActivity());

        S_PhoneS = root.findViewById(R.id.search_view_order);
        R_PhoneS = root.findViewById(R.id.Order_Searchoo);

        R_PhoneS.setHasFixedSize(true);
        R_PhoneS.setItemViewCacheSize(20);
        R_PhoneS.setDrawingCacheEnabled(true);
        R_PhoneS.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSS = new LinearLayoutManager(getActivity());
        R_PhoneS.setLayoutManager(layoutManagerSS);

        Paper.book().write(Prevalant.FAD, "OrderA");

        SearchRef = FirebaseDatabase.getInstance().getReference().child("Order");
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
                            PList.add(ss.getValue(Order.class));
                        }

                        for (int g = 0; g < PList.size(); g++) {

                            String h = PList.get(g).getFromUser();

                            if (h.equals(Paper.book().read(Prevalant.UserIdA))) {
                                FList.add(PList.get(g));
                            }

                        }

                        OrderAdapter adapter = new OrderAdapter(getActivity(), FList);
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

        ArrayList<Order> Newlist = new ArrayList<>();

        for(Order product : FList){
            if(product.getKeyWord().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
        OrderAdapter adapter = new OrderAdapter(getActivity(), Newlist);
        R_PhoneS.setAdapter(adapter);

    }

}
