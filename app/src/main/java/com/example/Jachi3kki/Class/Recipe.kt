package com.example.Jachi3kki.Class

data class Recipe (
    val id: Int, val name: String, val content: String, val category: String, val img_src: String, val ingredientArr: ArrayList<String>)