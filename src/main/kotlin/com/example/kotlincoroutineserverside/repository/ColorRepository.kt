package com.example.kotlincoroutineserverside.repository

import com.example.kotlincoroutineserverside.domain.Color
import com.example.kotlincoroutineserverside.exception.DatabaseOperationFailureException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.findAndRemove
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class ColorRepository(private val template: ReactiveMongoTemplate) : IColorRepository {
    override fun findAll(): Flow<Color> {
        return template.findAll<Color>().asFlow()
    }

    override suspend fun findByCode(code: String): Color {
        return template
                .findOne<Color>(Query(Color::code isEqualTo code))
                .awaitFirstOrNull()
                ?: throw DatabaseOperationFailureException("[詳細取得失敗]カラーコードーがDBに存在しません")
    }

    override suspend fun save(color: Color): Color {
        template
                .findOne<Color>(Query(Color::code isEqualTo color.code))
                .awaitFirstOrNull()
                ?.let { throw DatabaseOperationFailureException("[作成失敗]同じカラーコードが既に存在します")}

        return template
                .save(color)
                .awaitFirst()
    }

    override suspend fun deleteByCode(code: String) {
        template
                .findAndRemove<Color>(Query(Color::code isEqualTo code))
                .awaitFirstOrNull()
                ?: throw IllegalArgumentException("[削除失敗]カラーコードがDBに存在しません")
    }
}