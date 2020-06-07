package com.mdnaseemashraf.shoplist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.mdnaseemashraf.shoplist.database.ShopItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    private lateinit var shopItemViewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val mBottomSheetDialog = BottomSheetDialog(this)

            val sheetView: View = this.layoutInflater.inflate(R.layout.bottom_sheet, null)
            mBottomSheetDialog.setContentView(sheetView)

            val inputTaskTitle: EditText = sheetView.edittext
            val saveButton: Button = sheetView.ButtonSave
            saveButton.text = getString(R.string.save)
            saveButton.setOnClickListener {
                val itemTitle = inputTaskTitle.text.toString()
                val newItem = ShopItem(itemTitle,false)
                shopItemViewModel.insert(newItem)

                //Toast.makeText(this, "ShopItem saved!", Toast.LENGTH_SHORT).show()
                
                if (mBottomSheetDialog.isShowing) {
                    mBottomSheetDialog.dismiss()
                    
                    val snackbar = Snackbar.make(sheetView,"$itemTitle " + getString(R.string.saved), Snackbar.LENGTH_LONG)
                    snackbar.setActionTextColor(resources.getColor(R.color.colorPrimary))
                    snackbar.show()
                }
            }

            mBottomSheetDialog.show()
        }

        items_recycler_view.layoutManager = LinearLayoutManager(this)
        items_recycler_view.setHasFixedSize(true)

        var adapter = ShopItemsAdapter()

        items_recycler_view.adapter = adapter

        shopItemViewModel = ViewModelProviders.of(this).get(ShopItemViewModel::class.java)

        shopItemViewModel.getAllItems().observe(this, Observer<List<ShopItem>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                shopItemViewModel.delete(adapter.getItemAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, getString(R.string.item_deleted), Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(items_recycler_view)

        adapter.setOnItemClickListener(object : ShopItemsAdapter.OnItemClickListener {
            override fun onItemClick(shopItem: ShopItem) {
                shopItem.isChecked = !shopItem.isChecked
                shopItemViewModel.update(shopItem)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_items -> {
                shopItemViewModel.deleteAllItems()
                Toast.makeText(this, getString(R.string.all_deleted), Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}