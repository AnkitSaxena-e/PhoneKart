package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonekart.Modal.Product;
import com.example.phonekart.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class AdminProductInfoActivity extends AppCompatActivity {

    private ImageView productImage1;
    private TextView productName, productPrice;
    private TextView Color1, SnR1;
    private String Color = "A", SnR = "A", Price, SR1 = "A", Col1;
    private TextView Name_T, Detail_T, Brand_T;
    private String ProductId = "", Check = "", OrdrN = "",Type = "";
    private int p = 0, k = 0;
    private String Image1;
    private String OON, type, Total_Star, AColor, APrice, ASnR;
    private String[] ProductID = {"ABCDEFG"};
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private float Total_S;
    private TextView TDRating, TNRating, RText1, RText2, RText3, RText4, RText5;
    private ProgressBar PRating1, PRating2, PRating3, PRating4, PRating5;
    private RatingBar Total_Rating;
    private SmileRating smileRating;
    private TextView submitRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_info);

        try {
            Paper.init(AdminProductInfoActivity.this);
            ProductId = getIntent().getStringExtra("PPIIDD");
            APrice = getIntent().getStringExtra("Price");
            AColor = getIntent().getStringExtra("Color");
            ASnR = getIntent().getStringExtra("SnR");

            productImage1 = findViewById(R.id.PicPhoto1S);

            Name_T = findViewById(R.id.name_T);
            Detail_T = findViewById(R.id.Detail_T);
            Brand_T = findViewById(R.id.brand_T);

            Color1 = findViewById(R.id.ProColor1);

            SnR1 = findViewById(R.id.SnRP1);

            productName = findViewById(R.id.Product_Name_data);
            productPrice = findViewById(R.id.Product_Price_data);

            type = Paper.book().read(Prevalant.CheckAdmin).toString();
            shortAnimationDuration = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

        }
        catch (Exception e){
            Toast.makeText(AdminProductInfoActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        productImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomImageFromThumb(productImage1, Image1);
            }
        });

        getTotalNo(ProductId);

    }

    private void getTotalNo(String productId) {

        getProductData(productId, "AS");

    }

    public static String[] addS(int n, String arr[], String s){

        int h;

        String newPID[] = new String[n + 1];

        for(h = 0; h < n ; h++){
            newPID[h] = arr[h];
        }

        newPID[n] = s;

        return newPID;

    }

    private void getProductData(String productId, String totalRatingLLLL) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    Product product = dataSnapshot.getValue(Product.class);

                    productName.setText(product.getProductName());

                    Name_T.setText(product.getTName());
                    Detail_T.setText(product.getTDetail());
                    Brand_T.setText(product.getTBrand());

                    Picasso.get().load(product.getImage1()).into(productImage1);

                    Image1 = product.getImage1();


                        productPrice.setText(APrice);

                        Color1.setText(AColor);

                        SnR1.setText(ASnR);
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
        final ImageView expandedImageView = findViewById(R.id.expanded_image);
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
        findViewById(R.id.countainer_kk)
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

}
