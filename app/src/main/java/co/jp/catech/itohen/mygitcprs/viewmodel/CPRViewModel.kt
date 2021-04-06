package co.jp.catech.itohen.mygitcprs.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jp.catech.itohen.mygitcprs.Constants.Companion.API_RESPONSE_LOG_TAG
import co.jp.catech.itohen.mygitcprs.Constants.Companion.INVALID_REQUEST_ERROR
import co.jp.catech.itohen.mygitcprs.data.CPRModel
import co.jp.catech.itohen.mygitcprs.data.CPRRepository
import co.jp.catech.itohen.mygitcprs.data.RequestModel
import co.jp.catech.itohen.mygitcprs.network.CPRApiCallbackHandler
import co.jp.catech.itohen.mygitcprs.network.CPRApiService
import retrofit2.Response

class CPRViewModel(private val cPRRepository: CPRRepository) : ViewModel() {

    val liveCPRDisplayModel =
        MutableLiveData<CPRDisplayModel>()

    private val fnUpdateData = fun(response: Response<List<CPRModel>?>) {
        Log.v(
            API_RESPONSE_LOG_TAG,
            "Data fetch completed, Response-isSuccess : ${response.isSuccessful}"
        )
        val dataModel: CPRDisplayModel = if (response.code() == CPRApiService.success) {
            CPRDisplayModel(data = response.body())
        } else {
            CPRDisplayModel(message = response.message())
        }
        liveCPRDisplayModel.postValue(dataModel)
    }

    private val cprApiCallbackHandler: CPRApiCallbackHandler =
        CPRApiCallbackHandler(updateLiveData = fnUpdateData)

    fun fetchPRList(
        requestModel: RequestModel
    ) {

        val validationData = requestModel.validateData()
        if (validationData.first) {

            liveCPRDisplayModel.postValue(CPRDisplayModel(showProgress = true))
            Log.v(API_RESPONSE_LOG_TAG, validationData.second)
            cPRRepository.fetchPrList(requestModel, callback = cprApiCallbackHandler)
        } else {
            Log.v(API_RESPONSE_LOG_TAG, INVALID_REQUEST_ERROR)
            invalidRequestError(errorMessage = validationData.second)
        }
    }

    fun invalidRequestError(errorMessage: String) {

        liveCPRDisplayModel.postValue(CPRDisplayModel(message = errorMessage))
    }
}

class CPRDisplayModel(
    var showProgress: Boolean = false,
    var data: List<CPRModel>? = null,
    var message: String = ""
)