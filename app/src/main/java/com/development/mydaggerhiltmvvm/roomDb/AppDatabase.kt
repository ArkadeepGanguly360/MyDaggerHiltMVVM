package com.development.mydaggerhiltmvvm.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.PhoneContactModel

@Database(entities = [PhoneContactModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDbInstance(context: Context): AppDatabase {
            if(DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext, AppDatabase::class.java, "MYDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
}