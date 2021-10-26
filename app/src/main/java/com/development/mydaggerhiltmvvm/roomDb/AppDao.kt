package com.development.mydaggerhiltmvvm.roomDb

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.PhoneContactModel

@Dao
interface AppDao {

   /* @Query("SELECT * FROM Contacts ORDER BY name ASC")
    fun getAllRecords(): ArrayList<PhoneContactModel>*/


    @Insert
    fun insertRecord(phoneContactModel: PhoneContactModel)
}