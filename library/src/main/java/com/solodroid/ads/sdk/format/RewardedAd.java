package com.solodroid.ads.sdk.format;

import static com.solodroid.ads.sdk.util.Constant.ADMOB;
import static com.solodroid.ads.sdk.util.Constant.AD_STATUS_ON;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_MAX;
import static com.solodroid.ads.sdk.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroid.ads.sdk.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroid.ads.sdk.util.Constant.FAN_BIDDING_APPLOVIN_MAX;
import static com.solodroid.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroid.ads.sdk.util.Constant.FAN_BIDDING_IRONSOURCE;
import static com.solodroid.ads.sdk.util.Constant.IRONSOURCE;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.LevelPlayRewardedVideoListener;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinSdk;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
//import com.solodroid.ads.sdk.helper.AppLovinCustomEventInterstitial;
import com.solodroid.ads.sdk.util.OnRewardedAdCompleteListener;
import com.solodroid.ads.sdk.util.OnRewardedAdDismissedListener;
import com.solodroid.ads.sdk.util.OnRewardedAdErrorListener;
import com.solodroid.ads.sdk.util.Tools;
import com.unity3d.mediation.LevelPlayAdError;
import com.unity3d.mediation.LevelPlayAdInfo;
import com.unity3d.mediation.rewarded.LevelPlayReward;
import com.unity3d.mediation.rewarded.LevelPlayRewardedAd;
import com.unity3d.mediation.rewarded.LevelPlayRewardedAdListener;


public class RewardedAd {

    @SuppressWarnings("deprecation")
    public static class Builder {

        private static final String TAG = "SoloRewarded";
        private final Activity activity;
//        private com.google.android.gms.ads.rewarded.RewardedAd adMobRewardedAd;
//        private com.google.android.gms.ads.rewarded.RewardedAd adManagerRewardedAd;
        private MaxRewardedAd applovinMaxRewardedAd;
        public AppLovinInterstitialAdDialog appLovinInterstitialAdDialog;
        public AppLovinAd appLovinAd;
        private String adStatus = "";
        private String mainAds = "";
        private String backupAds = "";
        private String adMobRewardedId = "";
        private String adManagerRewardedId = "";
        private String fanRewardedId = "";
        private String unityRewardedId = "";
        private String applovinMaxRewardedId = "";
        private String applovinDiscRewardedZoneId = "";
        private String ironSourceRewardedId = "";
        private LevelPlayRewardedAd mRewardedAd;
        private String wortiseRewardedId = "";
        private String alienAdsRewardedId = "";
        private int placementStatus = 1;
        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public interface RewardedAdListener {
            void onRewardedAdComplete();
            void onRewardedAdError();
        }

        public Builder build(final RewardedAdListener callback) {
            loadRewardedAd(callback);
            return this;
        }

        public Builder show(final RewardedAdListener callback) {
            showRewardedAd(callback);
            return this;
        }

        public Builder setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Builder setMainAds(String mainAds) {
            this.mainAds = mainAds;
            return this;
        }

        public Builder setBackupAds(String backupAds) {
            this.backupAds = backupAds;
            return this;
        }

        public Builder setAdMobRewardedId(String adMobRewardedId) {
            this.adMobRewardedId = adMobRewardedId;
            return this;
        }

        public Builder setAdManagerRewardedId(String adManagerRewardedId) {
            this.adManagerRewardedId = adManagerRewardedId;
            return this;
        }

        public Builder setFanRewardedId(String fanRewardedId) {
            this.fanRewardedId = fanRewardedId;
            return this;
        }

        public Builder setUnityRewardedId(String unityRewardedId) {
            this.unityRewardedId = unityRewardedId;
            return this;
        }

        public Builder setApplovinMaxRewardedId(String applovinMaxRewardedId) {
            this.applovinMaxRewardedId = applovinMaxRewardedId;
            return this;
        }

        public Builder setApplovinDiscRewardedZoneId(String applovinDiscRewardedZoneId) {
            this.applovinDiscRewardedZoneId = applovinDiscRewardedZoneId;
            return this;
        }

        public Builder setIronSourceRewardedId(String ironSourceRewardedId) {
            this.ironSourceRewardedId = ironSourceRewardedId;
            return this;
        }

        public Builder setWortiseRewardedId(String wortiseRewardedId) {
            this.wortiseRewardedId = wortiseRewardedId;
            return this;
        }

