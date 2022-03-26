package com.example.phonekart;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonekart.Modal.Product;
import com.example.phonekart.Modal.SearchCategoryModal;
import com.example.phonekart.Prevalant.OrderPrevalent;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.View_Holder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ConfermOrderActivity extends AppCompatActivity {

    private EditText Y_Full_Name, Y_Addres, Y_Number, Y_City, Y_Pin, Y_State;
    private Button Confirm, Place;
    private TextView Change_Total, rem1, rem2, tere;
    private RelativeLayout reR;
    private String Total_Price = "", PPIIDDDD = "", QQQQ = "", SIDD, UN, PN;
    private String Name, Number, Address, City, Pin, type, State, CheckAddOrEn;
    private RecyclerView ReVA;
    private RecyclerView.LayoutManager layoutManagerAddress;
    private Dialog paymentDialog;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private ProgressDialog LoadingBar;
    private FirebaseRecyclerOptions<SearchCategoryModal> options;
    private FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder> adapter;
    private DatabaseReference UAddref;
    private int i = 0;
    private TextView Cash_On_Delivery, Paytm_Payment, Other_Payment;
    private final int UPI_PAYMENT = 0;
    private int k = 0;
    private int j = 5;
    private int h = 0;
    private ImageView back;
    private int m = 0;
    private int n = 0;
    private int f = 0;
    private int d = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferm_order);

        Paper.init(ConfermOrderActivity.this);
        Total_Price = getIntent().getStringExtra("TotalPrice");
        PPIIDDDD = getIntent().getStringExtra("PPID");
        QQQQ = getIntent().getStringExtra("QQ");
        SIDD = getIntent().getStringExtra("SId");
        UN = getIntent().getStringExtra("UN");
        PN = getIntent().getStringExtra("PPNN");
        LoadingBar = new ProgressDialog(ConfermOrderActivity.this);

        back = findViewById(R.id.back_sett_con_order);

        Y_Full_Name = findViewById(R.id.Full_Name_Conferm);
        Y_Addres = findViewById(R.id.Address_Conferm);
        Y_Number = findViewById(R.id.Phone_Conferm);
        Y_City = findViewById(R.id.City_Conferm);
        Y_State = findViewById(R.id.State_Conferm);
        Y_Pin = findViewById(R.id.Pin_Conferm);

        Change_Total = findViewById(R.id.total_Price);

        rem1 = findViewById(R.id.remove11);
        rem2 = findViewById(R.id.remove21);

        reR = findViewById(R.id.kiuyt);
        tere = findViewById(R.id.lopl);

        type = Paper.book().read(Prevalant.CheckAdmin);
        Place = findViewById(R.id.Place_Button);

        Window window = ConfermOrderActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        Change_Total.setText("Hello! " + Paper.book().read(Prevalant.UserNameA) + "Your total \n Amount is Rs." + Total_Price + "/-");

        Place.setOnClickListener(v -> {

            Name = Y_Full_Name.getText().toString();
            Number = Y_Number.getText().toString();
            Address = Y_Addres.getText().toString();
            City = Y_City.getText().toString();
            State = Y_State.getText().toString();
            Pin = Y_Pin.getText().toString();

            Checkll(Name, Number, Address, City, State, Pin);

        });

        ReVA = findViewById(R.id.Recycler_Address);
