package co.reby.rnkustomersdk

import com.facebook.react.bridge.*
import com.kustomer.core.models.*
import com.kustomer.core.chat.*
import java.lang.Object
import com.kustomer.ui.Kustomer

class KustomerSDKModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
        
    override fun getName(): String {
        return "KustomerSDK"
    }

    @ReactMethod
    fun identify(hash: String) {
        Kustomer.getInstance().logIn(hash){
            when (it) {
                is KusResult.Success -> it.data
                is KusResult.Error -> it.exception.localizedMessage
            }
        }
    }

    @ReactMethod
    fun presentSupport() {
        Kustomer.getInstance().open()
    }

    @ReactMethod
    fun openConversationsCount(promise: Promise) {
        promise.resolve(Kustomer.getInstance().observeActiveConversationIds())
    }

    @ReactMethod
    fun resetTracking() {
        Kustomer.getInstance().logOut()
    }

    @ReactMethod
    fun describeCustomer(data: ReadableMap) {
        val email: String? = data.getString("email")
        var emails = null
        if (email != null && !email.isEmpty()) {
            emails = listOf(KusEmail(email))
        }
        val phone: String? = data.getString("phone")
        var phones = null
        if (phone != null && !phone.isEmpty()) {
            phones = listOf(KusPhone(phone))
        }

        val custom = null
        try {
            val customData: ReadableMap? = data.getMap("custom")
            if (customData != null) {
                val customObject: JSONObject = convertMapToJson(customData)
                custom = customObject
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val attributes = KusCustomerDescribeAttributes(
            emails = emails,
            phones = phones,
            custom = custom     
        )
        Kustomer.getInstance().describeCustomer(attributes)
    }

    private fun convertMapToJson(readableMap: ReadableMap): JSONObject {
        var result = JSONObject()
        val iterator: ReadableMapKeySetIterator = readableMap.keySetIterator()
        while (iterator.hasNextKey()) {
            val key: String = iterator.nextKey()
            when (readableMap.getType(key)) {
                Boolean -> result.put(key, readableMap.getBoolean(key))
                Number -> result.put(key, readableMap.getDouble(key))
                String -> {
                    val value: String = readableMap.getString(key)
                    if (value != null && !value.isEmpty()) {
                        result.put(key, value)
                    }
                }
            }
        }
        return result
    }
}