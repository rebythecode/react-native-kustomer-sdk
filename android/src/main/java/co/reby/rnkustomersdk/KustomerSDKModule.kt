package co.reby.rnkustomersdk;

import androidx.lifecycle.liveData
import com.facebook.react.bridge.*
import com.facebook.stetho.json.ObjectMapper


import com.kustomer.core.models.KusResult
import com.kustomer.core.models.chat.KusCustomerDescribeAttributes
import com.kustomer.core.models.chat.KusEmail
import com.kustomer.core.models.chat.KusPhone
import com.kustomer.ui.Kustomer;
import org.json.JSONObject

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
    suspend fun describeCustomer(data: ReadableMap){

        val email = data.getString("email")
        val phone = data.getString("phone")
        val custom = data.getMap("custom")
        val convertedCustom = custom?.let { convertMapToJson(it) }
        val objectMapper = ObjectMapper()


        val attributes = KusCustomerDescribeAttributes(
                emails = listOf(KusEmail(email!!)),
                phones = listOf(KusPhone(phone!!)),
                custom = objectMapper.convertValue(convertedCustom!!, HashMap<String, String>().javaClass)
        )

      Kustomer.getInstance().describeCustomer(attributes)

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

    private fun convertMapToJson(readableMap:ReadableMap):JSONObject {
        val jsonObject = JSONObject()
        val iterator = readableMap.keySetIterator()
        while (iterator.hasNextKey())
        {
            val key = iterator.nextKey()
            when (readableMap.getType(key)) {
                ReadableType.Boolean -> jsonObject.put(key, readableMap.getBoolean(key))
                ReadableType.Number -> jsonObject.put(key, readableMap.getDouble(key))
                ReadableType.String -> {
                    val value = readableMap.getString(key)
                    if (value != null && !value.isEmpty())
                    {
                        jsonObject.put(key, value)
                    }
                }
            }
        }
        return jsonObject
    }

}
