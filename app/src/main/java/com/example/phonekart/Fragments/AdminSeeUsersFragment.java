package com.example.phonekart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Adapter.AdminOrderAdapter;
import com.example.phonekart.Modal.Order;
import com.example.phonekart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminSeeUsersFragment extends Fragment {

    private RecyclerView recyclerViewPr;
    private RecyclerView.LayoutManager layoutManagerPr;
    private ArrayList<Order> PList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_see_user, container, false);

        recyclerViewPr = root.findViewById(R.id.RePrintShowLL);
        recyclerViewPr.setHasFixedSize(true);
        recyclerViewPr.setItemViewCacheSize(20);
        recyclerViewPr.setDrawingCacheEnabled(true);
        recyclerViewPr.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerPr = new LinearLayoutManager(getActivity());
        recyclerViewPr.setLayoutManager(layoutManagerPr);

        FStart();

        return root;
    }

    public void FStart() {

        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Order");

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    PList = new  ArrayList<>();

                    for(DataSnapshot ss : snapshot.getChildren()){
                        PList.add(ss.getValue(Order.class));
                    }

                    AdminOrderAdapter aa = new AdminOrderAdapter(getActivity(), PList);
                    recyclerViewPr.setAdapter(aa);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
