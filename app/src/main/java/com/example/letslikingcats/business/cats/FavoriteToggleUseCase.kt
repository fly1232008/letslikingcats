package com.example.letslikingcats.business.cats

import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.data.database.CatsDao
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteToggleUseCase @Inject constructor(private val catsDao: CatsDao) {

    private val catMapper = CatMapper()

    fun favorite(cat: Cat): Completable {
        val vaforitedCat = cat.copyWith(favorite = true)
        return catsDao.isExists(vaforitedCat.id)
            .flatMapCompletable {
                if (it) {
                    catsDao.favorite(vaforitedCat.id)
                } else {
                    catsDao.insertAll(catMapper.toEntity(vaforitedCat))
                }
            }
            .subscribeOn(Schedulers.io())

    }

    fun unFavorite(cat: Cat): Completable {
        val vaforitedCat = cat.copyWith(favorite = false)
        return catsDao.delete(catMapper.toEntity(vaforitedCat))
            .subscribeOn(Schedulers.io())
    }

}