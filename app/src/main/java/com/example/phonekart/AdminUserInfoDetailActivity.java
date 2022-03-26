package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonekart.Modal.SearchCategoryModal;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.View_Holder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class AdminUserInfoDetailActivity extends AppCompatActivity {

    private TextView profileNameA, profileNumberA, profileAddressA, profilePinA, profilePassA, profileUIDA;
    private RecyclerView RecyclerViewAPA;
    private RecyclerView.LayoutManager layoutManagerAPA;
    private Button DeleteAcc, Added_Pro, Susp_User;
    private FirebaseRecyclerOptions<SearchCategoryModal> optionsAd;
    private FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder> adapterAd;
    private DatabaseReference UserAddressref;
    String EInfo, NUMA = "0", UUUU, CC = "TT";
    CircleImageView profile_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_info_detail);

        EInfo = Paper.book().read(Prevalant.ACAdmin);

        RecyclerViewAPA = findViewById(R.id.addressSeeAdmin);
        RecyclerViewAPA.setDrawingCacheEnabled(true);
        RecyclerViewAPA.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerAPA = new LinearLayoutManager(AdminUserInfoDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewAPA.setLayoutManager(layoutManagerAPA);

        profileNameA = findViewById(R.id.profile_nameA);
        profileNumberA = findViewById(R.id.profile_numberA);
        profilePassA = findViewById(R.id.PasswardProfileA);
        profileUIDA = findViewById(R.id.UIDProfileA);
        profile_Image = findViewById(R.id.user_profile_image_profileA);

        DeleteAcc = findViewById(R.id.Delete_Account);
        Susp_User = findViewById(R.id.Sus_User);

        Added_Pro = findViewById(R.id.See_Added_Products);

        TakeInfo(EInfo);

        CheckSusp(EInfo);

        DeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMA = EInfo;

                if (TextUtils.isEmpty(NUMA)) {
                    Toast.makeText(AdminUserInfoDetailActivity.this, "Please Enter Number", Toast.LENGTH_LONG).show();
                } else {
                    delInfo(NUMA);
                }
            }
        });

        Susp_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CC.equals("FF")){
                    UnSus(EInfo);
                }else{
                    SusUser(EInfo);
                }

            }
        });

    }

    private void UnSus(String eInfo) {

        DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("User").child(eInfo);

        HashMap<String, Object> hh = new HashMap<>();

        hh.put("Suspend", "A");

        rty.updateChildren(hh).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminUserInfoDetailActivity.this, "UnSuspended", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void CheckSusp(String Info) {

        DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("User").child(Info);

        rty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String ii = snapshot.child("Suspend").getValue().toString();

                    if(ii.equals("Suspended")){

                        Susp_User.setText("UnSuspend");

                        CC = "FF";

                    }else{

                        Susp_User.setText("Suspend");

                        CC = "WW";

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SusUser(String nInfo) {

        DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("User").child(nInfo);

        HashMap<String, Object> hh = new HashMap<>();

        hh.put("Suspend", "Suspended");

        rty.updateChildren(hh).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminUserInfoDetailActivity.this, "Suspended", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void WarUser(String wt, String EInfo) {

        DatabaseReference reffff = FirebaseDatabase.getInstance().getReference().child("User");

        HashMap<String, Object> hshs = new HashMap<>();

        hshs.put("Warn", wt);

        reffff.child(EInfo).updateChildren(hshs).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminUserInfoDetailActivity.this, "Warn Successfully", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        UserAddressref = FirebaseDatabase.getInstance().getReference()
                .child("UAddressI").child(EInfo);

        optionsAd =
                new  FirebaseRecyclerOptions.Builder<SearchCategoryModal>()
                        .setQuery(UserAddressref, SearchCategoryModal.class)
                        .build();

        adapterAd =
                new FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder>(optionsAd) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder , final int i , @NonNull final SearchCategoryModal modal) {

                        holder.CatName.setText(modal.getName());
                        holder.CatNumber.setText(modal.getNumber());
                        holder.CatAddress.setText(modal.getAddress() + "," + modal.getCity() + "," + modal.getState());
                        holder.CatPin.setText(modal.getPin());
                        holder.CatRemoveButton.setVisibility(View.INVISIBLE);
                        holder.CatEditButton.setVisibility(View.INVISIBLE);

                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_resource_file, parent, false);
                        CategoryViewHolder holder = new CategoryViewHolder(view);
                        return holder;
                    }
                };
        RecyclerViewAPA.setAdapter(adapterAd);
        adapterAd.startListening();
    }

    private void delInfo(String num) {

        final DatabaseReference ProfileRef = FirebaseDatabase.getInstance().getReference().child("User").child(num);

        android.app.AlertDialog.Builder aD = new android.app.AlertDialog.Builder(AdminUserInfoDetailActivity.this);
        aD.setMessage("Are You Sure, Your Wanna Delete this User..");
        aD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProfileRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AdminUserInfoDetailActivity.this, "Remove Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        aD.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = aD.create();
        alertDialog.show();

    }

    private void TakeInfo(final String num) {

        final DatabaseReference ProfileRef = FirebaseDatabase.getInstance().getReference().child("User").child(num);

        ProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showInform(num);
                } else {
                    Toast.makeText(AdminUserInfoDetailActivity.this, "Account with" + num + "does not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showInform(String num) {

        DatabaseReference refref = FirebaseDatabase.getInstance().getReference().child("User").child(num);

        profileNameA.setVisibility(View.VISIBLE);
        profilePassA.setVisibility(View.VISIBLE);
        profileNumberA.setVisibility(View.VISIBLE);
        profileUIDA.setVisibility(View.VISIBLE);
        profile_Image.setVisibility(View.VISIBLE);
        DeleteAcc.setVisibility(View.VISIBLE);

        refref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String NNNN = dataSnapshot.child("Name").getValue().toString();
                    String Nuuu = dataSnapshot.child("Number").getValue().toString();
                    String Nppp = dataSnapshot.child("Passward").getValue().toString();
                    String Nuus = dataSnapshot.child("UserId").getValue().toString();

                    try{
                        String Niii = dataSnapshot.child("Image").getValue().toString();

                        Picasso.get().load(Niii).into(profile_Image);

                    }catch (Exception n){
                        Toast.makeText(AdminUserInfoDetailActivity.this, "Pic is not available", Toast.LENGTH_SHORT).show();
                    }

                    profileNameA.setText(NNNN);
                    profileNumberA.setText(Nuuu);
                    profilePassA.setText(Nppp);
                    profileUIDA.setText(Nuus);

                    Added_Pro.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(AdminUserInfoDetailActivity.this, AdminSeeUserAddedPhoneActivity.class);
                            startActivity(i);
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AdminUserInfoDetailActivity.this, AdminMainActivity.class);
        startActivity(i);
    }
}