package com.mjob.moviecatalog.domain.model

data class Catalog(
    val favorites: List<Int>,
    val contentsGroups: List<ContentGroup>
)

data class ContentGroup(
    val name: String,
    val contents: List<Content>
)