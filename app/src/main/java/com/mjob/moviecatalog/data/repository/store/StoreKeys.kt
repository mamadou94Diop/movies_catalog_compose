package com.mjob.moviecatalog.data.repository.store

object PlatformKey
object MovieKey
sealed class ShowKeys {
    sealed class Read : ShowKeys() {
        object All : Read()
    }
}