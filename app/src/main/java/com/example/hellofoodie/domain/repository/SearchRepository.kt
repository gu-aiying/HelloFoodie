package com.example.hellofoodie.domain.repository

import com.example.hellofoodie.domain.model.QueryAutoComplete

interface SearchRepository {
    suspend fun searchAutoComplete(query: String): List<QueryAutoComplete>
}