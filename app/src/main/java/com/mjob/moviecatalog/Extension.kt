package com.mjob.moviecatalog

import java.util.Locale

fun Boolean?.orFalse() = this ?: false
fun Boolean.toInt() = if (this) 1 else 0
fun <E> List<E>.addOrRemove(element: E): List<E> {
    return this.toMutableList().apply {
        if (this.contains(element)) {
            this.remove(element)
        } else {
            this.add(element)
        }
    }
}

fun String.toCapital() : String {
    return  this.replaceFirstChar { text ->
        if (text.isLowerCase()) text.titlecase(
            Locale.getDefault()
        ) else text.toString()
    }
}