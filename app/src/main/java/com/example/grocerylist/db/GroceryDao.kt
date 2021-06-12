package com.example.grocerylist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.grocerylist.model.Grocery

@Dao
interface GroceryDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addGrocery(grocery: Grocery)

  @Delete
  suspend fun delete(grocery: Grocery)

  @Update
  suspend fun editGrocery(grocery: Grocery)

  @Query("SELECT * FROM grocery")
  fun getAllGroceriesList(): LiveData<List<Grocery>>

  @Query("SELECT * FROM grocery WHERE grocery_status = :groceryStatus")
  fun getRequiredGroceriesList(groceryStatus: String): LiveData<List<Grocery>>
}