package com.example.adsdemo_java;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.bytedance.sdk.openadsdk.api.init.PAGConfig;
import com.bytedance.sdk.openadsdk.api.init.PAGSdk;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardItem;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAd;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAdLoadListener;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedRequest;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.adsdemo_java.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private static final String TAG = "SSSSSSSS";

    private Context mContext;

    private PAGRewardedAd mRewardedAd;

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //init SDK
        Context context = getApplicationContext();
        PAGConfig pAGInitConfig = buildNewConfig(mContext);
        PAGSdk.init(context, pAGInitConfig, new PAGSdk.PAGInitCallback() {
            @Override
            public void success() {
                //load pangle ads after this method is triggered.
                Log.e(TAG, "pangle init success: ");
                String version = PAGSdk.getSDKVersion();//get SDK's version
                boolean isInitSuccess = PAGSdk.isInitSuccess();//check the initialization status

                Log.e(TAG, "SDK version is: " + version);
                Log.e(TAG, "result is: " + isInitSuccess);

                //load Ad
                PAGRewardedRequest request = new PAGRewardedRequest();
                PAGRewardedAd.loadAd("980088192",
                        request,
                        new PAGRewardedAdLoadListener() {
                            @Override
                            public void onError(int code, String message) {
                                Log.e(TAG, "error is: "+ code + "message is: " + message);
                            }

                            @Override
                            public void onAdLoaded(PAGRewardedAd rewardedAd) {
                                Log.e(TAG, "Ad load success!!");
                                mRewardedAd = rewardedAd;


                                rewardedAd.setAdInteractionListener(new PAGRewardedAdInteractionListener(){

                                    @Override
                                    public void onAdShowed() {

                                    }

                                    @Override
                                    public void onAdClicked() {

                                    }

                                    @Override
                                    public void onAdDismissed() {

                                    }

                                    @Override
                                    public void onUserEarnedReward(PAGRewardItem item) {

                                    }

                                    @Override
                                    public void onUserEarnedRewardFail(int errorCode, String errorMsg) {

                                    }
                                });
                            }
                        });
            }

            @Override
            public void fail(int code, String msg) {
                Log.e(TAG, "pangle init fail: " + code);
            }
        });



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        //show Ads
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "showAds", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
                if (mRewardedAd != null) {
                    mRewardedAd.show(mActivity);
                }
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private static PAGConfig buildNewConfig(Context context) {
        return new PAGConfig.Builder()
                .appId("8025677")
                .appIcon(R.mipmap.ic_launcher)
                .debugLog(true)
                .build();
    }

    public void getCurrentActivity() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            mActivity = activity;
        }
    }
}