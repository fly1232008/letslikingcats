package com.example.letslikingcats.business.cats

import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.business.paginator.SinglePagePaginator
import com.example.letslikingcats.business.contracts.CatsUseCase
import com.example.letslikingcats.business.paginator.Paginator
import com.example.letslikingcats.data.database.CatsDao
import javax.inject.Inject

class FavoriteCatsUseCase @Inject constructor(private val catsDao: CatsDao): CatsUseCase {

    private val catMapper = CatMapper()

    override fun cats(): Paginator<Cat> {
        return SinglePagePaginator {
            catsDao.getAll().map { list ->
                list.map {
                    catMapper.from(it)
                }
            }
        }
    }
}