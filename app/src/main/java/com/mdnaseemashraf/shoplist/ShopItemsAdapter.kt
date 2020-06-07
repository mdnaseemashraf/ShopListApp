package com.mdnaseemashraf.shoplist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdnaseemashraf.shoplist.database.ShopItem
import kotlinx.android.synthetic.main.shop_item_layout.view.*

class ShopItemsAdapter : ListAdapter<ShopItem, ShopItemsAdapter.ShopItemHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShopItem>() {
            override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
                return oldItem.id == newItem.id && oldItem.isChecked == newItem.isChecked
            }

            override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
                return oldItem.title == newItem.title && oldItem.isChecked == newItem.isChecked
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.shop_item_layout, parent, false)
        return ShopItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShopItemHolder, position: Int) {
        val currentShopItem: ShopItem = getItem(position)

        holder.textViewTitle.text = currentShopItem.title
        holder.checkBox.isChecked = currentShopItem.isChecked
    }

    fun getItemAt(position: Int): ShopItem {
        return getItem(position)
    }

    inner class ShopItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.tvItem
        var checkBox: CheckBox = itemView.chkDone
    }

    interface OnItemClickListener {
        fun onItemClick(ShopItem: ShopItem)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}