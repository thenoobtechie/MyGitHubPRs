package co.jp.catech.itohen.mygitcprs.data

import co.jp.catech.itohen.mygitcprs.Constants.Companion.INVALID_REQUEST_ERROR

data class RequestModel(val owner: String,
                        val repoName: String,
                        val prStatus: String = "closed",
                        val sortDirection: String = "desc") {

    fun validateData(): Pair<Boolean, String> {
        return if (owner.isNotBlank() && repoName.isNotBlank()) Pair(true, "Calling api with : $owner && $repoName")
        else Pair(false, INVALID_REQUEST_ERROR)
    }
}