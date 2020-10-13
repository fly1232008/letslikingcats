package com.example.letslikingcats.business.cats

import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.data.database.dbentity.CatEntity
import com.example.letslikingcats.data.network.dto.CatDto

class CatMapper {

    fun from(catDto: CatDto, catEntity: CatEntity?): Cat {
        return Cat(
            id = catDto.id,
            url = catDto.url,
            width = catDto.width,
            height = catDto.height,
            favorite = catEntity?.favorite ?: false
        )
    }

    fun from(catEntity: CatEntity): Cat {
        return Cat(
            id = catEntity.id,
            url = catEntity.url,
            width = catEntity.width,
            height = catEntity.height,
            favorite = catEntity.favorite
        )
    }

    fun toEntity(cat: Cat): CatEntity {
        return CatEntity(
            id = cat.id,
            url = cat.url,
            width = cat.width,
            height = cat.height,
            favorite = cat.favorite
        )
    }

}

fun Cat.copyWith(favorite: Boolean): Cat {
    return Cat(
        id = id,
        url = url,
        width = width,
        height = height,
        favorite = favorite
    )
}