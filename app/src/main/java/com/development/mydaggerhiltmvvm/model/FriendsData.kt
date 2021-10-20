package  com.development.mydaggerhiltmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Friends")
data class FriendsData(
    @ColumnInfo(name = "_id")
    @SerializedName("_id") val _id : String,
    @ColumnInfo(name = "from_id")
    @SerializedName("from_id") val from_id : String,
    @ColumnInfo(name = "to_id")
    @SerializedName("to_id") val to_id : String,
    @ColumnInfo(name = "status")
    @SerializedName("status") val status : String,
    @ColumnInfo(name = "isDeleted")
    @SerializedName("isDeleted") val isDeleted : Boolean,
    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt") val createdAt : String,
    @ColumnInfo(name = "updatedAt")
    @SerializedName("updatedAt") val updatedAt : String,
    @ColumnInfo(name = "__v")
    @SerializedName("__v") val __v : Int,
    @ColumnInfo(name = "friendDetails")
    @SerializedName("friendDetails") val friendDetails : FriendDetails
) : Serializable
{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}
