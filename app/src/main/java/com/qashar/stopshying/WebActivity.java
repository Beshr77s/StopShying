package com.qashar.stopshying;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.qashar.stopshying.databinding.ActivityWebBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WebActivity extends AppCompatActivity {
    private ActivityWebBinding binding;
    private Integer image=0;
    private char aChar='a';
    private AdView mAdView;
    private boolean isShow = false;

    private ViewPagerAdapter mCardAdapter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private RewardedAd rewardedAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = getSharedPreferences("Root",MODE_PRIVATE);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        ads();
        if (preferences.getBoolean("card",false)==false){
            binding.imageView3.setImageResource(R.drawable.ic_down);
            binding.card.setVisibility(View.GONE);
        }else {
            binding.imageView3.setImageResource(R.drawable.ic_up);
            binding.card.setVisibility(View.VISIBLE);
        }


        editor = preferences.edit();
        int count = preferences.getInt("showen",0);
       if (count>=10){
           editor.putInt("showen",0);
           editor.apply();

           Intent i = new Intent(android.content.Intent.ACTION_VIEW);
           i.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Beshr%20Qashar"));
           startActivity(i);

       }else {
           count++;
           editor.putInt("showen",count);
           editor.apply();
       }
        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow){
                    isShow = false;
                    binding.imageView3.setImageResource(R.drawable.ic_down);
                    binding.card.setVisibility(View.GONE);
                }else {
                    isShow = true;
                    binding.imageView3.setImageResource(R.drawable.ic_up);
                    binding.card.setVisibility(View.VISIBLE);
                }
                    editor = preferences.edit();
                    editor.putBoolean("card",isShow);
                    editor.apply();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        AdLoader adLoader = new AdLoader.Builder(WebActivity.this, "ca-app-pub-7502845830222181/5283637760")
             .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                 @Override
                 public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {

                 }
             })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.

                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());

//        mInterstitialAd.show(WebActivity.this);
        pager();

        mAdView = findViewById(R.id.adView2);
//        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


//        AdLoader loader = new AdLoader.Builder(getApplicationContext(),"ca-app-pub-7017573787579533/2361921994")
//                .forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
//                    @Override
//                    public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
//                    }
//                }).withAdListener(new AdListener() {
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        super.onAdFailedToLoad(loadAdError);
//                    }
//                }).build();
//        loader.loadAd(new AdRequest.Builder().build());

    }


    private void ads(){
        if (rewardedAd == null){
            AdRequest request = new AdRequest.Builder().build();
            RewardedAd.load(this, "ca-app-pub-7502845830222181/2657474420", request, new RewardedAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                    super.onAdLoaded(rewardedAd);
                    WebActivity.this.rewardedAd = rewardedAd;
                    Toast.makeText(getApplicationContext(), "Sucsses", Toast.LENGTH_SHORT).show();

                    rewardedAd.show(WebActivity.this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void pager() {

        String url = "file:///android_asset/i0/a1.jpg";
        mCardAdapter = new ViewPagerAdapter(WebActivity.this);

        for (int i = 0; i <57 ; i++) {
            mCardAdapter.addCardItem("");
        }
        binding.vp.setAdapter(mCardAdapter);
//        binding.vp.setCurrentItem(57);
        binding.vp.setOffscreenPageLimit(3);
        switch (getIntent().getIntExtra("id",0)){
            case 15:
                binding.vp.setCurrentItem(57);
                break;
            case 14:
                binding.vp.setCurrentItem(53);
                break;
            case 13:
                binding.vp.setCurrentItem(49);
                break;
            case 12:
                binding.vp.setCurrentItem(45);
                break;
            case 11:
                binding.vp.setCurrentItem(41);
                break;
            case 10:
                binding.vp.setCurrentItem(39);
                break;
            case 9:
                binding.vp.setCurrentItem(34);
                break;
            case 8:
                binding.vp.setCurrentItem(30);
                break;
            case 7:
                binding.vp.setCurrentItem(29);
                break;
            case 6:
                binding.vp.setCurrentItem(24);
                break;
            case 5:
                binding.vp.setCurrentItem(20);
                break;
            case 4:
                binding.vp.setCurrentItem(15);
                break;
            case 3:
                binding.vp.setCurrentItem(12);
                break;
            case 2:
                binding.vp.setCurrentItem(4);
                break;
            case 1:
                binding.vp.setCurrentItem(3);
                break;
            case 0:
                binding.vp.setCurrentItem(0);
                break;
        }

        binding.txtCurrent.setText("57"+"/"+binding.vp.getCurrentItem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ourApps:
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Beshr%20Qashar"));
                startActivity(i);
                break;
            case R.id.menu_share:
                String goo="https://play.google.com/store/apps/details?id="+getPackageName();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "lllllll");
                intent.putExtra(Intent.EXTRA_TEXT, "ندعوك لتحميل تطبيق توقف عن الخجل تطبيق لمن يعانون من الرهب الاجتماعي " +"\n"+goo);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "مشاركة تطبيق توقف عن الخجل"));
                break;
            case R.id.meun_rating:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                break;
        }
        return true;
    }

    public void moveUp(View view) {
        int current = binding.vp.getCurrentItem();
        current++;
        binding.vp.setCurrentItem(current);
        binding.txtCurrent.setText("57"+"/"+current);
    }

    public void moveDown(View view) {
        int current = binding.vp.getCurrentItem();
        current--;
        binding.vp.setCurrentItem(current);
        binding.txtCurrent.setText("57"+"/"+current);
    }
}