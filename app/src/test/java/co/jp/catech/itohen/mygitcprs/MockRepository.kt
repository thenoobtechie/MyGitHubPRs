package co.jp.catech.itohen.mygitcprs

import androidx.lifecycle.MutableLiveData
import co.jp.catech.itohen.mygitcprs.data.CPRModel
import co.jp.catech.itohen.mygitcprs.data.IRepository
import co.jp.catech.itohen.mygitcprs.data.User
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
import java.util.*
import kotlin.coroutines.CoroutineContext

class MockRepository(private var service: ApiService, var liveData: MutableLiveData<Response<List<CPRModel>?>>): IRepository {

//    private val service = ApiService.getRetrofitService()
//    override var liveData = MutableLiveData<Response<List<CPRModel>?>>()

    override fun fetchPrList(owner: String, repoName: String, prStatus: String, sort: String, sortDirection: String, page: Int, perPage: Int, callback: Callback<List<CPRModel>?>) {

        val cprList = arrayListOf<CPRModel>()
        cprList.add(CPRModel("Some random title", Date(), Date(), User("Kid Francescoli", "")))
        val data: Response<List<CPRModel>?> = Response.success(null)
        liveData.postValue(data)
    }

}