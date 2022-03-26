package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.phonekart.Modal.AdModalClass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class AdminActivity extends AppCompatActivity {

    private Button Phone, BasicPhone, EarPhone, HeadPhone, Speaker, LatestCover, BoyCover, GirlCover, Tampard, Hammer, AntiGlass, MatteGlass,
            ooDGlass, SpecialGlass, CameraProGlass, TicTokAccesories, Stand, PrintCover, PrintMug, PrintPellow, PrintSpeaker, PrintPandent,
            Watches, Accessaries, Pubg, Blutooth;

    private Button Check_Order, Logout_Admin, Manage_Order, User_Info, Manage_Ad, Print_Order, Feedback;

    private String Image1 = "No", Image2 = "No",Image3 = "No", Image4 = "No", Image5 = "No";

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Paper.init(AdminActivity.this);

        Phone = findViewById(R.id.phone);
        BasicPhone = findViewById(R.id.basicphone);
        LatestCover = findViewById(R.id.fancy_cover);
        AntiGlass = findViewById(R.id.antiBlueGlass);
        MatteGlass = findViewById(R.id.matteGlass);
        ooDGlass = findViewById(R.id.oodGlass);
        SpecialGlass = findViewById(R.id.specialGlass);
        CameraProGlass = findViewById(R.id.cameraProtectiveGlass);
        EarPhone = findViewById(R.id.earphone);
        HeadPhone = findViewById(R.id.headphone);
        Speaker = findViewById(R.id.speakers);
        BoyCover = findViewById(R.id.boycover);
        GirlCover = findViewById(R.id.girlCover);
        Tampard = findViewById(R.id.tampard);
        Hammer = findViewById(R.id.hammer);
        TicTokAccesories = findViewById(R.id.tiktokacc);
        Stand = findViewById(R.id.stand);
        PrintCover = findViewById(R.id.printCover);
        PrintMug = findViewById(R.id.printMug);
        PrintPellow = findViewById(R.id.printPellow);
        PrintSpeaker = findViewById(R.id.printSpeaker);
        PrintPandent = findViewById(R.id.printPandent);
        Watches = findViewById(R.id.watches);
        Accessaries = findViewById(R.id.accesorier);
        Pubg = findViewById(R.id.pubg);
        Blutooth = findViewById(R.id.bluetooth);

        Check_Order = findViewById(R.id.Check_Order);
        Logout_Admin = findViewById(R.id.Logout_Admin);
        Manage_Order = findViewById(R.id.Manage_Order);
        User_Info = findViewById(R.id.User_Information);
        Manage_Ad = findViewById(R.id.AdBaner);
        Feedback = findViewById(R.id.feedback);
        Print_Order = findViewById(R.id.PrintThings);

        BasicPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "BasicPhone");
                startActivity(i);
            }
        });

        LatestCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "LatestCover");
                startActivity(i);
            }
        });

        AntiGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "AntiGlass");
                startActivity(i);
            }
        });

        MatteGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "MatteGlass");
                startActivity(i);
            }
        });

        ooDGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "11DGlass");
                startActivity(i);
            }
        });

        SpecialGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "SpecialGlass");
                startActivity(i);
            }
        });

        CameraProGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "CameraProGlass");
                startActivity(i);
            }
        });

        PrintCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "PrintCover");
                startActivity(i);
            }
        });

        PrintPandent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "PrintPandent");
                startActivity(i);
            }
        });

        PrintPellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "PrintPellow");
                startActivity(i);
            }
        });

        PrintSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "PrintSpeaker");
                startActivity(i);
            }
        });

        PrintMug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "PrintMug");
                startActivity(i);
            }
        });

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Phone");
                startActivity(i);
            }
        });

        EarPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "EarPhone");
                startActivity(i);
            }
        });

        HeadPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "HeadPhone");
                startActivity(i);
            }
        });

        Speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Speaker");
                startActivity(i);
            }
        });

        BoyCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "BoyCover");
                startActivity(i);
            }
        });

        GirlCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "GirlCover");
                startActivity(i);
            }
        });

        Tampard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Tampard");
                startActivity(i);
            }
        });

        Hammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Hammer");
                startActivity(i);
            }
        });

        TicTokAccesories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "TicTokAccesories");
                startActivity(i);
            }
        });

        Stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Stand");
                startActivity(i);
            }
        });

        Watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Watches");
                startActivity(i);
            }
        });

        Accessaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Accessaries");
                startActivity(i);
            }
        });

        Pubg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Pubg");
                startActivity(i);
            }
        });

        Blutooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                i.putExtra("category", "Bluetooth");
                startActivity(i);
            }
        });

        /////////////////////////////////////////////////////////

        Manage_Ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAdd();
            }
        });

        Print_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                startActivity(i);
            }
        });

        Check_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminCheckOrderActivity.class);
                startActivity(i);
            }
        });

        Manage_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, HomeActivity.class);
                i.putExtra("Admin","Admin");
                startActivity(i);
            }
        });

        Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminFeedbackActivity.class);
                startActivity(i);
            }
        });

        Logout_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent i = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void goToAdd() {

        DatabaseReference ADref = FirebaseDatabase.getInstance().getReference().child("Ads");

        ADref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){

                    AdModalClass adModalClass = new AdModalClass();

                    if(i == 0){
                        Image1 = dataSnapshot.child("Image").getValue().toString();
                        i++;
                    }else if(i == 1){
                        Image2 = dataSnapshot.child("Image").getValue().toString();
                        i++;
                    }else if(i == 2){
                        Image3 = dataSnapshot.child("Image").getValue().toString();
                        i++;
                    }else if(i == 3){
                        Image4 = dataSnapshot.child("Image").getValue().toString();
                        i++;
                    }else if(i == 4){
                        Image5 = dataSnapshot.child("Image").getValue().toString();
                        Intent intent = new Intent(AdminActivity.this,ADActivity.class);
                        intent.putExtra("Link1", Image1);
                        intent.putExtra("Link2", Image2);
                        intent.putExtra("Link3", Image3);
                        intent.putExtra("Link4", Image4);
                        intent.putExtra("Link5", Image5);
                        startActivity(intent);
                        i++;
                    }
                    else {
                        Intent intent = new Intent(AdminActivity.this,ADActivity.class);
                        intent.putExtra("Link1", Image1);
                        intent.putExtra("Link2", Image2);
                        intent.putExtra("Link3", Image3);
                        intent.putExtra("Link4", Image4);
                        intent.putExtra("Link5", Image5);
                        startActivity(intent);
                    }
                }
                else {
                    Intent intent = new Intent(AdminActivity.this,ADActivity.class);
                    intent.putExtra("Link1", Image1);
                    intent.putExtra("Link2", Image2);
                    intent.putExtra("Link3", Image3);
                    intent.putExtra("Link4", Image4);
                    intent.putExtra("Link5", Image5);
                    startActivity(intent);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}