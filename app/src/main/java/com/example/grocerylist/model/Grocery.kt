package com.example.grocerylist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.grocerylist.enum.GroceryStatus

@Entity(tableName = "grocery")
data class Grocery (
  @PrimaryKey(autoGenerate = true) var id: Int ?= null,
  @ColumnInfo(name = "grocery_name") var _groceryName: String,
  @ColumnInfo(name = "grocery_price") var _groceryPrice: Double,
  @ColumnInfo(name = "grocery_status") var _groceryStatus: String
)