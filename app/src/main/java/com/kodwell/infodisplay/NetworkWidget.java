package com.kodwell.infodisplay;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackphoenix.phoenixnetworkmanager.PxNetworkManager;
import com.blackphoenix.phoenixnetworkmanager.PxSignalStrength;

import java.util.List;


/**
 * Created by Abhishek on 4/23/2018.
 */

public class NetworkWidget extends RelativeLayout {

    private  String SIM_STATUS="NetworkWidget:SIM_STATUS";
    ImageView imgNetwork,imgSIM;
    TextView tvType,sim_status_text,operatorName;
    LinearLayout status_layout;
    PxNetworkManager pxNetworkManager;

    String LTE = "4G";
    String WCDMA = "3G";
    String GSM = "2G";

    String LEVEL_ONE = "1";
    String LEVEL_TWO = "2";
    String LEVEL_THREE = "3";
    String LEVEL_FOUR = "4";
    String LEVEL_ZERO = "0";

    String sim_status_txt;
    public NetworkWidget(Context context) {
        super(context);
    }

    public NetworkWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.layout_networkwidget, this, true);

            imgNetwork = findViewById(R.id.imgNetwork);

            tvType = findViewById(R.id.tvType);
            status_layout = findViewById(R.id.ll_sim_status);
           // sim_status_text = findViewById(R.id.tvSIMStatus);
            imgSIM = findViewById(R.id.imgSIM);
            operatorName = findViewById(R.id.operatorName);

            pxNetworkManager = new PxNetworkManager(context);
            TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            int simState = telMgr != null ? telMgr.getSimState() : 0;
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    Log.e(SIM_STATUS,"ABSENT");
                    sim_status_txt="ABSENT";
                    tvType.setVisibility(GONE);
                    imgNetwork.setVisibility(GONE);
                    status_layout.setVisibility(VISIBLE);
                    imgSIM.setImageDrawable(getResources().getDrawable(R.drawable.no_simcard));
                    break;
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                    Log.e(SIM_STATUS,"NETWORK_LOCKED");
                    sim_status_txt="NETWORK_LOCKED";
                    tvType.setVisibility(GONE);
                    imgNetwork.setVisibility(GONE);
                    status_layout.setVisibility(VISIBLE);
                    imgSIM.setImageDrawable(getResources().getDrawable(R.drawable.sim_exclamation));
                    break;
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    Log.e(SIM_STATUS,"PIN_REQUIRED");
                    sim_status_txt="PIN_REQUIRED";
                    tvType.setVisibility(GONE);
                    imgNetwork.setVisibility(GONE);
                    status_layout.setVisibility(VISIBLE);
                    imgSIM.setImageDrawable(getResources().getDrawable(R.drawable.sim_exclamation));
                    break;
                case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                    Log.e(SIM_STATUS,"PUK_REQUIRED");
                    sim_status_txt="PUK_REQUIRED";
                    tvType.setVisibility(GONE);
                    imgNetwork.setVisibility(GONE);
                    status_layout.setVisibility(VISIBLE);
                    imgSIM.setImageDrawable(getResources().getDrawable(R.drawable.sim_exclamation));
                    break;
                case TelephonyManager.SIM_STATE_READY:
                    Log.e(SIM_STATUS,"STATE_READY");
                    sim_status_txt="STATE_READY";
                    Log.e("Airplan Mode",isAirplaneModeOn(getContext())+"");
                    if (isAirplaneModeOn(getContext()))
                    {
                        tvType.setVisibility(GONE);
                        imgNetwork.setVisibility(GONE);
                        imgNetwork.setVisibility(GONE);
                        status_layout.setVisibility(VISIBLE);
                        imgSIM.setImageDrawable(getResources().getDrawable(R.drawable.ic_flight_black_24dp));
                    }else {
                        Log.e("Operator", telMgr.getNetworkOperatorName());
                        pxNetworkManager.registerSignalStrengthListener(signalStrengthListener);
                        operatorName.setText(telMgr.getNetworkOperatorName());
                    }
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    Log.e(SIM_STATUS,"UNKNOWN");
                    tvType.setVisibility(GONE);
                    imgNetwork.setVisibility(GONE);
                    status_layout.setVisibility(VISIBLE);
                    imgSIM.setImageDrawable(getResources().getDrawable(R.drawable.no_simcard));
                    break;
                default: Log.e("default","ok");
                    break;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }


    public void registerNetworkListner() {
        if (pxNetworkManager != null) {
            pxNetworkManager.registerSignalStrengthListener();
        }
    }

    public void unregisterNetworkListner() {

        if (pxNetworkManager != null) {
            pxNetworkManager.unRegisterSignalStrengthListener();
        }
    }

    PxNetworkManager.SignalStrengthListener signalStrengthListener = new PxNetworkManager.SignalStrengthListener() {
        @Override
        public void onSignalStrengthChanged(int i, List<PxSignalStrength> list) {
            if (list==null){return;}
            for (PxSignalStrength signalStrength : list) {

                if (signalStrength.type.contains("LTE")) {
                    tvType.setText(LTE);
                } else if (signalStrength.type.contains("WCDMA")) {
                    tvType.setText(WCDMA);
                } else if (signalStrength.type.contains("GSM")) {
                    tvType.setText(GSM);
                }
                if (signalStrength.strength.contains(LEVEL_FOUR)) {
                    imgNetwork.setImageResource(R.drawable.icnetworkfull);
                } else if (signalStrength.strength.contains(LEVEL_THREE)) {
                    imgNetwork.setImageResource(R.drawable.icnetworkthree);
                } else if (signalStrength.strength.contains(LEVEL_TWO)) {
                    imgNetwork.setImageResource(R.drawable.icnetworktwo);
                } else if (signalStrength.strength.contains(LEVEL_ONE)) {
                    imgNetwork.setImageResource(R.drawable.iclnetworkone);
                } else if (signalStrength.strength.contains(LEVEL_ZERO)) {
                    imgNetwork.setImageResource(R.drawable.icnonetwork);
                }

            }
        }
    };

    public static boolean isAirplaneModeOn(Context context) {

            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerNetworkListner();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterNetworkListner();
    }
}
