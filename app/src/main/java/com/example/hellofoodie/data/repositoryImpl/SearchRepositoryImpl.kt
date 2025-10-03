package com.example.hellofoodie.data.repositoryImpl

import com.example.hellofoodie.data.local.dao.RecipeDao
import com.example.hellofoodie.data.remote.api.SpooncularApiService
import com.example.hellofoodie.di.NetworkMonitor
import com.example.hellofoodie.domain.model.QueryAutoComplete
import com.example.hellofoodie.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val recipeDao: RecipeDao,
    private val spooncularApiService: SpooncularApiService
) : SearchRepository {
    override suspend fun searchAutoComplete(query: String): List<QueryAutoComplete> {

        if (!networkMonitor.isOnline()) {
            // Поиск в локальной БД без интернета
            return searchRecipesFromDB(query)
        }
        return try {
            spooncularApiService.autoComplete(query)
        } catch (e: Exception) {
            searchRecipesFromDB(query)
        }
    }

    private suspend fun searchRecipesFromDB(query: String): List<QueryAutoComplete> {

        return recipeDao.queryAutoComplete(query)
            .map {
                QueryAutoComplete(
                    id = it.id,
                    title = it.title
                )
            }.take(10)
    }

}