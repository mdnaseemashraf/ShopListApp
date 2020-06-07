package com.mdnaseemashraf.shoplist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopitems_table")
data class ShopItem( var title: String, var isChecked: Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}