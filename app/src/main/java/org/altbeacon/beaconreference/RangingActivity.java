package org.altbeacon.beaconreference;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import android.app.Activity;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

public class RangingActivity extends Activity {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
   // protected Integer n = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);

    }

    @Override
    protected void onResume() {
        super.onResume();
        RangeNotifier rangeNotifier = new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                int n=0;
                if (beacons.size() > 0) {
                    Log.i(TAG, "didRangeBeaconsInRegion called with beacon count:  " + beacons.size());
                    Beacon firstBeacon = beacons.iterator().next();
                    logToDisplay("The " + n + " beacon " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away.");
                    n++;
                    Gson gson = new Gson();
                    String Beacon_distance = gson.toJson(firstBeacon.getDistance());
                    String Beacon_name = gson.toJson(firstBeacon.toString());
                    String Beacon_address = gson.toJson(firstBeacon.getBluetoothAddress());
                }
                /*if (beacons.size() > 0) {
                    Log.i(TAG, "didRangeBeaconsInRegion called with beacon count:  "+beacons.size());
                    Beacon firstBeacon = beacons.iterator().next();
                    logToDisplay("The " + n + " beacon " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away.");
                    n++;
                    string json = gson

                 */
                /*
                int i = 0;
                for(Beacon b : beacons) {
                    Beacon firstBeacon = beacons.iterator().next();
                    logToDisplay("The first beacon " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away. " + i);
                    i++;
                }

                 */
//                int i = 0;
//                int x = 1;
//                Iterator<Beacon> iterator = beacons.iterator();
//                while(iterator.hasNext()){
//                    if(i != x){
//                        logToDisplay(iterator.next().getBluetoothAddress());
//                        i++;
//                        x = i;
//                    } else {
//                        i++;
//                    }
//                }
            }

        };
        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.startRangingBeacons(BeaconReferenceApplication.wildcardRegion);
    }

    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.stopRangingBeacons(BeaconReferenceApplication.wildcardRegion);
        beaconManager.removeAllRangeNotifiers();
    }
/*
    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
    */


    private void logToDisplay(final String line) {
        runOnUiThread(new Runnable() {
            public void run() {
                EditText editText = (EditText)RangingActivity.this.findViewById(R.id.rangingText);
                editText.append(line+"\n");
            }
        });
    }
}
