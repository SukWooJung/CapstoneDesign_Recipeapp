package com.example.Jachi3kki.InternalDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FridgeIngredient::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDAO(): IngredientDAO

    companion object {  //싱글톤 패턴 적용(db 중복 생성 방지)
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "fridgeIngredient"
                    ).allowMainThreadQueries().createFromAsset("databases/fridgeIngredient.db")
                        .build()   //미리 데이터 채우고 싶으면 .build 앞에 createFormAsset("db이름.db")넣으면 됨. 기본으로 assets 폴더로 정해져있음
                }
            }
            return INSTANCE
        }
    }
}