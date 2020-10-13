package com.example.letslikingcats.business.cats

import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.business.paginator.SimplePaginator
import com.example.letslikingcats.business.contracts.CatsUseCase
import com.example.letslikingcats.business.paginator.Paginator
import com.example.letslikingcats.data.CatsRepo
import com.example.letslikingcats.data.database.CatsDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AllCatsUseCase @Inject constructor(private val catsRepo: CatsRepo, private val catsDao: CatsDao):
    CatsUseCase {

    private val catMapper = CatMapper()

    override fun cats(): Paginator<Cat> {
        return SimplePaginator { offset ->
            Observable.combineLatest(
                catsDao.getAll().map { list -> list.associateBy({ it.id }, { it }).toMap() },
                catsRepo.load(offset),
                {t1, t2 -> Pair(t1, t2)})
                .observeOn(Schedulers.computation())
                .map { (entityMap, dtos) ->
                    dtos.map { catDto -> catMapper.from(catDto, entityMap[catDto.id]) }
                }
                .observeOn(Schedulers.io())
        }
    }

}