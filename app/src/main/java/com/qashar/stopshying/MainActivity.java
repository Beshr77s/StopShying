package com.qashar.stopshying;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


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
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.ratebottomsheet.RateBottomSheet;
import com.mikhaellopez.ratebottomsheet.RateBottomSheetManager;
import com.qashar.stopshying.databinding.ActivityMainBinding;
import com.qashar.stopshying.databinding.SendBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private ActivityMainBinding binding;
    private AdView mAdView;
//    private FirebaseAnalytics mFirebaseAnalytics;

    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                loadAd();
            }
        });
//        startActivity(new Intent(getApplicationContext(),VideoActivity.class));
        
//        rewardedInterstitialAd.show(this,this);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        SendBinding  sendBinding = SendBinding.inflate(getLayoutInflater());
        builder.setView(sendBinding.getRoot());

        AlertDialog dialog = builder.create();
        sendBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Float rat= sendBinding.ratingBar.getRating();
            String message = sendBinding.etMessage.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("StopShying");
                if (!message.isEmpty()){
                    reference.child("Messages").push().setValue(message+("(:"+rat+":)"));
                    Snackbar.make(v,"شكرا لك على ارسالك للمقترح ^-^",Snackbar.LENGTH_SHORT).show();
//                dialog.dismiss();
                    sendBinding.etMessage.setText("");
                }else{
                    Snackbar.make(v,"لايمكن أرسال مقترح فارغ!",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();

//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        ArrayList<String> strings = new ArrayList<>();
        strings.add("لا خجل بعد اليوم");
        strings.add("ماهو الخجل بالنسبة لك ؟");
        strings.add("ماهو الخجل بالنسبة لك ؟");
        strings.add("انا مرأة نفسي");
        strings.add("اقتحام المواقف الاجتماعية");
        strings.add("تحدي نفسي");
        strings.add("افكار ونصائح");
        strings.add("التحول من شخص خجول الى واثق");
        strings.add("ركز على العقلية");
        strings.add("التعامل بثقة اكبر");
        strings.add("كيف تبدو واثقا بنفسك ؟");
        strings.add("استخدام لغة الجسد");
        strings.add("التعاملات الاجتماعية");
        strings.add("تغير نمط الحياة");
        strings.add("ماهي مخاوفك");
        strings.add("طلب صغير");
        binding.rv.setAdapter(new MyAdapter(MainActivity.this,strings));
        binding.rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        AdLoader adLoader = new AdLoader.Builder(MainActivity.this, "ca-app-pub-7502845830222181/5332234065")
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
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("تقييم التطبيق !").setMessage("من فضلك, قم بدعمنا بتقييم التطبيق وشاركة لاصدقائك فذلك يسعدنا حقا ^-^")
       ;builder.setPositiveButton("تقييم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()));
                startActivity(i);
            }
        }).
                setNegativeButton("لاحقأ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadAd() {/*
        // Use the test ad unit ID to load an ad.
        RewardedInterstitialAd.load(MainActivity.this, "ca-app-pub-7017573787579533/1974934836",
                new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        rewardedInterstitialAd = ad;
                        ad.show(MainActivity.this, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                Log.e(TAG, "onAdLoaded");
                            }
                        });


                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.e(TAG, "onAdFailedToLoad");
                    }
                });
//        rewardedInterstitialAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
//            @Override
//            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//            }
//        });*/

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

//    @Override
//    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//    }
}