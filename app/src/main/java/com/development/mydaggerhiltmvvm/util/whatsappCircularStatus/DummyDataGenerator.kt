package com.development.mydaggerhiltmvvm.util.whatsappCircularStatus

import com.development.mydaggerhiltmvvm.model.WhatsappStatus
import com.development.mydaggerhiltmvvm.model.WhatsappUser
import java.util.*

object DummyDataGenerator {

    fun generateStatuses(): List<WhatsappUser> {
        val userStatusList: MutableList<WhatsappUser> = ArrayList<WhatsappUser>()
        for (i in 0..19) {
            val statusList: MutableList<WhatsappStatus> = ArrayList<WhatsappStatus>()
            for (j in 0 until getCount(i)) {
                statusList.add(WhatsappStatus(generateIsSeenForStatus(j, i)))
            }
            val userStatus = WhatsappUser(userStatusList[i].userImage, userStatusList[i].userName, genereateAreAllSeen(i), statusList)
            userStatusList.add(userStatus)
        }
        return userStatusList
    }

    fun getCount(i: Int): Int {
        return when (i) {
            0 -> 1
            1 -> 4
            2 -> 2
            3 -> 8
            4 -> 3
            else -> 6
        }
    }

    fun genereateAreAllSeen(i: Int): Boolean {
        return when (i) {
            0 -> false
            1 -> false
            2 -> true
            3 -> true
            else -> false
        }
    }

    fun generateIsSeenForStatus(i: Int, j: Int): Boolean {
        if (j == 0) {
            return false
        }
        return if (j == 1) {
            i == 0
        } else false
    }
}
