package com.example.grocerylist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.grocerylist.model.Grocery

  @Database(entities = [Grocery::class], version = 2)
  abstract class GroceryDatabase : RoomDatabase() {
    abstract fun groceryDao(): GroceryDao

    companion object {
      private var instance: GroceryDatabase? = null

      private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
          database.execSQL("ALTER TABLE grocery"
            + " ADD COLUMN grocery_status TEXT NOT NULL")
        }
      }

      @Synchronized
      fun getInstance(ctx: Context): GroceryDatabase {
        if (instance == null)
          instance = Room.databaseBuilder(
            ctx.applicationContext, GroceryDatabase::class.java,
            "grocery_database"
          )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            //.addCallback(roomCallback)
            .build()

        return instance!!

      }
    }
  }