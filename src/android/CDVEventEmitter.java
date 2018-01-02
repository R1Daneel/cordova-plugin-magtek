
package it.lmancini.cordova.magtek;

import android.app.Activity;

import org.apache.cordova.CordovaWebView;

import java.lang.ref.WeakReference;

public class CDVEventEmitter {

    private final String JS_FIRE_METHOD_NAME = "cordova.plugins.magtek.fire";
    private CordovaWebView webView;

    public CDVEventEmitter(CordovaWebView webView) {
        this.webView = webView;
    }

    public void emitEventWithInt(String event, int data) {
        final String js = String.format("%s('%s', %d)", JS_FIRE_METHOD_NAME, event, data);
        this.emitEvent(js);
    }

    private void emitEvent(final String jsCallback) {
        final WeakReference<CordovaWebView> view = new WeakReference<CordovaWebView>(this.webView);

        ((Activity)(view.get().getContext())).runOnUiThread(new Runnable() {
            public void run() {
                view.get().loadUrl("javascript:" + jsCallback);
            }
        });
    }
}
