package com.mdnaseemashraf.shoplist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopDatabaseDao {

    @Insert
    fun insert(shopItem: ShopItem)

    @Update
    fun update(shopItem: ShopItem)

    @Delete
    fun delete(shopItem: ShopItem)

    @Query("DELETE FROM shopitems_table")
    fun deleteAllItems()

    @Query("SELECT * FROM shopitems_table")
    fun getAllItems(): LiveData<List<ShopItem>>

}
