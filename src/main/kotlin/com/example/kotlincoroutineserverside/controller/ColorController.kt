package com.example.kotlincoroutineserverside.controller

import com.example.kotlincoroutineserverside.domain.Color
import com.example.kotlincoroutineserverside.exception.DatabaseOperationFailureException
import com.example.kotlincoroutineserverside.repository.ColorRepository
import com.example.kotlincoroutineserverside.repository.IColorRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*

@RestController
class ColorController(private val colorRepository: IColorRepository) {
    @GetMapping("/colors")
    fun getAll(): Flow<Color> = colorRepository.findAll()

    @GetMapping("/colors/{code}")
    suspend fun getByCode(@PathVariable code: String) = colorRepository.findByCode(code)

    @PostMapping("/colors")
    suspend fun post(@RequestBody color: Color) = colorRepository.save(color)

    @DeleteMapping("/colors/{code}")
    suspend fun delete(@PathVariable code: String) = colorRepository.deleteByCode(code)

    @ExceptionHandler(DatabaseOperationFailureException::class)
    fun handleDatabaseOperationFailureException(exception: DatabaseOperationFailureException)
        = "リクエストに誤りがあります: ${exception.message}"
}