package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ConfermOTP extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private TextView User_Id, User_Pass;
    private ProgressBar progressBar;
    private EditText editText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    Button SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferm_otp);

        User_Pass = findViewById(R.id.UserPassAdd);
        User_Id = findViewById(R.id.UserIdAdd);

        try {
            mAuth = FirebaseAuth.getInstance();

            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    Toast.makeText(ConfermOTP.this, "Code Send", Toast.LENGTH_LONG).show();
                    super.onCodeSent(s, forceResendingToken);
                    verificationId = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    final String phonenumber = getIntent().getStringExtra("Number");
                    final String name = getIntent().getStringExtra("Name");
                    final String shopname = getIntent().getStringExtra("ShopName");
                    if (code != null) {
                        editText.setText(code);
                        verifyCode(code, name, shopname, phonenumber);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(ConfermOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

            final String name = getIntent().getStringExtra("Name");
            final String shopname = getIntent().getStringExtra("ShopName");
            final String phonenumber = getIntent().getStringExtra("Number");

            progressBar = findViewById(R.id.progressbar);

            AddAccount(name, phonenumber, shopname);

        }
        catch(Exception e){
            Toast.makeText(ConfermOTP.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void sendVerificationCode(String phonenumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack) {
        Toast.makeText(ConfermOTP.this,phonenumber,Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60,
                TimeUnit.SECONDS,
                ConfermOTP.this,
                mCallBack
        );
        //TaskExecutors.MAIN_THREAD
        Toast.makeText(ConfermOTP.this,"Send Verification Code",Toast.LENGTH_LONG).show();
    }

    private void verifyCode(String code, String Name, String ShopName, String PhoneNumber) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Toast.makeText(ConfermOTP.this,"verifyCode",Toast.LENGTH_LONG).show();
        signInWithCredential(credential, Name, PhoneNumber, ShopName);
    }

    private void signInWithCredential(PhoneAuthCredential credential, final String NameF, final String PhoneNumberF, final String ShopName) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AddAccount(NameF,PhoneNumberF,ShopName);
                        }

                        else {
                            Toast.makeText(ConfermOTP.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void AddAccount(String nameF, final String phoneNumberF, String shopN) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        String UID = UUID.randomUUID().toString().substring(0, 28);

        String word = UUID.randomUUID().toString().substring(0, 5) + nameF.substring(0, nameF.length()/2) + shopN.substring(0, shopN.length()/2 - 3);

        String WW = nameF.substring(0, 4) + shopN.substring(0, 4) + phoneNumberF.substring(3, 7);

        String UserId, Password;

        Password = getRSt(nameF, phoneNumberF);

        UserId = WW;

                    HashMap<String,Object> userdata = new HashMap<>();
                    userdata.put("Name",nameF);
                    userdata.put("Number",phoneNumberF);
                    userdata.put("ShopName",shopN);
                    userdata.put("Passward", Password);
                    userdata.put("UserId", UserId);
                    userdata.put("UID", UID);
                    userdata.put("Suspend", "A");
                    userdata.put("Warn", "");

                    RootRef.child("User").child(UserId).updateChildren(userdata).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        final DatabaseReference UpdateOrderNo = FirebaseDatabase.getInstance().getReference()
                                                .child("OrderNo").child(UserId);
                                        DatabaseReference UserRefAddNo = FirebaseDatabase.getInstance().getReference().child("UAddressNo")
                                                .child(UserId);

                                        HashMap<String, Object> OrderNo = new HashMap<>();
                                        OrderNo.put("OrderNo", "0");

                                        UpdateOrderNo.updateChildren(OrderNo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    HashMap<String, Object> mapANoJ = new HashMap<>();
                                                    mapANoJ.put("AddressNo", "0");

                                                    UserRefAddNo.updateChildren(mapANoJ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            User_Id.setText(UserId);
                                                            User_Pass.setText(Password);
                                                            Toast.makeText(ConfermOTP.this, "Congratulation, Account is Created Successfully", Toast.LENGTH_LONG).show();

                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
    }

    private String getRSt(String nameF, String phoneNumberF) {

        String AllCharN = "QWERTYUIOPLKJHGFDSAZXCVBNMmnbvcxzasdfghjklpoiuytrewq0987654321";
        StringBuilder namak = new StringBuilder();

        Random random = new Random();

        while(namak.length() <= 10){
            int index = (int) (random.nextFloat() * AllCharN.length());
            namak.append(AllCharN.charAt(index));
        }

        String PPPP = nameF.substring(0, 4) + phoneNumberF.substring(0, 4) + "@" + namak.toString().substring(0, 5);

        String namakstring = namak.toString();
        return PPPP;
    }

}

