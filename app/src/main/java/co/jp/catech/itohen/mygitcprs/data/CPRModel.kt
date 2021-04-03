package co.jp.catech.itohen.mygitcprs.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class CPRModel(
    @field:SerializedName("title")
    var title: String,
    @field:SerializedName("created_at")
    var createdDate: Date?,
    @field:SerializedName("closed_at")
    var closedDate: Date?,
    @field:SerializedName("user")
    var user: User? = null
)

data class User(
    @field:SerializedName("login")
    var name: String,
    @field:SerializedName("avatar_url")
    var avatarImg: String)