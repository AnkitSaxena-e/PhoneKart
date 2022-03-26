package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.paperdb.Paper;

public class AboutActivity extends AppCompatActivity {

     private TextView About, AnkitInsta, AnandInsta;
     private ImageView backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        About = findViewById(R.id.AboutA);
        AnkitInsta = findViewById(R.id.instaMe);
        AnandInsta = findViewById(R.id.instaAnand);
        backB = findViewById(R.id.back_sett_about);

        Window window = AboutActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        DatabaseReference UserRefA = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        AnkitInsta.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.instagram.com/ankits1432/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/ankits1432/")));
            }
        });

        AnandInsta.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.instagram.com/ankits1432/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/ankits1432/")));
            }
        });

        UserRefA.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("Name").exists()) {
                        String name = Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString();

                        About.setText("Hello " + name + "! as you know this is an e-commers app where you can discover and buy new things like Phones, Covers, HeadPhones and soo on. And also you can print your awasome memorable pictures onto your fabrate Accessories like Covers and Mugs. Here you can find so many things that you like.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AboutActivity.this, HomeActivity.class);
        i.putExtra("eeee", "SA");
        startActivity(i);
    }

}