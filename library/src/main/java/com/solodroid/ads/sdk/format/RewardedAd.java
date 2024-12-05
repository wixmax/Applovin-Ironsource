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
        private String wortiseRewardedId = "";
        private String alienAdsRewardedId = "";
        private int placementStatus = 1;
        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
            loadRewardedAd(onComplete, onDismiss);
            return this;
        }

        public Builder show(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
            showRewardedAd(onComplete, onDismiss, onError);
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

        public void loadRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (mainAds) {
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        applovinMaxRewardedAd = MaxRewardedAd.getInstance(applovinMaxRewardedId, activity);
                        applovinMaxRewardedAd.loadAd();
                        applovinMaxRewardedAd.setListener(new MaxRewardedAdListener() {
                            @Override
                            public void onUserRewarded(@NonNull MaxAd maxAd, @NonNull MaxReward maxReward) {
                                onComplete.onRewardedAdComplete();
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
                                loadRewardedAd(onComplete, onDismiss);
                                onComplete.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad hidden");
                            }

                            @Override
                            public void onAdClicked(@NonNull MaxAd maxAd) {

                            }

                            @Override
                            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                                loadRewardedBackupAd(onComplete, onDismiss);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + maxError.getMessage() + ", try to load backup ad: " + backupAds);
                            }

                            @Override
                            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                                loadRewardedBackupAd(onComplete, onDismiss);
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
                                loadRewardedBackupAd(onComplete, onDismiss);
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
                                loadRewardedAd(onComplete, onDismiss);
                                onComplete.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "ad hidden");
                            }
                        });
                        break;
                }
            }
        }

        public void loadRewardedBackupAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
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
                                onComplete.onRewardedAdComplete();
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
                                loadRewardedAd(onComplete, onDismiss);
                                onComplete.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad hidden");
                            }

                            @Override
                            public void onAdClicked(@NonNull MaxAd maxAd) {

                            }

                            @Override
                            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                                loadRewardedBackupAd(onComplete, onDismiss);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + maxError.getMessage() + ", try to load backup ad: " + backupAds);
                            }

                            @Override
                            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                                loadRewardedBackupAd(onComplete, onDismiss);
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
                                loadRewardedBackupAd(onComplete, onDismiss);
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
                                loadRewardedAd(onComplete, onDismiss);
                                onComplete.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "ad hidden");
                            }
                        });
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:
                        IronSource.setLevelPlayRewardedVideoListener(new LevelPlayRewardedVideoListener() {
                            @Override
                            public void onAdAvailable(AdInfo adInfo) {
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad is ready");
                            }

                            @Override
                            public void onAdUnavailable() {

                            }

                            @Override
                            public void onAdOpened(AdInfo adInfo) {

                            }

                            @Override
                            public void onAdShowFailed(IronSourceError ironSourceError, AdInfo adInfo) {
                                loadRewardedBackupAd(onComplete, onDismiss);
                                Log.d(TAG, "[" + mainAds + "] " + "failed to load rewarded ad: " + ironSourceError.getErrorMessage() + ", try to load backup ad: " + backupAds);
                            }

                            @Override
                            public void onAdClicked(Placement placement, AdInfo adInfo) {

                            }

                            @Override
                            public void onAdRewarded(Placement placement, AdInfo adInfo) {
                                onComplete.onRewardedAdComplete();
                                Log.d(TAG, "[" + mainAds + "] " + "rewarded ad complete");
                            }

                            @Override
                            public void onAdClosed(AdInfo adInfo) {
                                loadRewardedAd(onComplete, onDismiss);
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        }

        public void showRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (mainAds) {
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        if (applovinMaxRewardedAd != null && applovinMaxRewardedAd.isReady()) {
                            applovinMaxRewardedAd.showAd();
                        } else {
                            showRewardedBackupAd(onComplete, onDismiss, onError);
                        }
                        break;

                    case APPLOVIN_DISCOVERY:
                        if (appLovinInterstitialAdDialog != null) {
                            appLovinInterstitialAdDialog.showAndRender(appLovinAd);
                        } else {
                            showRewardedBackupAd(onComplete, onDismiss, onError);
                        }
                        break;

                    default:
                        onError.onRewardedAdError();
                        break;
                }
            }

        }

        public void showRewardedBackupAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAds) {
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        if (applovinMaxRewardedAd != null && applovinMaxRewardedAd.isReady()) {
                            applovinMaxRewardedAd.showAd();
                        } else {
                            onError.onRewardedAdError();
                        }
                        break;

                    case APPLOVIN_DISCOVERY:
                        if (appLovinInterstitialAdDialog != null) {
                            appLovinInterstitialAdDialog.showAndRender(appLovinAd);
                        } else {
                            onError.onRewardedAdError();
                        }
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:
                        if (IronSource.isRewardedVideoAvailable()) {
                            IronSource.showRewardedVideo(ironSourceRewardedId);
                        } else {
                            onError.onRewardedAdError();
                        }
                        break;

                    default:
                        onError.onRewardedAdError();
                        break;
                }
            }

        }

        public void destroyRewardedAd() {

        }

    }

}