        public Builder setAlienAdsRewardedId(String alienAdsRewardedId) {
            this.alienAdsRewardedId = alienAdsRewardedId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadRewardedAd(final RewardedAdListener callback) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                Log.w(TAG,"loadRewardedAd 1");
                switch (mainAds) {
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        applovinMaxRewardedAd = MaxRewardedAd.getInstance(applovinMaxRewardedId, activity);
                        applovinMaxRewardedAd.loadAd();
                        applovinMaxRewardedAd.setListener(new MaxRewardedAdListener() {
                            @Override
                            public void onUserRewarded(@NonNull MaxAd maxAd, @NonNull MaxReward maxReward) {
                                callback.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad complete");
                            }

//                            @Override
//                            public void onRewardedVideoStarted(@NonNull MaxAd maxAd) {
//                                Log.d(TAG, "[" + mainAds + "] " + "rewarded video started");
//                            }

//                            @Override
//                            public void onRewardedVideoCompleted(@NonNull MaxAd maxAd) {
//                                onComplete.onRewardedAdComplete();
//                                Log.d(TAG, "[" + mainAds + "] " + "rewarded video complete");
//                            }

                            @Override
                            public void onAdLoaded(@NonNull MaxAd maxAd) {
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                            }

                            @Override
                            public void onAdDisplayed(@NonNull MaxAd maxAd) {

                            }

                            @Override
                            public void onAdHidden(@NonNull MaxAd maxAd) {
                                loadRewardedAd(callback);
                                callback.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad hidden");
                            }

                            @Override
                            public void onAdClicked(@NonNull MaxAd maxAd) {

                            }

                            @Override
                            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                                loadRewardedBackupAd(callback);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + maxError.getMessage() + ", try to load backup ad: " + backupAds);
                            }

                            @Override
                            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                                loadRewardedBackupAd(callback);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + maxError.getMessage() + ", try to load backup ad: " + backupAds);
                            }
                        });
                        break;

                    case APPLOVIN_DISCOVERY:
//                        AdRequest.Builder builder = new AdRequest.Builder();
                        Bundle interstitialExtras = new Bundle();
                        interstitialExtras.putString("zone_id", applovinDiscRewardedZoneId);
//                        builder.addCustomEventExtrasBundle(AppLovinCustomEventInterstitial.class, interstitialExtras);
                        AppLovinSdk.getInstance(activity).getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
                            @Override
                            public void adReceived(AppLovinAd ad) {
                                appLovinAd = ad;
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                            }

                            @Override
                            public void failedToReceiveAd(int errorCode) {
                                loadRewardedBackupAd(callback);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + errorCode + ", try to load backup ad: " + backupAds);
                            }
                        });
                        appLovinInterstitialAdDialog = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(activity), activity);
                        appLovinInterstitialAdDialog.setAdDisplayListener(new AppLovinAdDisplayListener() {
                            @Override
                            public void adDisplayed(AppLovinAd appLovinAd) {

                            }

                            @Override
                            public void adHidden(AppLovinAd appLovinAd) {
                                loadRewardedAd(callback);
                                callback.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "ad hidden");
                            }
                        });
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:
                        Log.w(TAG,"loadRewardedAd 2 : "+ironSourceRewardedId);
                        mRewardedAd = new LevelPlayRewardedAd(ironSourceRewardedId);
                        mRewardedAd.setListener(new LevelPlayRewardedAdListener() {
                            @Override
                            public void onAdLoaded(LevelPlayAdInfo levelPlayAdInfo) {
                                Log.w(TAG,"loadRewardedAd 3");
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad is ready");
                            }

                            @Override
                            public void onAdLoadFailed(LevelPlayAdError levelPlayAdError) {
                                Log.w(TAG,"loadRewardedAd 5");
                                loadRewardedBackupAd(callback);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + levelPlayAdError.getErrorMessage() + ", try to load backup ad: " + backupAds);

                            }

                            @Override
                            public void onAdDisplayed(LevelPlayAdInfo levelPlayAdInfo) {

                            }

                            @Override
                            public void onAdDisplayFailed(@NonNull LevelPlayAdError error, @NonNull LevelPlayAdInfo adInfo) {
                                Log.w(TAG,"loadRewardedAd 5");
                                loadRewardedBackupAd(callback);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + error.getErrorMessage() + ", try to load backup ad: " + backupAds);
                            }
                            @Override
                            public void onAdClosed(@NonNull LevelPlayAdInfo adInfo) {
                                loadRewardedAd(callback);
                            }

                            @Override
                            public void onAdRewarded(LevelPlayReward levelPlayReward, LevelPlayAdInfo levelPlayAdInfo) {
                                callback.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad complete");

                            }
                        });
                        mRewardedAd.loadAd();
                        break;
                }
            }
        }

        public void loadRewardedBackupAd(final RewardedAdListener callback) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAds) {
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        applovinMaxRewardedAd = MaxRewardedAd.getInstance(applovinMaxRewardedId, activity);
                        applovinMaxRewardedAd.loadAd();
                        applovinMaxRewardedAd.setListener(new MaxRewardedAdListener() {
                            @Override
                            public void onUserRewarded(@NonNull MaxAd maxAd, @NonNull MaxReward maxReward) {
                                callback.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad complete");
                            }

                            @Override
                            public void onAdLoaded(@NonNull MaxAd maxAd) {
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                            }

                            @Override
                            public void onAdDisplayed(@NonNull MaxAd maxAd) {

                            }

                            @Override
                            public void onAdHidden(@NonNull MaxAd maxAd) {
                                loadRewardedAd(callback);
                                callback.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad hidden");
                            }

                            @Override
                            public void onAdClicked(@NonNull MaxAd maxAd) {

                            }

                            @Override
                            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                                loadRewardedBackupAd(callback);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + maxError.getMessage() + ", try to load backup ad: " + backupAds);
                            }

                            @Override
                            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                                loadRewardedBackupAd(callback);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + maxError.getMessage() + ", try to load backup ad: " + backupAds);
                            }
                        });
                        break;

                    case APPLOVIN_DISCOVERY:
