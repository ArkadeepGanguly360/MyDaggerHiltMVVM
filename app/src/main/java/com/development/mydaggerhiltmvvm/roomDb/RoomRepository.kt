package com.development.mydaggerhiltmvvm.roomDb

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.PhoneContactModel
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao) {

   /* fun getAllRecords(): ArrayList<PhoneContactModel> {
        return appDao.getAllRecords()
    }*/

    fun insertRecord(phoneContactModel: PhoneContactModel) {
        appDao.insertRecord(phoneContactModel)
    }
}