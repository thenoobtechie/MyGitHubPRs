package co.jp.catech.itohen.mygitcprs.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import co.jp.catech.itohen.mygitcprs.Constants.Companion.API_RESPONSE_LOG_TAG
import co.jp.catech.itohen.mygitcprs.data.CPRModel
import co.jp.catech.itohen.mygitcprs.viewmodel.CPRDisplayModel
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CPRApiCallbackHandler(private val updateLiveData : (Response<List<CPRModel>?>) -> Unit) :
    Callback<List<CPRModel>?> {

    override fun onResponse(
        call: Call<List<CPRModel>?>,
        response: Response<List<CPRModel>?>
    ) {
        Log.v(API_RESPONSE_LOG_TAG, "Data fetch successful")
        updateLiveData(response)
    }

    override fun onFailure(call: Call<List<CPRModel>?>, t: Throwable) {

        Log.v(API_RESPONSE_LOG_TAG, "Data error, reason: ${t.message}")
        updateLiveData(
            Response.error(
                CPRApiService.failure, ResponseBody.create(
                    MediaType.get("string"), t.message ?: ""
                )
            )
        )
    }
}