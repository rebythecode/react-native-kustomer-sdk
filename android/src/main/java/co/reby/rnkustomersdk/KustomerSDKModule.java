package co.reby.rnkustomersdk;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.kustomer.kustomersdk.Interfaces.KUSIdentifyListener;
import com.kustomer.kustomersdk.Kustomer;
import com.kustomer.kustomersdk.Models.KUSCustomerDescription;

public class KustomerSDKModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public KustomerSDKModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "KustomerSDK";
    }

    @ReactMethod
    public void identify(String hash){
        Kustomer.identify(hash, new KUSIdentifyListener() {
            @Override
            public void onComplete(final boolean success) {
                // Note: This will be called on background thread
            }
        });
    }

    @ReactMethod
    public void presentSupport() {
        final Activity activity = getCurrentActivity();
        Kustomer.showSupport(activity);
    }

    @ReactMethod
    public void presentKnowledgeBase(){
        final Activity activity = getCurrentActivity();
        Kustomer.presentKnowledgeBase(activity);
    }

    @ReactMethod
    public void openConversationsCount(final Promise promise){
        promise.resolve(Kustomer.getOpenConversationsCount());
    }

    @ReactMethod
    public void resetTracking(){
        Kustomer.resetTracking();
    }

    @ReactMethod
    public void setCurrentPageName(String screen){
        Kustomer.setCurrentPageName(screen);
    }

    @ReactMethod
    public void describeCustomer(final ReadableMap data){
        KUSCustomerDescription customerDescription = new KUSCustomerDescription();
        customerDescription.setEmail(data.getString("email"));
        customerDescription.setPhone(data.getString("phone"));

        try {
            ReadableMap customData = data.getMap("custom");
            if (customData != null){
                JSONObject customObject = convertMapToJson(customData);
                customerDescription.setCustom(customObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Kustomer.describeCustomer(customerDescription);
    }

    @ReactMethod
    public void customLayout(final ReadableMap data){
    }

    private static JSONObject convertMapToJson(ReadableMap readableMap) throws JSONException {
        JSONObject object = new JSONObject();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (readableMap.getType(key)) {
                case Null:
                    object.put(key, JSONObject.NULL);
                    break;
                case Boolean:
                    object.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    object.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    object.put(key, readableMap.getString(key));
                    break;
            }
        }
        return object;
    }
}
