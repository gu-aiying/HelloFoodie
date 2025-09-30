package com.example.hellofoodie.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "extended_ingredients",
    primaryKeys = ["recipeId", "id"], // композитный первичный ключ (один рецепт может иметь много ингредиентов, и один ингредиент может содержаться во многих рецептах)
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class, // родительский объект, на который ссылается данный объект
            parentColumns = ["id"], // название колонки в родительском объекте
            childColumns = ["recipeId"], // название колонки в данном объекте для связи
            onDelete = ForeignKey.CASCADE // // удаление рецепта приведет к удалению его ингредиентов
        )
    ],
    indices = [Index("recipeId")]
)
data class ExtendedIngredientEntity(
    // Внешний ключ, который связывает ингредиент с конкретным рецептом
    val recipeId: Long,

    // Id ингредиента из API
    val id: Long,

    val image: String?,
    val name: String,
    val original: String,
    val originalName: String,
    val amount: Double,
    val unit: String?
)
