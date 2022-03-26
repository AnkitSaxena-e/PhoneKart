package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonekart.Adapter.CounteryData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinActivity extends AppCompatActivity {

    Button Join;
    EditText Name,Number,Passward,Conferm_Passward;
    Spinner spinner;
    ImageButton Show_Pass;
    private Dialog LoadingBar;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_join);

        Join = findViewById(R.id.Join_button);
        Name = findViewById(R.id.Name_Join);
        Number = findViewById(R.id.Number_Join);
        Passward = findViewById(R.id.Passward_Join);
        Conferm_Passward = findViewById(R.id.Passward_Conferm_Join);

        LoadingBar = new Dialog(JoinActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        Show_Pass = findViewById(R.id.Show_Join);

        Passward.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Conferm_Passward.setTransformationMethod(PasswordTransformationMethod.getInstance());

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CounteryData.countryNames));

        String defaul = "India";
        ArrayAdapter myAdapter = (ArrayAdapter) spinner.getAdapter();
        int position = myAdapter.getPosition(defaul);
        spinner.setSelection(position);

        Show_Pass.setOnClickListener(v -> {
            if(i == 0){
                Passward.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Conferm_Passward.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Show_Pass.setBackground(ContextCompat.getDrawable(JoinActivity.this, R.drawable.ic_visibility));
                i++;
            }

            else{
                Passward.setTransformationMethod(PasswordTransformationMethod.getInstance());
                Conferm_Passward.setTransformationMethod(PasswordTransformationMethod.getInstance());
                Show_Pass.setBackground(ContextCompat.getDrawable(JoinActivity.this,R.drawable.ic_visibility_off));
                i = 0;
            }
        });

        Join.setOnClickListener(v -> CreateAccount());
    }

    private void CreateAccount() {

        String name = Name.getText().toString();
        String passward = Passward.getText().toString();
        String conferm_passward = Conferm_Passward.getText().toString();
        String code = CounteryData.countryAreaCodes[spinner.getSelectedItemPosition()];
        String number = Number.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Name.setError("Please Enter Your Name");
        }

        else if (number.isEmpty() || number.length() < 10) {
            Number.setError("Valid number is required");
            Number.requestFocus();
        }

        else if(passward.length() < 8){
            Passward.setError("Minimum 8 Characters Required");
        }

        else if(TextUtils.isEmpty(code)){
            ((TextView)spinner.getSelectedView()).setError("Please Select Your Countery Code");
        }

        else if(TextUtils.isEmpty(passward)){
            Passward.setError("Please set your Password");
        }

        else if(TextUtils.isEmpty(conferm_passward)) {
            Conferm_Passward.setError("Please Conform Your Password");
        }

        else if(passward.equals(conferm_passward)) {
            LoadingBar.show();
            String phoneNumber = "+" + code + number;
            ValidatingAccount(name,phoneNumber,passward);
        }

        else{
            Passward.setError("Please Match the Password");
            Conferm_Passward.setError("Please Match the Password");
        }
    }

    private void ValidatingAccount(final String name, final String number, final String passward) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("User").child(number).exists())){
                    LoadingBar.dismiss();
                    Intent i = new Intent(JoinActivity.this, ConfermOTP.class);
                    i.putExtra("Name",name);
                    i.putExtra("Passward",passward);
                    i.putExtra("Number",number);
                    startActivity(i);
                }

                else{
                    Toast.makeText(JoinActivity.this,"This" + number + "is already exist please try with a different number",Toast.LENGTH_LONG).show();
                    LoadingBar.dismiss();
                    Intent i = new Intent(JoinActivity.this,MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
