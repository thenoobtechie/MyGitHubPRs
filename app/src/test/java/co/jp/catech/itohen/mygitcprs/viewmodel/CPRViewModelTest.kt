package co.jp.catech.itohen.mygitcprs.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.jp.catech.itohen.mygitcprs.MainCoroutineRule
import co.jp.catech.itohen.mygitcprs.data.CPRRepository
import co.jp.catech.itohen.mygitcprs.data.RequestModel
import co.jp.catech.itohen.mygitcprs.getOrAwaitValueTest
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule


class CPRViewModelTest : TestCase() {

    private lateinit var mockRepo: CPRRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewmodel1: CPRViewModel

    @Before
    public override fun setUp() {

        mockRepo = mock()
        viewmodel1 = CPRViewModel(mockRepo)
//        MockitoAnnotations.initMocks(this)
//        `when`(mockRepo.getCPRLiveData()).thenReturn(MutableLiveData<Response<List<CPRModel>?>>())
    }

    public override fun tearDown() {}

    fun testGetLiveCPRDisplayModel() {}

    fun testSetLiveCPRDisplayModel() {}

    fun testFetchPrList_emptyOwner() {


        /*`when`(mockRepo.liveData).thenReturn(MutableLiveData())

            .thenAnswer { MutableLiveData<Response<List<CPRModel>?>>() }*/
//        viewmodel1.fetchPrList("", "xyz")
//        verify(viewmodel).invalidDataError()

        val value = viewmodel1.liveCPRDisplayModel.getOrAwaitValueTest()
        assert(value.message == "Please fill in required fields")
    }
    fun testFetchPrList_emptyRepo() {
        val viewmodel = CPRViewModel(mock())
        viewmodel.fetchPRList(RequestModel("xyz", ""))
        verify(viewmodel.invalidRequestError("validationData.second"))
    }
    fun testFetchPrList_emptyOwnerRepo() {
        val viewmodel = CPRViewModel(mock())
        viewmodel.fetchPRList(RequestModel("", ""))
        verify(viewmodel.invalidRequestError("validationData.second"))
    }
    fun testFetchPrList_correctData() {

        val userName = "xyz"
        val repoName = "xyz"
        val repo = mock<CPRRepository>()
        val viewmodel = CPRViewModel(repo)
        viewmodel.fetchPRList(RequestModel("", ""))
        verify(viewmodel).fetchPRList(RequestModel("", ""))
    }

    fun testInvalidDataError() {}

    fun testOnCleared() {}

    fun testGetRepo() {}

    fun testSetRepo() {}
}