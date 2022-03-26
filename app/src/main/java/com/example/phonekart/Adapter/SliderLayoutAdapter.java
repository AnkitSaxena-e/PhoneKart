package com.example.phonekart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.phonekart.ADSeeUserActivity;
import com.example.phonekart.R;

public class SliderLayoutAdapter extends PagerAdapter {
    private String[] urls;
    private LayoutInflater inflater;
    private Context context;

    public SliderLayoutAdapter(Context context, String[] urls) {
        this.context = context;
        this.urls = urls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slider_image_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.imageL);

//        RequestOptions options = new RequestOptions();
//        options.centerCrop();

        Glide.with(context)
                .load(urls[position])
                .into(imageView);

//        Picasso.get().load(urls[position]).fit().centerCrop().into(imageView);

        view.addView(imageLayout, 0);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ADSeeUserActivity.class);
                context.startActivity(intent);
            }
        });

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
