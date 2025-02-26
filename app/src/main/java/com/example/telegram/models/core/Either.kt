package com.example.telegram.models.core

sealed class Either<out L, out R> {

    data class Error<L>(val error: L) : Either<L, Nothing>()
    data class Success<R>(val data: R) : Either<Nothing, R>()
}