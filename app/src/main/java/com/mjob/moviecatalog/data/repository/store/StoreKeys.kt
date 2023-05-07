package com.mjob.moviecatalog.data.repository.store

object GenreKey
object PlatformKey
object MovieKey
sealed class ShowKeys {
    sealed class Read : ShowKeys() {
        object All : Read()
        data class ById(val id: String) : Read()
    }
}