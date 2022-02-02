package com.tatvasoft.tatvasoftassignment7.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.tatvasoft.tatvasoftassignment7.Fragment.CityFragment;
import com.tatvasoft.tatvasoftassignment7.R;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryLevel = level*100/(float) scale;
        CityFragment.batteryPercent.setText(String.format("%s%%", batteryLevel));

        if(batteryLevel >=0 && batteryLevel<=10) {
            CityFragment.batteryImg.setImageResource(R.drawable.battery_0_to_10);
        }
        else if(batteryLevel>10 && batteryLevel <=20){
            CityFragment.batteryImg.setImageResource(R.drawable.battery_10_20);
        }
        else if (batteryLevel>20 && batteryLevel<=40){
            CityFragment.batteryImg.setImageResource(R.drawable.battery_20_40);
        }
        else if(batteryLevel>40 && batteryLevel<=60){
            CityFragment.batteryImg.setImageResource(R.drawable.battery_40_60);
        }
        else if(batteryLevel>60 && batteryLevel<=80){
            CityFragment.batteryImg.setImageResource(R.drawable.battery_60_80);
        }
        else if(batteryLevel>80 && batteryLevel<=99){
            CityFragment.batteryImg.setImageResource(R.drawable.battery_80_99);
        }
        else if (batteryLevel == 100){
            CityFragment.batteryImg.setImageResource(R.drawable.battery_100);
        }

    }
}
