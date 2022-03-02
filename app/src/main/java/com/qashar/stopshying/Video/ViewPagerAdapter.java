package com.qashar.stopshying.Video;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.qashar.stopshying.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<CardView> mViews;
    private List<String> mData;
    private float mBaseElevation;
    private Context context;

    public ViewPagerAdapter(Context context, List<String> mData) {
        this.mData = mData;
        mViews = new ArrayList<>();
        this.context=context;
    }

    public void addCardItem(String item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item, container, false);
        container.addView(view);
        bind((position), view);
        Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
//        VideoView videoView = view.findViewById(R.id.vv);
//        try {/
//            videoView.setVideoPath(context.getAssets().open("aa1.mp4").toString());
//            videoView.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        CardView cardView = (CardView) view.findViewById(R.id.cardView);
//        if (mBaseElevation == 0) {
//            mBaseElevation = cardView.getCardElevation();
//        }

//        cardView.setMaxCardElevation(mBaseElevation * 8);
//        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(Integer pos, View view) {
//        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
//        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);

//        titleTextView.setText(item.getTitle());
//        contentTextView.setText(item.getText());
//        imageView.setImageResource(R.drawable.a0);
//        imageView.setImageDrawable(item.getText());
    }

}