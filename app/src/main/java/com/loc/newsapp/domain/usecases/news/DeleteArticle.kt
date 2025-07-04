package com.loc.newsapp.domain.usecases.news

import com.loc.newsapp.data.local.NewsDao
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteArticle @Inject constructor(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article)
    }

}