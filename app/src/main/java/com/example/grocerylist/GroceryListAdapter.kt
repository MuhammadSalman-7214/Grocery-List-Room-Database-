package com.example.grocerylist

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.enum.GroceryStatus
import com.example.grocerylist.extension.hide
import com.example.grocerylist.extension.inflate
import com.example.grocerylist.extension.show
import com.example.grocerylist.model.Grocery
import kotlinx.android.synthetic.main.item_grocery_list.view.tvCompleteGrocery
import kotlinx.android.synthetic.main.item_grocery_list.view.tvGroceryName
import kotlinx.android.synthetic.main.item_grocery_list.view.tvGroceryPrice

class GroceryListAdapter(private val groceryList: List<Grocery>,
private val onGroceryItemCompleteListener: OnGroceryItemCompleteListener): RecyclerView.Adapter<GroceryListAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflatedView = parent.inflate(R.layout.item_grocery_list, false)
    return ViewHolder(inflatedView)

  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.tvGroceryName.text = groceryList[position]._groceryName
    holder.tvGroceryPrice.text = groceryList[position]._groceryPrice.toInt().toString()

    if (groceryList[position]._groceryStatus == GroceryStatus.PENDING.status) {
      holder.tvCompleteGrocery.show()
    } else {
      holder.tvCompleteGrocery.hide()
    }
  }

  override fun getItemCount(): Int {
    return groceryList.size
  }

  inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvGroceryName: TextView = itemView.tvGroceryName
    val tvGroceryPrice: TextView = itemView.tvGroceryPrice
    val tvCompleteGrocery: TextView = itemView.tvCompleteGrocery

    init {
      tvCompleteGrocery.setOnClickListener {
        onGroceryItemCompleteListener.onCompleteGroceryItem(groceryList[adapterPosition])
      }
    }
  }
}

interface OnGroceryItemCompleteListener {
  fun onCompleteGroceryItem(grocery: Grocery)
}