package  com.development.mydaggerhiltmvvm.model

import com.google.gson.annotations.SerializedName

data class FriendsData(
    @SerializedName("_id") val _id : String,
    @SerializedName("from_id") val from_id : String,
    @SerializedName("to_id") val to_id : String,
    @SerializedName("status") val status : String,
    @SerializedName("isDeleted") val isDeleted : Boolean,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("__v") val __v : Int,
    @SerializedName("friendDetails") val friendDetails : FriendDetails
)