//                        AdRequest.Builder builder = new AdRequest.Builder();
                        Bundle interstitialExtras = new Bundle();
                        interstitialExtras.putString("zone_id", applovinDiscRewardedZoneId);
//                        builder.addCustomEventExtrasBundle(AppLovinCustomEventInterstitial.class, interstitialExtras);
                        AppLovinSdk.getInstance(activity).getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
                            @Override
                            public void adReceived(AppLovinAd ad) {
                                appLovinAd = ad;
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad loaded");
                            }

                            @Override
                            public void failedToReceiveAd(int errorCode) {
                                loadRewardedBackupAd(callback);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + errorCode + ", try to load backup ad: " + backupAds);
                            }
                        });
                        appLovinInterstitialAdDialog = AppLovinInterstitialAd.create(AppLovinSdk.getInstance(activity), activity);
                        appLovinInterstitialAdDialog.setAdDisplayListener(new AppLovinAdDisplayListener() {
                            @Override
                            public void adDisplayed(AppLovinAd appLovinAd) {

                            }

                            @Override
                            public void adHidden(AppLovinAd appLovinAd) {
                                loadRewardedAd(callback);
                                callback.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "ad hidden");
                            }
                        });
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:
                        Log.w(TAG,"loadRewardedAd 2");
                        mRewardedAd = new LevelPlayRewardedAd(ironSourceRewardedId);
                        mRewardedAd.setListener(new LevelPlayRewardedAdListener() {
                            @Override
                            public void onAdLoaded(LevelPlayAdInfo levelPlayAdInfo) {
                                Log.w(TAG,"loadRewardedAd 3");
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad is ready");
                            }

                            @Override
                            public void onAdLoadFailed(LevelPlayAdError levelPlayAdError) {
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + levelPlayAdError.getErrorMessage() + ", try to load backup ad: " + backupAds);

                            }

                            @Override
                            public void onAdDisplayed(LevelPlayAdInfo levelPlayAdInfo) {

                            }

                            @Override
                            public void onAdDisplayFailed(@NonNull LevelPlayAdError error, @NonNull LevelPlayAdInfo adInfo) {
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + error.getErrorMessage() + ", try to load backup ad: " + backupAds);
                            }
                            @Override
                            public void onAdClosed(@NonNull LevelPlayAdInfo adInfo) {
                                loadRewardedBackupAd(callback);
                            }

                            @Override
                            public void onAdRewarded(LevelPlayReward levelPlayReward, LevelPlayAdInfo levelPlayAdInfo) {
                                callback.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad complete");

                            }
                        });
                        mRewardedAd.loadAd();
                        break;

                    default:
                        break;
                }
            }
        }

        public void showRewardedAd(final RewardedAdListener callback) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (mainAds) {
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        if (applovinMaxRewardedAd != null && applovinMaxRewardedAd.isReady()) {
                            applovinMaxRewardedAd.showAd();
                        } else {
                            showRewardedBackupAd(callback);
                        }
                        break;

                    case APPLOVIN_DISCOVERY:
                        if (appLovinInterstitialAdDialog != null) {
                            appLovinInterstitialAdDialog.showAndRender(appLovinAd);
                        } else {
                            showRewardedBackupAd(callback);
                        }
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:
                        if(mRewardedAd.isAdReady()) {
                            mRewardedAd.showAd(activity);
                        } else {
                            showRewardedBackupAd(callback);
                        }
                        break;

                    default:
                        callback.onRewardedAdError();
                        break;
                }
            }

        }

        public void showRewardedBackupAd(final RewardedAdListener callback) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAds) {
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        if (applovinMaxRewardedAd != null && applovinMaxRewardedAd.isReady()) {
                            applovinMaxRewardedAd.showAd();
                        } else {
                            callback.onRewardedAdError();
                        }
                        break;

                    case APPLOVIN_DISCOVERY:
                        if (appLovinInterstitialAdDialog != null) {
                            appLovinInterstitialAdDialog.showAndRender(appLovinAd);
                        } else {
                            callback.onRewardedAdError();
                        }
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:
                        if(mRewardedAd.isAdReady()) {
                            mRewardedAd.showAd(activity);
                        } else {
                            callback.onRewardedAdError();
                        }
                        break;

                    default:
                        callback.onRewardedAdError();
                        break;
                }
            }

        }

        public void destroyRewardedAd() {

        }

    }

}
