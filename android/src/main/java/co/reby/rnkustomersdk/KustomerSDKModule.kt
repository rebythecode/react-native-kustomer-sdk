package co.reby.rnkustomersdk;

import com.facebook.react.bridge.*
import com.google.gson.Gson
import com.kustomer.core.models.KusResult
import com.kustomer.core.models.chat.KusCustomerDescribeAttributes
import com.kustomer.core.models.chat.KusEmail
import com.kustomer.core.models.chat.KusPhone
import com.kustomer.ui.Kustomer
import kotlinx.coroutines.runBlocking
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
    fun describeCustomer(data: ReadableMap){

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
    
    @ReactMethod
    fun presentSupport(apiKey: String) {
        Kustomer.getInstance().open()
    }

    @ReactMethod
    fun resetTracking() {
        Kustomer.getInstance().logOut()
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
