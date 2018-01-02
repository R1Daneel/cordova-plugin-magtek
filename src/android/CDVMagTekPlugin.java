
package it.lmancini.cordova.magtek;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import com.magtek.mobile.android.mtlib.MTSCRA;

import org.json.JSONArray;
import org.json.JSONException;

public class CDVMagTekPlugin extends CordovaPlugin {

    private static final String GET_SDK_VERSION = "getSDKVersion";
    private static final String GET_BLE_STATE = "getBLEStatus";

    private MTSCRA libMagTek;
    private BluetoothAdapter bluetoothAdapter;
    private boolean hardwareSupportsBLE;

    @Override
    public void pluginInitialize() {
        Activity activity = this.cordova.getActivity();

        this.hardwareSupportsBLE = activity.getApplicationContext()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) &&
                Build.VERSION.SDK_INT >= 18;

        if (this.hardwareSupportsBLE) {
            this.libMagTek = new MTSCRA(activity.getApplicationContext(), new Handler(new CDVScraHanderCallback()));

            // Initializes Bluetooth adapter.
            final BluetoothManager bluetoothManager = (BluetoothManager)activity.getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = bluetoothManager.getAdapter();

            final CDVEventEmitter eventEmitter = new CDVEventEmitter(this.webView);
            final BroadcastReceiver stateReceiver = new CDVBLEStateReceiver(eventEmitter);
            IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            this.webView.getContext().registerReceiver(stateReceiver, intentFilter);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (!this.hardwareSupportsBLE) {
            callbackContext.error("This device does not support Bluetooth Low Energy.");
            return false;
        }

        if (GET_SDK_VERSION.equals(action)) {
            callbackContext.success(this.libMagTek.getSDKVersion());
            return true;
        } else if (GET_BLE_STATE.equals(action)) {
            callbackContext.success(bluetoothAdapter.getState());
        }
        return false;
    }
}
