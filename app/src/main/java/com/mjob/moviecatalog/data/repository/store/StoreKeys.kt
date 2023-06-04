package com.mjob.moviecatalog.data.repository.store

object MovieKey
sealed class ShowKeys {
    sealed class Read : ShowKeys() {
        object All : Read()
    }
}