package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import rocks.ecox.jokeactivity.JokeActivity.JokeActivity;

public class MainActivityFragment extends Fragment implements OnTaskCompleted{
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
    private String mResult;
    private Boolean mAdsOnScreen;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button mJokeButton = (Button) root.findViewById(R.id.btn_joke);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        // Set up ads
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        // Create InterstitialAd object
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        // Create AdListener
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                mAdsOnScreen = false;
                launchActivity();
            }
        });

        requestNewInterstitial();

        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the interstitial ad
                if (mInterstitialAd.isLoaded()) {
                    mAdsOnScreen = true;
                    mInterstitialAd.show();
                } else {
                    mAdsOnScreen = false;
                }

                loadData();
                launchActivity();
            }
        });

        return root;
    }

    private void loadData() {
        mResult = null;
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(this);
        endpointsAsyncTask.execute();
    }

    @Override
    public void onTaskCompleted(String result) {
        mResult = result;
        launchActivity();
    }

    public void launchActivity() {
        // If no ads are displayed
        if (!mAdsOnScreen){

            if (mResult != null) {
                Intent intent = new Intent(getActivity(), JokeActivity.class);
                intent.putExtra(JokeActivity.JOKE_KEY, mResult);
                mProgressBar.setVisibility(View.GONE);
                startActivity(intent);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    // Request new interstitial ad
    private void requestNewInterstitial() {
        // Create an ad request
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}