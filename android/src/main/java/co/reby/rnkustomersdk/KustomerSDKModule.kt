package co.reby.rnkustomersdk

import com.facebook.react.bridge.*
import com.kustomer.core.models.*
import com.kustomer.core.models.chat.*
import org.json.JSONObject
import org.json.JSONException
import com.kustomer.ui.Kustomer
import kotlinx.coroutines.runBlocking

class KustomerSDKModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
        
    override fun getName(): String {
        return "KustomerSDK"
    }

    @ReactMethod
    fun identify(hash:String, promise: Promise) {
        Kustomer.getInstance().logIn(hash){
            when (it) {
                is KusResult.Success -> promise.resolve(true)
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
        val email = data.getString("email")
        val phone = data.getString("phone")
        val custom = data.getMap("custom")

        val attributes = KusCustomerDescribeAttributes(
                emails = listOf(KusEmail(email!!)),
                phones = listOf(KusPhone(phone!!)),
                custom = toMap(custom!!)
        )

        runBlocking {
            Kustomer.getInstance().describeCustomer(attributes)
        }
    }

    private fun toMap(readableMap: ReadableMap): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        val iterator: ReadableMapKeySetIterator = readableMap.keySetIterator()
    
        while (iterator.hasNextKey()) {
            val key: String = iterator.nextKey()
            val type: ReadableType = readableMap.getType(key)

            when (type) {
                ReadableType.Boolean -> map[key] = readableMap.getBoolean(key).toString()
                ReadableType.Number -> map[key] = readableMap.getDouble(key).toString()
                ReadableType.String -> {
                    val value: String? = readableMap.getString(key)
                    if (value != null && !value.isEmpty()) {
                        map[key] = value
                    }   
                }
            }
        }
        return map 
    }
}