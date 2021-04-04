package co.jp.catech.itohen.mygitcprs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import co.jp.catech.itohen.mygitcprs.Utility.Companion.API_RESPONSE_LOG_TAG
import co.jp.catech.itohen.mygitcprs.data.CPRModel
import co.jp.catech.itohen.mygitcprs.data.Repository
import co.jp.catech.itohen.mygitcprs.network.ApiService
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CPRViewModel(var repo: Repository) : ViewModel() {

    var liveCPRDisplayModel = MutableLiveData<CPRDisplayModel>()

    fun updateData(response: Response<List<CPRModel>?>) {
        val dataModel: CPRDisplayModel = if(response.code() == ApiService.success) {
            CPRDisplayModel(data = response.body())
        } else {
            CPRDisplayModel(message = response.message())
        }
        liveCPRDisplayModel.postValue(dataModel)
    }

    fun fetchPrList(owner: String, repoName: String, prStatus: String = "closed", sortDirection: String = "desc") {

        if(owner.isNotBlank() && repoName.isNotBlank()) {
            liveCPRDisplayModel.postValue(CPRDisplayModel(showProgress = true))
            repo.fetchPrList(owner, repoName, prStatus, sortDirection = sortDirection, callback = object :
                Callback<List<CPRModel>?> {
                override fun onResponse(
                    call: Call<List<CPRModel>?>,
                    response: Response<List<CPRModel>?>
                ) {
                    Log.v(API_RESPONSE_LOG_TAG, "Data fetch successful")
                    updateData(response)
                }

                override fun onFailure(call: Call<List<CPRModel>?>, t: Throwable) {

                    Log.v(API_RESPONSE_LOG_TAG, "Data error, reason: ${t.message}")
                    updateData(Response.error(ApiService.failure, ResponseBody.create(
                        MediaType.get("string"), t.message ?: "")))
                }
            })
        }
        else
        {
            Log.v(API_RESPONSE_LOG_TAG, "Either owner or repo name is empty")
            invalidDataError()
        }
    }

    fun invalidDataError() {

        liveCPRDisplayModel.postValue(CPRDisplayModel(message = "Please fill in required fields"))
    }
}

//Model for the activity screen
class CPRDisplayModel(var showProgress: Boolean = false, var data: List<CPRModel>? = null, var message: String = "")