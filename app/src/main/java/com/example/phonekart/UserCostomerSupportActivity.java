package com.example.phonekart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class UserCostomerSupportActivity extends AppCompatActivity {

    private EditText ComandSugg;
    private Button Submittttt;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_costomer_support);

        Paper.init(UserCostomerSupportActivity.this);

        ComandSugg = findViewById(R.id.SandW);
        Submittttt = findViewById(R.id.submitBBBBB);

        back = findViewById(R.id.back_sett_ucs);

        Window window = UserCostomerSupportActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        Submittttt.setOnClickListener(v -> {
            String text = ComandSugg.getText().toString();

            if(text.isEmpty()){
                ComandSugg.setError("Please enter your Comment");
            }
            else{
                Add(text);
            }
        });

        back.setOnClickListener(v -> {

            Intent i = new Intent(UserCostomerSupportActivity.this, HomeActivity.class);
            i.putExtra("eeee", "SA");
            startActivity(i);

        });


    }

    private void Add(String text) {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Suggetion");

        String SaveCurruntTime, SaveCurruntDate, Time;

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        SaveCurruntDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
        SaveCurruntTime = currentTime.format(calendar.getTime());

        Time = SaveCurruntDate + SaveCurruntTime;

        HashMap<String, Object> su = new HashMap<>();
        su.put("Suggetion", text);
        su.put("Name", Paper.book().read(Prevalant.UserNameA));
        su.put("Number", Paper.book().read(Prevalant.UserNumberA));

        ref.child(Time).updateChildren(su).addOnCompleteListener(task -> Toast.makeText(UserCostomerSupportActivity.this, "Thanks for Your Feedback", Toast.LENGTH_SHORT).show());
    }
}