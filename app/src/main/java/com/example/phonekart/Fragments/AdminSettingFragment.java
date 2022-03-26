package com.example.phonekart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Adapter.AdminUserReAdapter;
import com.example.phonekart.Modal.Users;
import com.example.phonekart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminSettingFragment extends Fragment {

    private RecyclerView UserReee;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference UserDA;
    private ArrayList<Users> PListAA;
    private SearchView ANumberEdit, ANameEdit, ASNameEdit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting_admin, container, false);

            ANumberEdit = root.findViewById(R.id.numberSearchAdmin_PPPP);
            ANameEdit = root.findViewById(R.id.nameSearchAdmin_PPPP);
            ASNameEdit = root.findViewById(R.id.shopSearchAdmin_PPPP);

            UserReee = root.findViewById(R.id.AdminUserData_PPPPpp);
            UserReee.setHasFixedSize(true);
            UserReee.setItemViewCacheSize(20);
            UserReee.setDrawingCacheEnabled(true);
            UserReee.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            UserReee.setLayoutManager(layoutManager);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        try {

            UserDA = FirebaseDatabase.getInstance().getReference().child("User");

            UserDA.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PListAA = new ArrayList<>();

                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PListAA.add(ss.getValue(Users.class));
                        }

                        AdminUserReAdapter adapter = new AdminUserReAdapter(getActivity(), PListAA);
                        UserReee.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if (ANumberEdit != null) {

                ANumberEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newTextNum) {
                        searchItNumber(newTextNum);
                        return false;
                    }
                });
            }

            if (ANameEdit != null) {

                ANameEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newTextNa) {
                        searchItName(newTextNa);
                        return false;
                    }
                });
            }

            if (ASNameEdit != null) {

                ASNameEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newTextSN) {
                        searchSName(newTextSN);
                        return false;
                    }
                });

            }

        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void searchSName(String newTextSN) {

        ArrayList<Users> NewlistSName = new ArrayList<>();

        for (Users SN : PListAA) {

            if (SN.getShopName().toLowerCase().contains(newTextSN.toLowerCase())) {
                NewlistSName.add(SN);
            }

        }

        AdminUserReAdapter adminUserAdapter = new AdminUserReAdapter(getActivity(), NewlistSName);
        UserReee.setAdapter(adminUserAdapter);

    }

    private void searchItNumber(String newTextNum) {

        ArrayList<Users> NewlistNum = new ArrayList<>();

        for (Users aUserModal : PListAA) {
            if (aUserModal.getNumber().toLowerCase().contains(newTextNum.toLowerCase())) {
                NewlistNum.add(aUserModal);
            }
        }
        AdminUserReAdapter adapter = new AdminUserReAdapter(getActivity(), NewlistNum);
        UserReee.setAdapter(adapter);
    }

    private void searchItName(String newTextNa) {

        ArrayList<Users> NewlistName = new ArrayList<>();

        for (Users aUserModal : PListAA) {
            if (aUserModal.getName().toLowerCase().contains(newTextNa.toLowerCase())) {
                NewlistName.add(aUserModal);
            }
        }
        AdminUserReAdapter adapter = new AdminUserReAdapter(getActivity(), NewlistName);
        UserReee.setAdapter(adapter);
    }

}
