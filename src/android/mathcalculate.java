package cordova.plugin.mathcalculator;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

//************************
// import cordova.plugin.sdk.*;
import java.util.Locale;




/**
 * This class echoes a string called from JavaScript.
 */
public class mathcalculate extends CordovaPlugin implements JivoDelegate{
    public static final String TAG = "mathcalculate_";

    JivoSdk jivoSdk;
    CordovaInterface _cordova;
    public static String packageName;
    public static PluginResult mPluginResult;

public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this._cordova = cordova;
        packageName = cordova.getActivity().getApplicationContext().getPackageName();

        if (android.os.Build.VERSION.SDK_INT < 23) {
            return;
        }

    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("setupJivo")) {
            this.setupJivoSDK(callbackContext);
            // String message = args.getString(0);
            // String lang = args.getJSONObject(0).getString('lang');
            return true;
        } else if (action.equals("startChat")) {
            this.startChat(callbackContext);
            return true;
        } else if (action.equals("startWithWebChat")) {
            this.startWithWebChat(callbackContext);
        } else {
            callbackContext.error("Error");
            return false;
        }
        
    }

    private void setupJivoSDK(CallbackContext _callback) {
        // try {
            String lang = Locale.getDefault().getLanguage().indexOf("ru") >= 0 ? "ru": "en";

            //*********************************************************
            int webID = this._cordova
            .getActivity()
            .getResources()
            .getIdentifier("webview", "id", this._cordova
                .getActivity()
                .getApplicationContext()
                .getPackageName());

            jivoSdk = new JivoSdk((WebView) this._cordova.getActivity().getWindow().getDecorView().findViewById(webID), lang);
            jivoSdk.delegate = this;
            // jivoSdk.prepare();
        // }
        // catch(Exception e) {
        //     callbackContext.error("error");
        // }
    }

    //**************** jivo skd icerinde ki method ***********************
    @Override
    public void onEvent(String name, String data) {
        if(name.equals("url.click")){
            if(data.length() > 2){
                String url = data.substring(1, data.length() - 1);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                this._cordova
                        .getActivity()
                        .getApplicationContext()
                        .startActivity(browserIntent);
            }
        }
    }

    private void startChat(CallbackContext _callback) {
        // try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jivosite.ru/sdk"));
            this._cordova
                    .getActivity()
                    .getApplicationContext()
                    .startActivity(browserIntent);

            _callback.success("oppened");
        // } catch(Exception e) {
        //     callbackContext.error("error");
        // }
    }

    private void startWithWebChat(CallbackContext _callback) {
        // try {
            jivoSdk.prepare();
            _callback.success("oppened");
        // } catch(Exception e) {
        //     callbackContext.error("error");
        // }
    }
}
