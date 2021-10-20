package com.development.mydaggerhiltmvvm.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Model")
data class model(
    @ColumnInfo(name = "username")
    var Username: String,

    @ColumnInfo(name = "password")
    var Password: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}
