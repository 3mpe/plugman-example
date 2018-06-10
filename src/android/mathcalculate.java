package cordova.plugin.mathcalculator;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    JivoSdk jivoSdk;
    public static String packageName;
    public static PluginResult mPluginResult;

public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.v(TAG, "Init Fingerprint");
        packageName = cordova.getActivity().getApplicationContext().getPackageName();
        mPluginResult = new PluginResult(PluginResult.Status.NO_RESULT);

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
            mPluginResult = new PluginResult(PluginResult.Status.Error);
            callbackContext.error("Error");
            callbackContext.sendPluginResult(mPluginResult);
            return false;
        }
        
    }

    private void setupJivoSDK(CallbackContext _callback) {
        // try {
            String lang = Locale.getDefault().getLanguage().indexOf("ru") >= 0 ? "ru": "en";

            //*********************************************************
            int fingerprint_auth_dialog_title_id = getResources()
                .getIdentifier("webview", "id",
                        cordova.getActivity().getApplicationContext().getPackageName());

            jivoSdk = new JivoSdk((WebView) findViewById(R.id.webview), lang);
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
                startActivity(browserIntent);
            }
        }
    }

    private void startChat(CallbackContext _callback) {
        // try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jivosite.ru/sdk"));
            startActivity(browserIntent);

            mPluginResult = new PluginResult(PluginResult.Status.Ok);            
            callbackContext.success("oppened");
            callbackContext.sendPluginResult(mPluginResult);
        // } catch(Exception e) {
        //     callbackContext.error("error");
        // }
    }

    private void startWithWebChat(CallbackContext _callback) {
        // try {
            jivoSdk.prepare();
            mPluginResult = new PluginResult(PluginResult.Status.Ok);            
            callbackContext.success("oppened");
            callbackContext.sendPluginResult(mPluginResult);
        // } catch(Exception e) {
        //     callbackContext.error("error");
        // }
    }
}
