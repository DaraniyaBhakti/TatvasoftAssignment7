package com.tatvasoft.tatvasoftassignment7.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.tatvasoft.tatvasoftassignment7.Fragment.CityFragment;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryLevel = level*100/(float) scale;
        CityFragment.batteryPercent.setText(String.format("%s%%", batteryLevel));

    }
}
