package com.mdnaseemashraf.shoplist.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ShopItem::class], version = 1,  exportSchema = false)
abstract class ShopDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDatabaseDao


    companion object {
        private var instance: ShopDatabase? = null

        fun getInstance(context: Context): ShopDatabase? {
            if (instance == null) {
                synchronized(ShopDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShopDatabase::class.java, "shop_database"
                    )
                        .fallbackToDestructiveMigration() // deletes db and creates new db on version change
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                ShopDatabase.PopulateDbAsyncTask(instance).execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: ShopDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val shopDao = db?.shopDao()

        override fun doInBackground(vararg p0: Unit?) {
            shopDao?.insert(ShopItem("Apple", false))
            shopDao?.insert(ShopItem("Bananas", false))
            shopDao?.insert(ShopItem("Mangos", false))
        }
    }

}