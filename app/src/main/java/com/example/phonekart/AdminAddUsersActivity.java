package com.example.phonekart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.phonekart.Adapter.CounteryData;
import com.google.firebase.auth.PhoneAuthProvider;

public class AdminAddUsersActivity extends AppCompatActivity {

    private Button Add_User, Enter_O;
    private EditText User_Na, User_Nu, User_Sh, Ent_O;
    private RelativeLayout Re_O;
    private Dialog LoadingBar;
    private Spinner spinner;
    private ImageView bb;
    private String Ch;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_users);

        Ch = getIntent().getStringExtra("loik");

        Add_User = findViewById(R.id.AAddUser);
        Enter_O = findViewById(R.id.DoneA);

        User_Na = findViewById(R.id.AUserName);
        User_Nu = findViewById(R.id.AUserNumber);
        User_Sh = findViewById(R.id.AUserShopName);

        Re_O = findViewById(R.id.OTppppppp);
        bb = findViewById(R.id.bb);

        LoadingBar = new Dialog(AdminAddUsersActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        spinner = findViewById(R.id.spinnerCountriesLP);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CounteryData.countryNames));

        String defaul = "India";
        ArrayAdapter myAdapter = (ArrayAdapter) spinner.getAdapter();
        int position = myAdapter.getPosition(defaul);
        spinner.setSelection(position);

        Add_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadingBar.show();

                String Name_U = User_Na.getText().toString();
                String Numb = User_Nu.getText().toString();
                String codee = CounteryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String Number_U = "+" + codee + Numb;
                String ShopName_U = User_Sh.getText().toString();

                if(TextUtils.isEmpty(Name_U)){
                    User_Na.setError("Please Enter");
                }
                else if(TextUtils.isEmpty(Number_U)){
                    User_Nu.setError("Please Enter");
                }
                else if(TextUtils.isEmpty(ShopName_U)){
                    User_Sh.setError("Please Enter");
                }
                else{
                    SendOtp(Name_U, Number_U, ShopName_U);
                }
            }
        });

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddUsersActivity.this, AdminMainActivity.class);
                startActivity(i);

            }
        });

    }

    private void SendOtp(String name_U, String number_U, String shopName_U) {

        LoadingBar.dismiss();

        Intent i = new Intent(AdminAddUsersActivity.this, ConfermOTP.class);
        i.putExtra("Name",name_U);
        i.putExtra("ShopName",shopName_U);
        i.putExtra("Number",number_U);
        startActivity(i);

    }
}