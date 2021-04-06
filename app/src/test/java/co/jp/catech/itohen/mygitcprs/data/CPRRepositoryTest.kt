package co.jp.catech.itohen.mygitcprs.data

import androidx.lifecycle.MutableLiveData
import co.jp.catech.itohen.mygitcprs.network.CPRApiService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.TestCase
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CPRRepositoryTest : TestCase() {

    val testUserName = "dummyUser"
    val testRepo = "dummyRepo"
    val testState = "dummyState"

    @Test
    fun testFetchPrList() {

        val service = mock<CPRApiService>()
        val liveData = mock<MutableLiveData<Response<List<CPRModel>?>>>()
        val repo = CPRRepository(service)

        repo.fetchPrList(
            RequestModel(
                testUserName,
                testRepo,
                testState,
                sortDirection = "desc"
            ),
            callback = object : Callback<List<CPRModel>?> {
                override fun onResponse(
                    call: Call<List<CPRModel>?>,
                    response: Response<List<CPRModel>?>
                ) {
                    assertTrue(response.isSuccessful)
                }

                override fun onFailure(call: Call<List<CPRModel>?>, t: Throwable) {

                    /*updateData(Response.error(ApiService.failure, ResponseBody.create(
                        MediaType.get("string"), t.message ?: "")))*/
                }
            })
        verify(service).listRepos(testUserName, testRepo, testState)
    }

    fun testGetLiveData() {}

    fun testSetLiveData() {}
}