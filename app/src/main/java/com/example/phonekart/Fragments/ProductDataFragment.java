package com.example.phonekart.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.phonekart.ConfermOrderActivity;
import com.example.phonekart.Prevalant.OrderPrevalent;
import com.example.phonekart.Prevalant.Prevalant;
import com.example.phonekart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import io.paperdb.Paper;

public class ProductDataFragment extends Fragment {

    private TextView BuyPP;
    private RelativeLayout re_kk;

    private TextView productName, productPrice;
    private TextView Color1, SnR1;
    private String Price, SR1 = "A", Col1, Priceee = "AA";
    private TextView Name_T, Detail_T, Brand_T;
    private String ProductId = "", Image1;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private ImageView expandedImageView, IM;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_data, container, false);

        try {

            Paper.init(requireActivity());

            Name_T = root.findViewById(R.id.name_TA);
            Detail_T = root.findViewById(R.id.Detail_TA);
            Brand_T = root.findViewById(R.id.brand_TA);

            Color1 = root.findViewById(R.id.ProColor1A);

            SnR1 = root.findViewById(R.id.SnRP1A);

            BuyPP = root.findViewById(R.id.BuyPPPP);

            re_kk = root.findViewById(R.id.countainer_kkA);

            IM = root.findViewById(R.id.PI);

            productName = root.findViewById(R.id.Product_Name_dataA);
            productPrice = root.findViewById(R.id.Product_Price_dataA);

            expandedImageView = root.findViewById(R.id.expanded_imageA);

            ProductId = Paper.book().read(Prevalant.ProductId);

            Paper.book().write(Prevalant.FAD, "ProDataA");

            shortAnimationDuration = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

        } catch (Exception e) {
            Toast.makeText(getActivity(), "lklklk" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        getProductData(ProductId);

        return root;

    }

    private void getProductData(String productId) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    try {

                        String gggggg = Objects.requireNonNull(dataSnapshot.child("ProductName").getValue()).toString();

                        String ggggll_N = Objects.requireNonNull(dataSnapshot.child("TName").getValue()).toString();
                        String ggggll_D = Objects.requireNonNull(dataSnapshot.child("TDetail").getValue()).toString();
                        String ggggll_B = Objects.requireNonNull(dataSnapshot.child("TBrand").getValue()).toString();

                        productName.setText(gggggg);

                        if (Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString().equals("0")) {

                            BuyPP.setText("!Out Of Stock!");

                        }

                        Image1 = Objects.requireNonNull(dataSnapshot.child("Image1").getValue()).toString();

                        Price = Objects.requireNonNull(dataSnapshot.child("Price").getValue()).toString();

                        Col1 = Objects.requireNonNull(dataSnapshot.child("Color").getValue()).toString();

                        SR1 = Objects.requireNonNull(dataSnapshot.child("SnR").getValue()).toString();

                        String SellerId = Objects.requireNonNull(dataSnapshot.child("SellerId").getValue()).toString();

                        Name_T.setText(ggggll_N);
                        Detail_T.setText(ggggll_D);
                        Brand_T.setText(ggggll_B);

                        Priceee = Price;

                        productPrice.setText("₹" + Price);

                        Color1.setText(Col1);

                        SnR1.setText(SR1);

                        Picasso.get().load(Image1).resize(600,400).centerCrop().into(IM);

                        if (Paper.book().read(Prevalant.UserIdA).equals(Objects.requireNonNull(dataSnapshot.child("SellerId").getValue()).toString())) {


                            if (Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString().equals("0")) {

                                BuyPP.setText("!Out Of Stock!");

                            } else {
                                BuyPP.setText("Make Out Of Stock");
                            }

                        }

                        BuyPP.setOnClickListener(v -> {

                            if (Paper.book().read(Prevalant.UserIdA).equals(Objects.requireNonNull(dataSnapshot.child("SellerId").getValue()).toString())) {

                                if (Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString().equals("0")) {
                                    Toast.makeText(getActivity(), "Product is Out Of Stock", Toast.LENGTH_SHORT).show();
                                } else {

                                    HashMap<String, Object> kl = new HashMap<>();

                                    kl.put("Quantity", "0");

                                    productRef.child(productId).updateChildren(kl).addOnCompleteListener(task -> Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show());
                                }

                            } else {

                                if (Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString().equals("0")) {
                                    Toast.makeText(getActivity(), "Product is Out Of Stock", Toast.LENGTH_SHORT).show();
                                } else {

                                    getTUB(ProductId, Priceee, SellerId, gggggg);

                                }
                            }
                        });

                        GUD(SellerId);

                    } catch (Exception ignored) {

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });    }

    private void getTUB(String productId, String priceee, String sellerId, String gggggg) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("User");

        productRef.child(sellerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String UN = Objects.requireNonNull(snapshot.child("Name").getValue()).toString();

                    Intent i = new Intent(getActivity(), ConfermOrderActivity.class);
                    i.putExtra("PPID", productId);
                    i.putExtra("TotalPrice", priceee);
                    i.putExtra("SId", sellerId);
                    i.putExtra("UN", UN);
                    i.putExtra("PPNN", gggggg);
                    i.putExtra("QQ", "1");
                    startActivity(i);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void GUD(String sellerId) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("User");

        productRef.child(sellerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String Token = Objects.requireNonNull(snapshot.child("Token").getValue()).toString();

                    Paper.book().write(OrderPrevalent.Token, Token);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void zoomImageFromThumb(final View thumbView, String imageResId) {

        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        Picasso.get().load(imageResId).into(expandedImageView);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        re_kk.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {

            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

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

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
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

}
