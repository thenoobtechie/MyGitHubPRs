package co.jp.catech.itohen.mygitcprs

import androidx.lifecycle.MutableLiveData
import co.jp.catech.itohen.mygitcprs.data.CPRModel
import co.jp.catech.itohen.mygitcprs.data.User
import co.jp.catech.itohen.mygitcprs.network.CPRApiService
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MockRepository(private var serviceCPR: CPRApiService, var liveData: MutableLiveData<Response<List<CPRModel>?>>) {

//    private val service = ApiService.getRetrofitService()
//    override var liveData = MutableLiveData<Response<List<CPRModel>?>>()

    fun fetchPrList(owner: String, repoName: String, prStatus: String, sortDirection: String, callback: Callback<List<CPRModel>?>) {

        val cprList = arrayListOf<CPRModel>()
        cprList.add(CPRModel("Some random title", Date(), Date(), User("Kid Francescoli", "")))
        val data: Response<List<CPRModel>?> = Response.success(null)
        liveData.postValue(data)
    }

}