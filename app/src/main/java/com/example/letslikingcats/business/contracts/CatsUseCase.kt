package com.example.letslikingcats.business.contracts

import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.business.paginator.Paginator

interface CatsUseCase {

    fun cats() : Paginator<Cat>

}