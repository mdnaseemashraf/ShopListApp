package com.mdnaseemashraf.shoplist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mdnaseemashraf.shoplist.database.ShopItem
import com.mdnaseemashraf.shoplist.database.ShopItemRepository

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: ShopItemRepository = ShopItemRepository(application)
    private var allShopItems: LiveData<List<ShopItem>> = repository.getAllShopItems()

    fun insert(shopItem: ShopItem) {
        repository.insert(shopItem)
    }

    fun update(shopItem: ShopItem) {
        repository.update(shopItem)
    }

    fun delete(shopItem: ShopItem) {
        repository.delete(shopItem)
    }

    fun deleteAllItems() {
        repository.deleteAllShopItems()
    }

    fun getAllItems(): LiveData<List<ShopItem>> {
        return allShopItems
    }
}