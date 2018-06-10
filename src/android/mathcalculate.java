package cordova.plugin.mathcalculator;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//************************
import cordova.plugin.sdk.*;
import java.util.Locale;


/**
 * This class echoes a string called from JavaScript.
 */
public class mathcalculate extends CordovaPlugin implements JivoDelegate{

    JivoSdk jivoSdk;
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
            return false;
        }
        
    }

    private void setupJivoSDK(CallbackContext callbackContext) {
        try {
            String lang = Locale.getDefault().getLanguage().indexOf("ru") >= 0 ? "ru": "en";

            //*********************************************************
            jivoSdk = new JivoSdk((WebView) findViewById(R.id.webview), lang);
            jivoSdk.delegate = this;
            // jivoSdk.prepare();
        }
        catch(Exception e) {
            callbackContext.error("error" + e.message);
        }
    }

    //**************** jivo skd icerinde ki method ***********************
    @Override
    public void onEvent(String name, String data) {
        if(name.equals("url.click")){
            if(data.length() > 2){
                String url = data.substring(1, data.length() - 1);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                callbackContext.success('opened');
            }
        }
    }

    private void startChat(CallbackContext callbackContext) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jivosite.ru/sdk"));
            startActivity(browserIntent);
            callbackContext.success('opened');
        } catch(Exception e) {
            callbackContext.error("error" + e.message);
        }
    }

    private void startWithWebChat(CallbackContext callbackContext) {
        try {
            jivoSdk.prepare();
            callbackContext.success('opened');
        } catch(Exception e) {
            callbackContext.error("error" + e.message);
        }
    }
}
