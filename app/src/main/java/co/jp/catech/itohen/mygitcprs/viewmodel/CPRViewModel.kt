package co.jp.catech.itohen.mygitcprs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import co.jp.catech.itohen.mygitcprs.data.CPRModel
import co.jp.catech.itohen.mygitcprs.data.Repository
import co.jp.catech.itohen.mygitcprs.network.ApiService
import retrofit2.Response

class CPRViewModel : ViewModel() {

    var liveCPRDisplayModel = MutableLiveData<CPRDisplayModel>()
    var repo = Repository()

    private val responseObserver = Observer<Response<List<CPRModel>?>> { response ->

        val dataModel: CPRDisplayModel = if(response.code() == ApiService.success) {
            CPRDisplayModel(success = true, showProgress = false, data = response.body())
        } else {
            CPRDisplayModel(success = false, showProgress = false, message = response.message())
        }
        liveCPRDisplayModel.postValue(dataModel)
    }

    init {
        repo.liveData.observeForever(responseObserver)
    }

    fun fetchPrList(owner: String, repoName: String, prStatus: String, sort: String = "created", sortDirection: String = "desc", page: Int = 0, perPage: Int = 50) {

        if(owner.isNotBlank() && repoName.isNotBlank()) {
            liveCPRDisplayModel.postValue(CPRDisplayModel(showProgress = true))
            repo.fetchPrList(owner, repoName, prStatus, sort = sort, sortDirection = sortDirection, page = page, perPage = perPage)
        }
        else
            liveCPRDisplayModel.postValue(CPRDisplayModel(message = "Please fill in required fields"))
    }

    override fun onCleared() {
        repo.liveData.removeObserver(responseObserver)
        super.onCleared()
    }
}

//Model for the activity screen
class CPRDisplayModel(var success: Boolean = false, var showProgress: Boolean = false, var data: List<CPRModel>? = null, var message: String = "")