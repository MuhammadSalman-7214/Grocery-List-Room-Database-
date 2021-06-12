package com.example.grocerylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.grocerylist.db.GroceryDao
import com.example.grocerylist.db.GroceryDatabase
import com.example.grocerylist.model.Grocery
import kotlinx.coroutines.launch


class GroceryViewModel(application: Application) : AndroidViewModel(application) {

  private var database: GroceryDatabase =
    GroceryDatabase.getInstance(application.applicationContext)
  private var groceryDao: GroceryDao = database.groceryDao()
  private lateinit var groceryList: LiveData<List<Grocery>>

  /**
   * IMPOTANT NOTE
   *
   * We can call below functions in repository , data source class as well,
   * that will fetch data from db and we'll only observe the output in view model
   * but not creating Repository and Data Source Classes because of shortage of time
   * on my side as I have some other commitments as well
   */

  fun getAllGroceryList(): LiveData<List<Grocery>> {
    groceryList = groceryDao.getAllGroceriesList()
    return groceryList
  }

  fun getSpecificGroceryList(groceryStatus: String): LiveData<List<Grocery>> {
    groceryList = groceryDao.getRequiredGroceriesList(groceryStatus)
    return groceryList
  }

  fun insertGrocery(grocery: Grocery) {
    viewModelScope.launch {
      groceryDao.addGrocery(grocery)
    }
  }

  fun updateGroceryStatus(grocery: Grocery) {
    viewModelScope.launch {
      groceryDao.editGrocery(grocery)
    }
  }

  fun deleteGrocery(grocery: Grocery) {
    viewModelScope.launch {
      groceryDao.delete(grocery)
    }
  }
}