package com.example.Jachi3kki.InternalDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update

//RoomDB에 접근하는 메소드들
@Dao
interface IngredientDAO {
    @Query("SELECT * FROM fridgeIngredient")    //다 읽어오는 쿼리
    fun getAll(): MutableList<FridgeIngredient>

    @Query("SELECT * FROM fridgeIngredient WHERE name IN (:ingredientsNames)")  //이름으로 다 읽어오는 쿼리
    fun loadAllByNames(ingredientsNames: Array<String>): List<FridgeIngredient>

    @Query("UPDATE fridgeIngredient SET activate_check = 0 WHERE name = (:givenName)")
    fun deactivate(givenName: String)

    @Query("UPDATE fridgeIngredient SET activate_check = 1 WHERE name = (:givenName)")
    fun activate(givenName: String)

    @Update////활성화여부만 바꾸게 할 메소드. 짜피 roomdb에선 데이터 앵간하면 삭제할게 없음
    fun update(new: FridgeIngredient)

    @Delete//삭제 쿼리인데 쓸일이 없겠지만 일단은 구현
    fun delete(ingredient: FridgeIngredient)

}