//        RecyclerViewAddress.setHasFixedSize(true);
        ReVA.setItemViewCacheSize(20);
        ReVA.setDrawingCacheEnabled(true);
        ReVA.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerAddress = new LinearLayoutManager(ConfermOrderActivity.this, LinearLayoutManager.VERTICAL, false);
        ReVA.setLayoutManager(layoutManagerAddress);

        back.setOnClickListener(v -> {

            Intent i = new Intent(ConfermOrderActivity.this, HomeActivity.class);
            i.putExtra("eeee", "PDA");
            startActivity(i);

        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        UAddref = FirebaseDatabase.getInstance().getReference().child("UAddressI").child(Paper.book().read(Prevalant.UserIdA));

        options =
                new FirebaseRecyclerOptions.Builder<SearchCategoryModal>()
                        .setQuery(UAddref, SearchCategoryModal.class)
                        .build();

        adapter =
                new FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, final int i, @NonNull final SearchCategoryModal modal) {

                        holder.CatName.setText(modal.getName());
                        holder.CatNumber.setText(modal.getNumber());
                        holder.CatAddress.setText(modal.getAddress());
                        holder.CatPin.setText(modal.getPin());
                        holder.CatEditButton.setVisibility(View.INVISIBLE);
                        holder.CatRemoveButton.setVisibility(View.INVISIBLE);

                        holder.itemView.setOnClickListener(v -> {

                            String Name = modal.getName();
                            String Number = modal.getNumber();
                            String Address = modal.getAddress();
                            String City = modal.getCity();
                            String State = modal.getState();
                            String Pin = modal.getPin();

                            Paper.book().write(OrderPrevalent.NameP, Name);
                            Paper.book().write(OrderPrevalent.NumberP, Number);
                            Paper.book().write(OrderPrevalent.AddressP, Address);
                            Paper.book().write(OrderPrevalent.CityP, City);
                            Paper.book().write(OrderPrevalent.PinP, Pin);
                            Paper.book().write(OrderPrevalent.TotalPrice, Total_Price);
                            Paper.book().write(OrderPrevalent.TUID, SIDD);
                            Paper.book().write(OrderPrevalent.Quantity, QQQQ);
                            Paper.book().write(OrderPrevalent.Keyword, QQQQ);
                            Paper.book().write(OrderPrevalent.StateP, State);
                            Paper.book().write(OrderPrevalent.UserID, Paper.book().read(Prevalant.UserIdA));

                            GoCheck();

                        });
                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_resource_file, parent, false);
                        CategoryViewHolder holder = new CategoryViewHolder(view);
                        return holder;
                    }
                };
        ReVA.setAdapter(adapter);
        adapter.startListening();
    }

    private void CheckAddNoACO(String name, String number, String address, String city, String state, String pin) {

        DatabaseReference UserRefAddNo = FirebaseDatabase.getInstance().getReference().child("UAddressI")
                .child(Paper.book().read(Prevalant.UserIdA));

        UserRefAddNo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    ArrayList<Product> pp = new ArrayList<>();

                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        pp.add(ss.getValue(Product.class));
                    }

                    int kl = pp.size();

                    userInfoSavedCOCO(name, number, address, city, state, pin, kl);

                }else{

                    int kl = 0;

                    userInfoSavedCOCO(name, number, address, city, state, pin, kl);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void userInfoSavedCOCO(String name, String number, String address, String city, String state, String pin, int kl) {

        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().child("UAddressI")
                .child(Paper.book().read(Prevalant.UserIdA));

        String SaveCurruntTime, SaveCurruntDate, Time;

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        SaveCurruntDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
        SaveCurruntTime = currentTime.format(calendar.getTime());

        if (kl < 3) {

            j++;

            Time = SaveCurruntDate + SaveCurruntTime;

            HashMap<String, Object> map = new HashMap<>();

            map.put("Name", Y_Full_Name.getText().toString());
            map.put("Address", Y_Addres.getText().toString());
            map.put("Number", Y_Number.getText().toString());
            map.put("PID", Time);
            map.put("Pin", Y_Pin.getText().toString());
            map.put("City", Y_City.getText().toString());
            map.put("State", Y_State.getText().toString());

            Userref.child(Time).updateChildren(map).addOnCompleteListener(task -> {
                Paper.book().write(OrderPrevalent.NameP, name);
                Paper.book().write(OrderPrevalent.NumberP, number);
                Paper.book().write(OrderPrevalent.AddressP, address);
                Paper.book().write(OrderPrevalent.CityP, city);
                Paper.book().write(OrderPrevalent.PinP, pin);
                Paper.book().write(OrderPrevalent.Quantity, QQQQ);
                Paper.book().write(OrderPrevalent.TotalPrice, Total_Price);
                Paper.book().write(OrderPrevalent.TUID, SIDD);
                Paper.book().write(OrderPrevalent.StateP, state);
                Paper.book().write(OrderPrevalent.UserID, Paper.book().read(Prevalant.UserIdA));

                GoCheck();

            });
        }

        Paper.book().write(OrderPrevalent.NameP, name);
        Paper.book().write(OrderPrevalent.NumberP, number);
        Paper.book().write(OrderPrevalent.AddressP, address);
        Paper.book().write(OrderPrevalent.CityP, city);
        Paper.book().write(OrderPrevalent.PinP, pin);
        Paper.book().write(OrderPrevalent.Quantity, QQQQ);
        Paper.book().write(OrderPrevalent.TotalPrice, Total_Price);
        Paper.book().write(OrderPrevalent.TUID, SIDD);
        Paper.book().write(OrderPrevalent.UserID, Paper.book().read(Prevalant.UserIdA));
        Paper.book().write(OrderPrevalent.StateP, state);

        GoCheck();

    }

    private void GoCheck() {

        LoadingBar.dismiss();

        Intent i = new Intent(ConfermOrderActivity.this, ReviewOrderActivity.class);
        i.putExtra("PPPP", PPIIDDDD);
        i.putExtra("UU", UN);
        i.putExtra("PPP", PN);
        startActivity(i);

    }

    private void Checkll(String name, String number, String address, String city, String state, String pin) {

        if (TextUtils.isEmpty(name)) {
            Y_Full_Name.setError("Please Enter Your Name");
        } else if (TextUtils.isEmpty(address)) {
            Y_Addres.setError("Please Enter Your Address");
        } else if (TextUtils.isEmpty(number)) {
            Y_Number.setError("Please Enter Your Number");
        } else if (TextUtils.isEmpty(city)) {
            Y_City.setError("Please Enter Your City");
        } else if (TextUtils.isEmpty(pin)) {
            Y_Pin.setError("Please Enter Your Pin");
        } else if (TextUtils.isEmpty(state)) {
            Y_State.setError("Please Enter Your State");
        } else {
            CheckAddNoACO(name, number, address, city, state, pin);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ConfermOrderActivity.this, HomeActivity.class);
        i.putExtra("eeee", "PDA");
        startActivity(i);
    }

}