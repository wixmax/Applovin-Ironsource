package com.solodroid.ads.sdk.format;

import static com.solodroid.ads.sdk.util.Constant.ADMOB;
import static com.solodroid.ads.sdk.util.Constant.AD_STATUS_ON;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_MAX;
import static com.solodroid.ads.sdk.util.Constant.FAN_BIDDING_ADMOB;
import static com.solodroid.ads.sdk.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.solodroid.ads.sdk.util.Constant.FAN_BIDDING_APPLOVIN_MAX;
import static com.solodroid.ads.sdk.util.Constant.FAN_BIDDING_IRONSOURCE;
import static com.solodroid.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroid.ads.sdk.util.Constant.IRONSOURCE;
import static com.solodroid.ads.sdk.util.Constant.NONE;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkInitializationConfiguration;
import com.unity3d.mediation.LevelPlay;
import com.unity3d.mediation.LevelPlayConfiguration;
import com.unity3d.mediation.LevelPlayInitError;
import com.unity3d.mediation.LevelPlayInitListener;
import com.unity3d.mediation.LevelPlayInitRequest;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.initialization.AdapterStatus;

import java.util.Arrays;
import java.util.List;

public class AdNetwork {

    public static class Initialize {

        private static final String TAG = "AdNetwork";
        Activity activity;
        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobAppId = "";
        private String startappAppId = "0";
        private String unityGameId = "";
        private String appLovinSdkKey = "";
        private String mopubBannerId = "";
        private String ironSourceAppKey = "";
        private String wortiseAppId = "";
        private boolean debug = true;

        public interface LevelPlayInitCallback {
            void onInitSuccess();
            void onInitFailed(String error);
        }
        public Initialize(Activity activity) {
            this.activity = activity;
        }

        public Initialize build(final LevelPlayInitCallback callback) {
            initAds(callback);
            initBackupAds(callback);
            return this;
        }
        public Initialize build() {
            initAds(null);
            initBackupAds(null);
            return this;
        }

        public Initialize setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Initialize setAdNetwork(String adNetwork) {
            this.adNetwork = adNetwork;
            return this;
        }

        public Initialize setBackupAdNetwork(String backupAdNetwork) {
            this.backupAdNetwork = backupAdNetwork;
            return this;
        }

        public Initialize setAdMobAppId(String adMobAppId) {
            this.adMobAppId = adMobAppId;
            return this;
        }

        public Initialize setStartappAppId(String startappAppId) {
            this.startappAppId = startappAppId;
            return this;
        }

        public Initialize setUnityGameId(String unityGameId) {
            this.unityGameId = unityGameId;
            return this;
        }

        public Initialize setAppLovinSdkKey(String appLovinSdkKey) {
            this.appLovinSdkKey = appLovinSdkKey;
            return this;
        }

        public Initialize setMopubBannerId(String mopubBannerId) {
            this.mopubBannerId = mopubBannerId;
            return this;
        }

        public Initialize setIronSourceAppKey(String ironSourceAppKey) {
            this.ironSourceAppKey = ironSourceAppKey;
            return this;
        }

        public Initialize setWortiseAppId(String wortiseAppId) {
            this.wortiseAppId = wortiseAppId;
            return this;
        }

        public Initialize setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }





