package com.development.mydaggerhiltmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
data class PhoneContactModel(
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "phNo")
    val phNo: String?,
    @ColumnInfo(name = "photo")
    val photo: String?
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}
