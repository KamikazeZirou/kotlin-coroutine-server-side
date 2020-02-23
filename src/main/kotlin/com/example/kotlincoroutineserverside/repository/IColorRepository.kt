package com.example.kotlincoroutineserverside.repository

import com.example.kotlincoroutineserverside.domain.Color
import kotlinx.coroutines.flow.Flow

interface IColorRepository {
    fun findAll(): Flow<Color>
    suspend fun findByCode(code: String): Color
    suspend fun save(color: Color): Color
    suspend fun deleteByCode(code: String)
}