package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonekart.Modal.SearchCategoryModal;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.View_Holder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private TextView profileName, profileNumber, profileAddress, profilePin;
    private RecyclerView RecyclerViewAddressP;
    private RecyclerView.LayoutManager layoutManagerAddressP;
    private CircleImageView profile_Image;
    private TextView addAddress;
    private ImageView changeDP;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private String AddressNo;
    private FirebaseRecyclerOptions<SearchCategoryModal> options;
    private FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder> adapter;
    private DatabaseReference UserRefAddNoR;
    private DatabaseReference UserAddressref;
    private String ImageShow;
    private ImageView Back, Im_Con;
    private Dialog ImageSeeDialog;
    Uri ImageUri;
    private String myUri = "";
    StorageTask uploadTask;
    StorageReference profilePictureRef;
    private String checker = "";
    private Dialog LoadingBar;
    private Button Yes_Con, No_Con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Paper.init(ProfileActivity.this);

        profileName = findViewById(R.id.profile_name);
        profileNumber = findViewById(R.id.profile_number);
        profile_Image = findViewById(R.id.user_profile_image_profile);
        Back = findViewById(R.id.back_sett);

        profilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile Picture");

        LoadingBar = new Dialog(ProfileActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        addAddress = findViewById(R.id.updateProfile);
        changeDP = findViewById(R.id.changePhoto);

        Window window = ProfileActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        RecyclerViewAddressP = findViewById(R.id.addressSee);
        RecyclerViewAddressP.setItemViewCacheSize(20);
        RecyclerViewAddressP.setDrawingCacheEnabled(true);
        RecyclerViewAddressP.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerAddressP = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewAddressP.setLayoutManager(layoutManagerAddressP);

        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        ImageSeeDialog = new Dialog(ProfileActivity.this);
        ImageSeeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageSeeDialog.setCancelable(false);
        ImageSeeDialog.setContentView(R.layout.image_conferm_dialog);

        Yes_Con = ImageSeeDialog.findViewById(R.id.con_Yes_POPO);
        No_Con = ImageSeeDialog.findViewById(R.id.con_No_POPO);
        Im_Con = ImageSeeDialog.findViewById(R.id.con_Image_POPO);

        UpdateProfile();

        profile_Image.setOnClickListener(v -> zoomImageFromThumb(profile_Image, ImageShow));

        changeDP.setOnClickListener(v -> {

            if(ActivityCompat.checkSelfPermission(ProfileActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(ProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }

            try{

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 128);
                intent.putExtra("outputY", 128);
                intent.putExtra("return-data", true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);

            }catch (Exception e){
                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addAddress.setOnClickListener(v -> AddAddress());

        Back.setOnClickListener(v -> {
            Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
            i.putExtra("eeee", "SA");
            startActivity(i);
        });
    }

    private void uploadImage(Uri imageUri) {

        try {

            LoadingBar.show();

            if (imageUri != null) {
                final StorageReference fileRef = profilePictureRef.child(Prevalant.currentOnlineUser.getNumber() + ".jpg");
                uploadTask = fileRef.putFile(imageUri);

                uploadTask.continueWithTask((Continuation) task -> {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {

                    if (task.isSuccessful()) {

                        try {
                            Uri doewnloadUri = task.getResult();
                            myUri = doewnloadUri.toString();

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");

                            HashMap<String, Object> map = new HashMap<>();

                            map.put("Image", myUri);
                            ref.child(Paper.book().read(Prevalant.UserIdA)).updateChildren(map);

                            LoadingBar.dismiss();

                        }
                        catch (Exception b){
                            Toast.makeText(ProfileActivity.this,b.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    } else {
                        LoadingBar.dismiss();
                        Toast.makeText(ProfileActivity.this, "Error...Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch (Exception e){
            Toast.makeText(ProfileActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void UpdateProfile() {

        DatabaseReference User = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        User.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    profileName.setText(Objects.requireNonNull(dataSnapshot.child("Name").getValue()).toString());
                    profileNumber.setText(Objects.requireNonNull(dataSnapshot.child("Number").getValue()).toString());
                    if(dataSnapshot.child("Image").exists()){
                        ImageShow = Objects.requireNonNull(dataSnapshot.child("Image").getValue()).toString();
                        Picasso.get().load(Objects.requireNonNull(dataSnapshot.child("Image").getValue()).toString()).centerCrop().fit().into(profile_Image);
                    }
                    CheckAddNo();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void CheckAddNo() {

        DatabaseReference UserRefAddNo = FirebaseDatabase.getInstance().getReference().child("UAddressI")
                .child(Paper.book().read(Prevalant.UserIdA));

        UserRefAddNo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    ArrayList<SearchCategoryModal> hh = new ArrayList<>();

                    for(DataSnapshot ss : dataSnapshot.getChildren()){
                        hh.add(ss.getValue(SearchCategoryModal.class));
                    }

                    AddressNo = String.valueOf(hh.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        UserAddressref = FirebaseDatabase.getInstance().getReference().child("UAddressI").child(Paper.book().read(Prevalant.UserIdA));

        options =
                new  FirebaseRecyclerOptions.Builder<SearchCategoryModal>()
                        .setQuery(UserAddressref, SearchCategoryModal.class)
                        .build();

        adapter =
                new FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder>(options) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder ,final int i , @NonNull final SearchCategoryModal modal) {

                        holder.CatName.setText(modal.getName());
                        holder.CatNumber.setText(modal.getNumber());
                        holder.CatAddress.setText(modal.getAddress() + ", " + modal.getCity() + ", " + modal.getState());
                        holder.CatPin.setText(modal.getPin());

                        holder.CatEditButton.setOnClickListener(v -> {
                            Intent i1 = new Intent(ProfileActivity.this, EditAddressActivity.class);
                            i1.putExtra("PID", modal.getPID());
                            startActivity(i1);
                        });

                        holder.CatRemoveButton.setOnClickListener(v -> UserAddressref.child(modal.getPID()).removeValue()
                                .addOnCompleteListener(task -> Toast.makeText(ProfileActivity.this, "Remove Successfully", Toast.LENGTH_SHORT).show()));
                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_resource_file, parent, false);
                        CategoryViewHolder holder = new CategoryViewHolder(view);
                        return holder;
                    }
                };
        RecyclerViewAddressP.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            assert data != null;
            if(data.getClipData() != null){

                Toast.makeText(ProfileActivity.this, "Please Select a single Image", Toast.LENGTH_SHORT).show();

            } else if (data.getData() != null){

                ImageUri = data.getData();

                ImageSeeDialog.show();
                Im_Con.setImageURI(ImageUri);

                Yes_Con.setOnClickListener(v -> {

                    LoadingBar.show();
                    ImageSeeDialog.dismiss();

                    uploadImage(ImageUri);

                });

                No_Con.setOnClickListener(v -> ImageSeeDialog.dismiss());

            }
        }

        else{
            Toast.makeText(ProfileActivity.this, "Error try Again....", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
            finish();
        }

    }


    private void AddAddress() {

        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().child("UAddressI").child(Paper.book().read(Prevalant.UserIdA));

        Userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    ArrayList<SearchCategoryModal> lolp = new ArrayList<>();

                    for(DataSnapshot gg : dataSnapshot.getChildren()){
                        lolp.add(gg.getValue(SearchCategoryModal.class));
                    }

                    int ooooo = lolp.size();

                    if(ooooo == 3){
                        Toast.makeText(ProfileActivity.this, "You Can Only Add 3 Addresses", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(ProfileActivity.this, EditAddressActivity.class);
                        i.putExtra("PID", "AS");
                        startActivity(i);
                    }

                }else{
                    Intent i = new Intent(ProfileActivity.this, EditAddressActivity.class);
                    i.putExtra("PID", "AS");
                    startActivity(i);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void zoomImageFromThumb(final View thumbView, String imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = findViewById(R.id.expanded_imageproo);
//        expandedImageView.setImageResource(imageResId);
        Picasso.get().load(imageResId).into(expandedImageView);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.countainer_pr)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
        i.putExtra("eeee", "SA");
        startActivity(i);
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