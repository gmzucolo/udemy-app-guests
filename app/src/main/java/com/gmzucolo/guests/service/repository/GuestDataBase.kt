package com.gmzucolo.guests.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmzucolo.guests.service.model.GuestModel
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = arrayOf(GuestModel::class), version = 1)
abstract class GuestDataBase : RoomDatabase() {

    abstract fun guestDAO(): GuestDAO

    companion object {
        private lateinit var INSTANCE: GuestDataBase

        @InternalCoroutinesApi
        fun getDataBase(context: Context): GuestDataBase {
            if (!::INSTANCE.isInitialized) {

                INSTANCE = Room.databaseBuilder(context, GuestDataBase::class.java, "guestDB")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }

}