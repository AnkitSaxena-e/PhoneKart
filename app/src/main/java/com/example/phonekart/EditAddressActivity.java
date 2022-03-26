package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class EditAddressActivity extends AppCompatActivity {

    EditText change_Name, change_Address, change_Number, change_Pin, change_State, change_City;
    TextView Close, Update, Change_Photo;
    CircleImageView change_profile_Image;
    String Name, type, PID;
    Uri ImageUri;
    private String myUri = "";
    StorageTask uploadTask;
    private  int f = 0;
    StorageReference profilePictureRef;
    private String checker = "";
    private ImageView back;
    private Dialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        try{
            PID = getIntent().getStringExtra("PID");

            Paper.init(EditAddressActivity.this);
            change_Name = findViewById(R.id.Change_Name);
            change_Address = findViewById(R.id.Change_Address);
            change_Number = findViewById(R.id.Change_Number);
            change_Pin = findViewById(R.id.Change_Pin);
            change_City = findViewById(R.id.Change_City);
            change_State = findViewById(R.id.Change_State);

            back = findViewById(R.id.back_sett_edit_add);

            Update = findViewById(R.id.update);

            LoadingBar = new Dialog(EditAddressActivity.this);
            LoadingBar.setContentView(R.layout.loading_dialog);
            LoadingBar.setCancelable(false);

            type = Paper.book().read(Prevalant.CheckAdmin);

            Window window = EditAddressActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

            Update.setOnClickListener(v -> updateOnlyUserInfo(PID));

            back.setOnClickListener(v -> {

                Intent i = new Intent(EditAddressActivity.this, ProfileActivity.class);
                startActivity(i);

            });

        }
        catch (Exception e){
            Toast.makeText(EditAddressActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void updateOnlyUserInfo(String pid) {

        if (TextUtils.isEmpty(change_Name.getText().toString())) {
            change_Name.setError("Please Enter");
        } else if (TextUtils.isEmpty(change_Number.getText().toString())) {
            change_Number.setError("Please Enter");
        }else if (TextUtils.isEmpty(change_Address.getText().toString())) {
            change_Address.setError("Please Enter");
        } else if (TextUtils.isEmpty(change_Pin.getText().toString())) {
            change_Pin.setError("Please Enter");
        } else if (TextUtils.isEmpty(change_City.getText().toString())) {
            change_City.setError("Please Enter");
        } else if (TextUtils.isEmpty(change_State.getText().toString())) {
            change_State.setError("Please Enter");
        } else {
            userInfoSaved(pid);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void userInfoSaved(String PIDD) {

        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().child("UAddressI")
                .child(Paper.book().read(Prevalant.UserIdA));

        String SaveCurruntTime, SaveCurruntDate, Time;

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        SaveCurruntDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
        SaveCurruntTime = currentTime.format(calendar.getTime());

        if(PIDD.equals("AS")) {
            Time = SaveCurruntDate + SaveCurruntTime;
        }
        else {
            Time = PIDD;
        }

        HashMap<String, Object> map = new HashMap<>();

        map.put("Name", change_Name.getText().toString());
        map.put("Address", change_Address.getText().toString());
        map.put("Number", change_Number.getText().toString());
        map.put("Pin", change_Pin.getText().toString());
        map.put("PID", Time);
        map.put("City", change_City.getText().toString());
        map.put("State", change_State.getText().toString());

        Userref.child(Time).updateChildren(map);

        LoadingBar.dismiss();

        Intent i = new Intent(EditAddressActivity.this, ProfileActivity.class);
        startActivity(i);
        Toast.makeText(EditAddressActivity.this, "Profile Information Update Successfully", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(EditAddressActivity.this, ProfileActivity.class);
        startActivity(i);

    }
}