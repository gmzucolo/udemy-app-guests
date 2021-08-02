package com.gmzucolo.guests.service.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.service.repository.GuestDataBaseHelper
import com.gmzucolo.guests.service.constants.DataBaseConstants
import com.gmzucolo.guests.service.model.GuestModel

class GuestRepository private constructor(context: Context) {

    //Singleton
    private var mGuestDataBaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null
        return try {
            //connexion/reading db
            val db = mGuestDataBaseHelper.readableDatabase

            // raw query - no safe
            // db.rawQuery(select name, presence from GUEST where id = $id,  null)

            // projecting values
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            //query executing
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            //filling the guest
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                // getting name
                val name =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                // getting presence
                val presence =
                    (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)
                //creating new guest
                guest = GuestModel(id, name, presence)
            }
            // closing cursor
            cursor?.close()
            guest
        } catch (e: Exception) {
            guest
        }
    }

    fun save(guest: GuestModel): Boolean {
        return try {
            //connexion with db
            val db = mGuestDataBaseHelper.writableDatabase
            // saving data
            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)
            // inserting data
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {
            //connexion/reading db
            val db = mGuestDataBaseHelper.readableDatabase

            // projecting values
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            //query executing
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            //filling the list
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    //getting id
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    // getting name
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    // getting presence
                    val presence =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)
                    // creating new guest
                    val guest = GuestModel(id, name, presence)
                    // adding new guest
                    list.add(guest)
                }
            }
            // closing cursor
            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun getPresents(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {
            //connexion/reading db
            val db = mGuestDataBaseHelper.readableDatabase

            //query executing
            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            //filling the list
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    //getting id
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    // getting name
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    // getting presence
                    val presence =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)
                    // creating new guest
                    val guest = GuestModel(id, name, presence)
                    // adding new guest
                    list.add(guest)
                }
            }
            // closing cursor
            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun getAbsents(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {
            //connexion/reading db
            val db = mGuestDataBaseHelper.readableDatabase

            //query executing
            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            //filling the list
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    //getting id
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    // getting name
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    // getting presence
                    val presence =
                        (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)
                    // creating new guest
                    val guest = GuestModel(id, name, presence)
                    // adding new guest
                    list.add(guest)
                }
            }
            // closing cursor
            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    //Method CRUD - create, read, update, delete

    fun update(guest: GuestModel): Boolean {
        return try {
            //connexion with db
            val db = mGuestDataBaseHelper.writableDatabase
            // saving data
            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)
            // updating data
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, contentValues, selection, args)
            true
        } catch (e: Exception) {
            false
        }

    }

    fun delete(id: Int): Boolean {
        return try {
            //connexion with db
            val db = mGuestDataBaseHelper.writableDatabase
            // deleting data
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }
}