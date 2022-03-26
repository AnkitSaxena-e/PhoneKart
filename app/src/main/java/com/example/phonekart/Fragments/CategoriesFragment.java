package com.example.phonekart.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.phonekart.CategoryActivity;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;

import io.paperdb.Paper;

public class CategoriesFragment extends Fragment {

    private TextView V232, V216, V332, V464, V4128, V6128, V6256;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);

        V232 = root.findViewById(R.id.V_232);
        V216 = root.findViewById(R.id.V_216);
        V332 = root.findViewById(R.id.V_332);
        V464 = root.findViewById(R.id.V_464);
        V4128 = root.findViewById(R.id.V_4128);
        V6128 = root.findViewById(R.id.V_6128);
        V6256 = root.findViewById(R.id.V_6256);

        Paper.book().write(Prevalant.FAD, "CategoryA");

        V216.setOnClickListener(v -> {

            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("CCAATT", "2/16");
            startActivity(i);

        });

        V232.setOnClickListener(v -> {

            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("CCAATT", "2/32");
            startActivity(i);

        });

        V332.setOnClickListener(v -> {

            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("CCAATT", "3/32");
            startActivity(i);

        });

        V464.setOnClickListener(v -> {

            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("CCAATT", "4/64");
            startActivity(i);

        });

        V4128.setOnClickListener(v -> {

            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("CCAATT", "4/128");
            startActivity(i);

        });

        V6128.setOnClickListener(v -> {

            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("CCAATT", "2/32");
            startActivity(i);

        });

        V6256.setOnClickListener(v -> {

            Intent i = new Intent(getActivity(), CategoryActivity.class);
            i.putExtra("CCAATT", "6/256");
            startActivity(i);

        });

        return root;
    }
}
