package com.example.phonekart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.phonekart.ADActivity;
import com.example.phonekart.AdminAddPhoneActivity;
import com.example.phonekart.AdminAddUsersActivity;
import com.example.phonekart.AdminFHShowActivity;
import com.example.phonekart.AdminSeeProductPhoneActivity;
import com.example.phonekart.LoginActivity;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;

import io.paperdb.Paper;

public class AdminHomeFragment extends Fragment {

    private Button AddNUser, AddNPhone, ManagePhones, ManageBanners, ManageFPhones, LogoutA;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);

        Paper.init(requireActivity());

        AddNUser = root.findViewById(R.id.AdminAddNewUser);
        AddNPhone = root.findViewById(R.id.AdminAddNewPhone);
        ManagePhones = root.findViewById(R.id.AdminManagePhones);
        ManageBanners = root.findViewById(R.id.AdminManageBanner);
        ManageFPhones = root.findViewById(R.id.AdminManageFPhones);
        LogoutA = root.findViewById(R.id.AdminManageLogout);

        AddNUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminAddUsersActivity.class);
                i.putExtra("lkjh", "loik");
                startActivity(i);
            }
        });

        AddNPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminAddPhoneActivity.class);
                i.putExtra("lkjh", "loik");
                startActivity(i);
            }
        });

        ManagePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminSeeProductPhoneActivity.class);
                Paper.book().write(Prevalant.CheckAdmin, "Admin");
                startActivity(i);
            }
        });

        ManageBanners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ADActivity.class);
                startActivity(i);
            }
        });

        ManageFPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminFHShowActivity.class);
                startActivity(i);
            }
        });

        LogoutA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

}
