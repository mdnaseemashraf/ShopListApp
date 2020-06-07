package com.mdnaseemashraf.shoplist.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ShopItemRepository(application: Application) {

    private var ShopDatabaseDao: ShopDatabaseDao

    private var allShopItems: LiveData<List<ShopItem>>

    init {
        val database: ShopDatabase = ShopDatabase.getInstance(
            application.applicationContext
        )!!
        ShopDatabaseDao = database.shopDao()
        allShopItems = ShopDatabaseDao.getAllItems()
    }

    fun insert(ShopItem: ShopItem) {
        val insertShopItemAsyncTask = InsertShopItemAsyncTask(ShopDatabaseDao).execute(ShopItem)
    }

    fun update(ShopItem: ShopItem) {
        val updateShopItemAsyncTask = UpdateShopItemAsyncTask(ShopDatabaseDao).execute(ShopItem)
    }


    fun delete(ShopItem: ShopItem) {
        val deleteShopItemAsyncTask = DeleteShopItemAsyncTask(ShopDatabaseDao).execute(ShopItem)
    }

    fun deleteAllShopItems() {
        val deleteAllShopItemsAsyncTask = DeleteAllShopItemsAsyncTask(
            ShopDatabaseDao
        ).execute()
    }

    fun getAllShopItems(): LiveData<List<ShopItem>> {
        return allShopItems
    }

    companion object {
        private class InsertShopItemAsyncTask(ShopDatabaseDao: ShopDatabaseDao) : AsyncTask<ShopItem, Unit, Unit>() {
            val ShopDatabaseDao = ShopDatabaseDao

            override fun doInBackground(vararg p0: ShopItem?) {
                ShopDatabaseDao.insert(p0[0]!!)
            }
        }

        private class UpdateShopItemAsyncTask(ShopDatabaseDao: ShopDatabaseDao) : AsyncTask<ShopItem, Unit, Unit>() {
            val ShopDatabaseDao = ShopDatabaseDao

            override fun doInBackground(vararg p0: ShopItem?) {
                ShopDatabaseDao.update(p0[0]!!)
            }
        }

        private class DeleteShopItemAsyncTask(ShopDatabaseDao: ShopDatabaseDao) : AsyncTask<ShopItem, Unit, Unit>() {
            val ShopDatabaseDao = ShopDatabaseDao

            override fun doInBackground(vararg p0: ShopItem?) {
                ShopDatabaseDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllShopItemsAsyncTask(ShopDatabaseDao: ShopDatabaseDao) : AsyncTask<Unit, Unit, Unit>() {
            val ShopDatabaseDao = ShopDatabaseDao

            override fun doInBackground(vararg p0: Unit?) {
                ShopDatabaseDao.deleteAllItems()
            }
        }
    }
}