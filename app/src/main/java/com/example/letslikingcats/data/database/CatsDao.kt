package com.example.letslikingcats.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.letslikingcats.data.database.dbentity.CatEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CatsDao {

    @Query("SELECT * FROM catentity")
    fun getAll(): Observable<List<CatEntity>>

    @Insert
    fun insertAll(vararg catEntity: CatEntity): Completable

    @Query("SELECT EXISTS(SELECT * FROM catentity WHERE id=:id)")
    fun isExists(id: String) : Single<Boolean>

    @Delete
    fun delete(catEntity: CatEntity): Completable

    @Query("UPDATE catentity SET favorite='true' WHERE id=:idEntity")
    fun favorite(idEntity: String): Completable

    @Query("UPDATE catentity SET favorite='false' WHERE id=:idEntity")
    fun unFavorite(idEntity: String): Completable

}