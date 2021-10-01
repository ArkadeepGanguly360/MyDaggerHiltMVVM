package  com.development.mydaggerhiltmvvm.model

import com.google.gson.annotations.SerializedName

data class FriendsResponse(
    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : List<FriendsData>,
    @SerializedName("message") val message : String
)
