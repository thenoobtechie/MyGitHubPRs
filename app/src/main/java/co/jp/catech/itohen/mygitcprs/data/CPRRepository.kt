package co.jp.catech.itohen.mygitcprs.data

import co.jp.catech.itohen.mygitcprs.network.CPRApiService
import retrofit2.Callback

class CPRRepository(private var cprApiService: CPRApiService) {

    fun fetchPrList(requestModel: RequestModel, callback: Callback<List<CPRModel>?>) {

        cprApiService.listRepos(
            requestModel.owner,
            requestModel.repoName,
            requestModel.prStatus,
            sortDirection = requestModel.sortDirection
        )
            ?.enqueue(callback)
    }

}