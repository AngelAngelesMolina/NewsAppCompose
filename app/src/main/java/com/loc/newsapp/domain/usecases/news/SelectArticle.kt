package com.loc.newsapp.domain.usecases.news

import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository

class SelectArticle(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(url: String): Article? = newsRepository.selectArticle(url)

}