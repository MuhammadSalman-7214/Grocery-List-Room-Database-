package com.example.grocerylist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(val app: Application) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return GroceryViewModel(app) as T
    }
    throw IllegalArgumentException("Unable to construct viewmodel")
  }
}