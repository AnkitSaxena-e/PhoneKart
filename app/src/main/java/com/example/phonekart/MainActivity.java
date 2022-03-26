package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.phonekart.Modal.Users;
import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button Join, Login;
    private String Image1 = "", Image2 = "",Image3 = "", Image4 = "", Image5 = "";
    private int i = 0;
    private Dialog LoadingBar;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

    Paper.init(MainActivity.this);

    Join = findViewById(R.id.join);
    Login = findViewById(R.id.login);
    LoadingBar = new Dialog(MainActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);
        Paper.init(MainActivity.this);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SYSTEM_ALERT_WINDOW)
            != PackageManager.PERMISSION_GRANTED){

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.INTERNET)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECEIVE_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SEND_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SYSTEM_ALERT_WINDOW))
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Grant Those Permissions");
            builder.setMessage("Internet, Read and Write Storage and Send SMS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                                    Manifest.permission.INTERNET,
                                    Manifest.permission.ACCESS_NETWORK_STATE,
                                    Manifest.permission.READ_SMS,
                                    Manifest.permission.RECEIVE_SMS,
                                    Manifest.permission.SEND_SMS,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.SYSTEM_ALERT_WINDOW
                            },ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

                }
            });

            builder.setNegativeButton("Cancel", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.SYSTEM_ALERT_WINDOW
                    },ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
        }
    }else{
        Toast.makeText(getApplicationContext(), "Permission is already Granted", Toast.LENGTH_SHORT).show();
    }

        Join.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, JoinActivity.class);
            startActivity(i);

        });

        Login.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        });

    String UserPhoneKey = Paper.book().read(Prevalant.userPhoneKey);
    String UserPassward = Paper.book().read(Prevalant.userPasswardKey);
    String UserType = Paper.book().read(Prevalant.CheckAdmin);

        if(!UserPhoneKey.equals("") && !UserPassward.equals("")){
        if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPassward)){

            LoadingBar.show();

            AddToAdd(UserPhoneKey, UserPassward, UserType);
        }
    }
}

    private void AddToAdd(final String userPhoneKey, final String userPassward, final String userType) {

        DatabaseReference ADref = FirebaseDatabase.getInstance().getReference().child("Ads");

        ADref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()) {

                    if (dataSnapshot.exists()) {

                        try {

                            if (i == 0) {
                                Image1 = Objects.requireNonNull(dataSnapshot.child("Image").getValue()).toString();
                                i++;
                            } else if (i == 1) {
                                Image2 = Objects.requireNonNull(dataSnapshot.child("Image").getValue()).toString();
                                i++;
                            } else if (i == 2) {
                                Image3 = Objects.requireNonNull(dataSnapshot.child("Image").getValue()).toString();
                                i++;
                            } else if (i == 3) {
                                Image4 = Objects.requireNonNull(dataSnapshot.child("Image").getValue()).toString();
                                i++;
                            } else if (i == 4) {
                                Image5 = Objects.requireNonNull(dataSnapshot.child("Image").getValue()).toString();
                                i++;
                                AllowAccess(userPhoneKey, userPassward, userType, Image1, Image2, Image3, Image4, Image5);
                            } else {
                                AllowAccess(userPhoneKey, userPassward, userType, Image1, Image2, Image3, Image4, Image5);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }

                else {
                    AllowAccess(userPhoneKey, userPassward, userType, Image1, Image2, Image3, Image4, Image5);
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

    private void AllowAccess(final String userPhoneKey, final String userPassward, final String userType, String image1,
                             String image2, String image3, String image4, String image5) {

        try {
            Paper.book().write(Prevalant.AD1, image1);
            Paper.book().write(Prevalant.AD2, image2);
            Paper.book().write(Prevalant.AD3, image3);
            Paper.book().write(Prevalant.AD4, image4);
            Paper.book().write(Prevalant.AD5, image5);
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(userType).child(userPhoneKey).exists()) {

                    Users userdata = dataSnapshot.child(userType).child(userPhoneKey).getValue(Users.class);

                    assert userdata != null;
                    if (userdata.getNumber().equals(userPhoneKey)) {

                        if (userdata.getPassward().equals(userPassward)) {
                            if (userType.equals("User")) {
                                try {
                                    Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                                    LoadingBar.dismiss();
                                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                    Prevalant.currentOnlineUser = userdata;
                                    Paper.book().write(Prevalant.userName, userdata);
                                    i.putExtra("Admin","AS");
                                    startActivity(i);
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else if (userType.equals("Admin")) {
                                Toast.makeText(MainActivity.this, "Admin Logged in Successfully", Toast.LENGTH_LONG).show();
                                LoadingBar.dismiss();
                                Intent i = new Intent(MainActivity.this, AdminActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                            LoadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Account with this " + userPhoneKey + " number does not exsit", Toast.LENGTH_LONG).show();
                    LoadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Please Create a new Account", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
