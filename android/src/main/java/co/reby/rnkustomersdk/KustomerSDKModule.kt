package co.reby.rnkustomersdk

import com.facebook.react.bridge.*
import com.kustomer.core.models.*
import com.kustomer.core.models.chat.*
import org.json.JSONObject
import org.json.JSONException
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
    suspend fun describeCustomer(data: ReadableMap) {
        val email: String? = data.getString("email")
        var emails: List<KusEmail>? = null
        if (email != null && !email.isEmpty()) {
            emails = listOf(KusEmail(email))
        }
        val phone: String? = data.getString("phone")
        var phones: List<KusPhone>? = null
        if (phone != null && !phone.isEmpty()) {
            phones = listOf(KusPhone(phone))
        }

        var custom: Map<String, Any>? = null
        try {
            val customData: ReadableMap? = data.getMap("custom")
            if (customData != null) {
                val prova = toMap(customData)
                custom = prova
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

    private fun toMap(readableMap: ReadableMap): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        val iterator: ReadableMapKeySetIterator = readableMap.keySetIterator()
    
        while (iterator.hasNextKey()) {
            val key: String = iterator.nextKey()
            val type: ReadableType = readableMap.getType(key)

            when (type) {
                ReadableType.Boolean -> map.put(key, readableMap.getBoolean(key))
                ReadableType.Number -> map.put(key, readableMap.getDouble(key))
                ReadableType.String -> {
                    val value: String? = readableMap.getString(key)
                    if (value != null && !value.isEmpty()) {
                        map.put(key, value)
                    }   
                }
            }
        }
        return map 
    }
}