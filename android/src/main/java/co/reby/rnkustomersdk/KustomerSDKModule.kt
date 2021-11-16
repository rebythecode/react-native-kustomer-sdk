package co.reby.rnkustomersdk

import com.facebook.react.bridge.*
import com.kustomer.core.models.*
import com.kustomer.core.models.chat.*
import org.json.JSONObject
import org.json.JSONException
import com.kustomer.ui.Kustomer
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

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
        val email = data.getString("email")
        val phone = data.getString("phone")
        val custom = data.getMap("custom")
        val convertedCustom = custom?.let { convertMapToJson(it) }

        val attributes = KusCustomerDescribeAttributes(
                emails = listOf(KusEmail(email!!)),
                phones = listOf(KusPhone(phone!!)),
                custom = Gson().fromJson(convertedCustom.toString(), HashMap<String, String>().javaClass)
        )

        runBlocking {
            Kustomer.getInstance().describeCustomer(attributes)
        }
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