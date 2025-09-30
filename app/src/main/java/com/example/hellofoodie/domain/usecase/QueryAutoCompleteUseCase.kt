package com.example.hellofoodie.domain.usecase

import com.example.hellofoodie.domain.model.QueryAutoComplete
import com.example.hellofoodie.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class QueryAutoCompleteUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String): Flow<Result<List<QueryAutoComplete>>> =
        flow {
            val autoCompleteList = searchRepository.searchAutoComplete(query)
            emit(Result.success(autoCompleteList))
        }.catch { e ->
            emit(Result.failure(e))
        }.flowOn(Dispatchers.IO)
}
