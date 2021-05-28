package com.example.Jachi3kki.InternalDB

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fridgeIngredient")
data class FridgeIngredient(
    @PrimaryKey @NonNull @ColumnInfo(name = "id") val id: Int,                       //이미지 쉽게 가져오려고 넣은 거
    @NonNull @ColumnInfo(name = "name") val name: String,                               //재료 이름(기본키)
    @ColumnInfo(name = "img_src") val img_src: String?,         //재료 이미지 경로(drawable에서 가져옴)
    @NonNull @ColumnInfo(name = "activate_check") val activation: Int    //해당 재료 활성화 여부(냉장고에 있는지 0 or 1)
)

