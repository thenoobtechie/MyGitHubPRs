package co.jp.catech.itohen.mygitcprs.data

import androidx.lifecycle.MutableLiveData
import co.jp.catech.itohen.mygitcprs.network.ApiService
import co.jp.catech.itohen.mygitcprs.viewmodel.CPRDisplayModel
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class Repository {

    private val service = ApiService.getRetrofitService()
    val liveData = MutableLiveData<Response<List<CPRModel>?>>()

    fun fetchPrList(owner: String, repoName: String, prStatus: String, sort: String, sortDirection: String, page: Int, perPage: Int) {

        val call = service.listRepos(owner, repoName, prStatus, sort = sort, sortDirection = sortDirection, page = page, perPage = perPage)

        call?.enqueue(object : Callback<List<CPRModel>?> {
            override fun onResponse(
                call: Call<List<CPRModel>?>,
                response: Response<List<CPRModel>?>
            ) {
                liveData.postValue(response)
            }

            override fun onFailure(call: Call<List<CPRModel>?>, t: Throwable) {

                liveData.postValue(Response.error(ApiService.failure, ResponseBody.create(MediaType.get("string"), t.message ?: "")))
            }
        })
    }

}