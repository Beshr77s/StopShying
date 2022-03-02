package com.qashar.stopshying;


import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qashar.stopshying.R;
import com.qashar.stopshying.Video.VideoAdapter;
import com.qashar.stopshying.Video.VideoObject;
import com.qashar.stopshying.databinding.ActivityVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    private ActivityVideoBinding binding;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        final ViewPager2 viewPager2 = findViewById(R.id.viewPager);

        List<VideoObject> videoObjects = new ArrayList<>();
//        videoObjects.add(new VideoObject("android.resource://" + getPackageName() + "/" + R.raw.p28,"تعلم كيف يكتب حرف (ي)",""));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Videos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videoObjects.clear();

                for (DataSnapshot snapshot1 :snapshot.getChildren()) {
                    VideoObject object = snapshot1.getValue(VideoObject.class);
                    videoObjects.add(object);
                }
                viewPager2.setAdapter(new VideoAdapter(videoObjects));
                Toast.makeText(getApplicationContext(), ""+videoObjects.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}