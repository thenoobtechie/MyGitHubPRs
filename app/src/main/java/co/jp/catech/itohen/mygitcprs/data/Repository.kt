package co.jp.catech.itohen.mygitcprs.data

import androidx.lifecycle.MutableLiveData
import co.jp.catech.itohen.mygitcprs.network.ApiService
import retrofit2.Callback
import retrofit2.Response

class Repository(private var service: ApiService) {

    private var liveApiResponse: MutableLiveData<Response<List<CPRModel>?>> = MutableLiveData<Response<List<CPRModel>?>>()

    fun getCPRLiveData() = liveApiResponse

    fun fetchPrList(owner: String, repoName: String, prStatus: String, sortDirection: String, callback: Callback<List<CPRModel>?>) {

        val call = service.listRepos(owner, repoName, prStatus, sortDirection = sortDirection)

        call?.enqueue(callback)
    }

}