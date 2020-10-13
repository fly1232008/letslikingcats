package com.example.letslikingcats.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.letslikingcats.data.database.dbentity.CatEntity

@Database(entities = [
    CatEntity::class
],
    version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun catsDao(): CatsDao

}