package com.example.kotlincoroutineserverside.exception

import java.lang.RuntimeException

class DatabaseOperationFailureException(message: String = "") : RuntimeException(message) {
}