        public void initAds(final LevelPlayInitCallback callback) {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (adNetwork) {
                    case ADMOB:
                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_ADMOB:
                    case FAN_BIDDING_AD_MANAGER:
                        break;

                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        AppLovinSdkInitializationConfiguration initConfig = AppLovinSdkInitializationConfiguration.builder( appLovinSdkKey, activity )
                                .setMediationProvider( AppLovinMediationProvider.MAX )
                                .build();
                        AppLovinSdk.getInstance(activity).initialize(initConfig, new AppLovinSdk.SdkInitializationListener() {
                            @Override
                            public void onSdkInitialized(AppLovinSdkConfiguration appLovinSdkConfiguration) {

                            }
                        });
//                        AppLovinSdk.getInstance(activity).setMediationProvider(AppLovinMediationProvider.MAX);
//                        AppLovinSdk.getInstance(activity).initializeSdk(config -> {
//                        });
                        break;

                    case APPLOVIN_DISCOVERY:
                        AppLovinSdk.initializeSdk(activity);
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:

                        // Init the SDK when implementing the Multiple Ad Units Interstitial and Banner APIs, and Rewarded using legacy APIs
                        List<LevelPlay.AdFormat> legacyAdFormats = Arrays.asList(LevelPlay.AdFormat.REWARDED);

                        LevelPlayInitRequest initRequest = new LevelPlayInitRequest.Builder(ironSourceAppKey)
                                .withLegacyAdFormats(legacyAdFormats)
                                .withUserId("UserID")
                                .build();
                        LevelPlayInitListener initListener = new LevelPlayInitListener() {
                            @Override
                            public void onInitFailed(@NonNull LevelPlayInitError error) {
                                //Recommended to initialize again
                                callback.onInitFailed(error.getErrorMessage());
                            }
                            @Override
                            public void onInitSuccess(LevelPlayConfiguration configuration) {
                                //Create ad objects and load ads
                                Log.w(TAG,"onInitSuccess");
                                callback.onInitSuccess();
                            }
                        };
                        LevelPlay.init(activity, initRequest, initListener);

//                        String advertisingId = IronSource.getAdvertiserId(activity);
//                        IronSource.setUserId(advertisingId);
//                        IronSource.init(activity, ironSourceAppKey, () -> {
//                            Log.d(TAG, "[" + adNetwork + "] initialize complete");
//                        });
//                        IronSource.init(activity, ironSourceAppKey, IronSource.AD_UNIT.REWARDED_VIDEO);
//                        IronSource.init(activity, ironSourceAppKey, IronSource.AD_UNIT.INTERSTITIAL);
//                        IronSource.init(activity, ironSourceAppKey, IronSource.AD_UNIT.BANNER);
                        break;

                    default:
                    break;
                }
                Log.d(TAG, "[" + adNetwork + "] is selected as Primary Ads");
            }
        }

        public void initBackupAds(final LevelPlayInitCallback callback) {
            if (adStatus.equals(AD_STATUS_ON)) {
                switch (backupAdNetwork) {
                    case ADMOB:
                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_ADMOB:
                    case FAN_BIDDING_AD_MANAGER:
                        break;

                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case FAN_BIDDING_APPLOVIN_MAX:
                        AppLovinSdk.getInstance(activity).setMediationProvider(AppLovinMediationProvider.MAX);
                        AppLovinSdk.getInstance(activity).initializeSdk(config -> {
                        });
                        break;

                    case APPLOVIN_DISCOVERY:
                        AppLovinSdk.initializeSdk(activity);
                        break;

                    case IRONSOURCE:
                    case FAN_BIDDING_IRONSOURCE:

                        // Init the SDK when implementing the Multiple Ad Units Interstitial and Banner APIs, and Rewarded using legacy APIs
                        List<LevelPlay.AdFormat> legacyAdFormats = Arrays.asList(LevelPlay.AdFormat.REWARDED);

                        LevelPlayInitRequest initRequest = new LevelPlayInitRequest.Builder(ironSourceAppKey)
                                .withLegacyAdFormats(legacyAdFormats)
                                .withUserId("UserID")
                                .build();
                        LevelPlayInitListener initListener = new LevelPlayInitListener() {
                            @Override
                            public void onInitFailed(@NonNull LevelPlayInitError error) {
                                //Recommended to initialize again
                                callback.onInitFailed(error.getErrorMessage());
                            }
                            @Override
                            public void onInitSuccess(LevelPlayConfiguration configuration) {
                                //Create ad objects and load ads
                                callback.onInitSuccess();
                            }
                        };
                        LevelPlay.init(activity, initRequest, initListener);

//                        String advertisingId = IronSource.getAdvertiserId(activity);
//                        IronSource.setUserId(advertisingId);
//                        IronSource.init(activity, ironSourceAppKey, () -> {
//                            Log.d(TAG, "[" + adNetwork + "] initialize complete");
//                        });
//                        IronSource.init(activity, ironSourceAppKey, IronSource.AD_UNIT.REWARDED_VIDEO);
//                        IronSource.init(activity, ironSourceAppKey, IronSource.AD_UNIT.INTERSTITIAL);
//                        IronSource.init(activity, ironSourceAppKey, IronSource.AD_UNIT.BANNER);
                        break;

                    case NONE:
                        //do nothing
                        break;

                    default:
                        break;
                }
                Log.d(TAG, "[" + backupAdNetwork + "] is selected as Backup Ads");
            }
        }

    }

}
