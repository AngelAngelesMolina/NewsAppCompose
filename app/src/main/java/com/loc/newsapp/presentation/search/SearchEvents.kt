package com.loc.newsapp.presentation.search

sealed class SearchEvents {

    data class UpdateSearchQuery(val searchQuery: String) : SearchEvents()
    object SearchNews : SearchEvents()
    object NavigateUp : SearchEvents()
    data class OnError(val message: String) : SearchEvents()

}