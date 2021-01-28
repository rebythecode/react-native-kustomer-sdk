package co.reby.rnkustomersdk;

import android.app.Application
import androidx.lifecycle.liveData
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

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

        Kustomer.getInstance().logIn(hash){
            when(it){
                is KusResult.Success -> it.data
                is KusResult.Error -> it.exception.localizedMessage
            }
        }
    }

    @ReactMethod
    fun presentSupport(apiKey: String) {
        Kustomer.getInstance().openNewConversation()
    }
    @ReactMethod
    fun presentKnowledgeBase() {
        val activity = getCurrentActivity()
        //Kustomer.presentKnowledgeBase(activity);
    }
    @ReactMethod
    fun openConversationsCount(promise:Promise) {
        val activeConversationIds = liveData {
            emitSource(Kustomer.getInstance().observeActiveConversationIds())
        }
        promise.resolve(activeConversationIds)
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