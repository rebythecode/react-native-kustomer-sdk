package co.reby.rnkustomersdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.uimanager.BaseViewManager
import com.facebook.react.uimanager.SimpleViewManager
import com.kustomer.core.models.KusResult
import com.kustomer.ui.Kustomer;

class KustomerSDKModule(reactContext:ReactApplicationContext):ReactContextBaseJavaModule(reactContext) {
    private val reactContext:ReactApplicationContext

    override fun getName(): String = "KustomerSDK"
    
    init{
        this.reactContext = reactContext
    }

    @ReactMethod
    fun identify(hash:String) {
        /*Kustomer.identify(hash, new KUSIdentifyListener() {
     @Override
     public void onComplete(final boolean success) {
     // Note: This will be called on background thread
     }
     });*/

        Kustomer.getInstance().logIn(hash){
            when(it){
                is KusResult.Success -> it.data
                is KusResult.Error -> it.exception.localizedMessage
            }
        }
    }

    @ReactMethod
    fun presentSupport() {
        val activity = getCurrentActivity()
        //Kustomer.showSupport(activity);
    }
    @ReactMethod
    fun presentKnowledgeBase() {
        val activity = getCurrentActivity()
        //Kustomer.presentKnowledgeBase(activity);
    }
    @ReactMethod
    fun openConversationsCount(promise:Promise) {
        //promise.run { resolve(Kustomer.getInstance().observeActiveConversationIds()) };
    }
    @ReactMethod
    fun resetTracking() {
        Kustomer.getInstance().logOut()
    }
    @ReactMethod
    fun setCurrentPageName(screen:String) {
        //Kustomer.setCurrentPageName(screen);
    }

}
