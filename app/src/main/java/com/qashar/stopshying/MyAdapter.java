package com.qashar.stopshying;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    final Context context;
    final ArrayList<String> strings;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

//    private InterstitialAd mInterstitialAd;

    AdView adView;

    TextView text_Reward,text_video;
    private InterstitialAd mInterstitialAd;

    private RewardedAd rewardedAd;

//    private RewardedVideoAd mRewardedVideoAd;
    public MyAdapter(Context context, ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
        preferences = context.getSharedPreferences("ADS",Context.MODE_PRIVATE);
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.textView.setText(strings.get(position));
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context,"ca-app-pub-7017573787579533/2361921994", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        try {
            mInterstitialAd.show((Activity) context);
        }catch (Exception e){
            Log.i("TAG", e.getMessage());
        }
                //                int gift = preferences.getInt("gift",1);
//                if (gift>=5){
//                    showADS();
//                    Snackbar.make(v,"في كل 5 مرات تدخل يجب عليك مشاهدة اعلان",Snackbar.LENGTH_SHORT).show();
//                }else{
//                    gift++;
//                    editor = preferences.edit();
//                    editor.putInt("gift",gift);
//                    editor.apply();
                    Intent intent = new Intent(context,WebActivity.class);
                    intent.putExtra("id",position);
                    context.startActivity(intent);
//                }

            }
        });
    }

    private void showADS() {
    }


    @Override
    public int getItemCount() {
        return strings.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }


    }

}