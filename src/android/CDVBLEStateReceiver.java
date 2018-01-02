
package it.lmancini.cordova.magtek;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CDVBLEStateReceiver extends BroadcastReceiver {

    private CDVEventEmitter eventFireService;

    public CDVBLEStateReceiver(CDVEventEmitter eventFireService) {
        this.eventFireService = eventFireService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    state = 4;
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    state = 6;
                    break;
                case BluetoothAdapter.STATE_ON:
                    state = 5;
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    state = 7;
                    break;
                default:
                    state = 0; // Unknown state
                    break;
            }
            this.eventFireService.emitEventWithInt("bleStateChange", state);
        }

    }
}
