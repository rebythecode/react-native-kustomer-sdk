package co.reby.rnkustomersdk

import com.facebook.react.bridge.*
import com.kustomer.core.models.KusResult
import com.kustomer.core.models.KusEmail
import com.kustomer.core.models.KusPhone
import com.kustomer.ui.Kustomer

class KustomerSDKModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
        
    override fun getName(): String {
        return "KustomerSDK"
    }

    @ReactMethod
    fun identify(hash: String?) {
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
    fun describeCustomer(data: ReadableMap) {
        val email: String = data.getString("email")
        val emails = null
        if (email != null && !email.isEmpty()) {
            emails = listOf(KusEmail(email))
        }
        val phone: String = data.getString("phone")
        val phones = null
        if (phone != null && !phone.isEmpty()) {
            phones = listOf(KusPhone(phone))
        }

        val custom = null
        try {
            val customData: ReadableMap = data.getMap("custom")
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
        val result = Kustomer.getInstance().describeCustomer(attributes)
        when (result) {
            is KusResult.Success -> //Success
            is KusResult.Error -> //Error
        }
    }

    private fun convertMapToJson(readableMap: ReadableMap): JSONObject {
        val object = JSONObject()
        val iterator: ReadableMapKeySetIterator = readableMap.keySetIterator()
        while (iterator.hasNextKey()) {
            val key: String = iterator.nextKey()
            when (readableMap.getType(key)) {
                Boolean -> object.put(key, readableMap.getBoolean(key))
                Number -> object.put(key, readableMap.getDouble(key))
                String -> {
                    val value: String = readableMap.getString(key)
                    if (value != null && !value.isEmpty()) {
                        object.put(key, value)
                    }
                }
            }
        }
        return object
    }
}