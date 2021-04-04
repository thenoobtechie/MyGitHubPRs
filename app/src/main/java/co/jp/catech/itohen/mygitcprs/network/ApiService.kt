package co.jp.catech.itohen.mygitcprs.network

import co.jp.catech.itohen.mygitcprs.Utility
import co.jp.catech.itohen.mygitcprs.data.CPRModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    companion object {

        const val success = 200
        const val failure = -1
        private const val baseURL = "https://api.github.com/"

        fun getRetrofitService(): ApiService {

            val gson = GsonBuilder().setDateFormat(Utility.serverDateFormat).create()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

    @GET("/repos/{owner}/{repo}/pulls")
    fun listRepos(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") status: String = "closed",
        @Query("sort") sort: String = "created",
        @Query("direction") sortDirection: String = "desc"
    ): Call<List<CPRModel>?>?
}