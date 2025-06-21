package com.loc.newsapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity //table to our roomDatabase
data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source, //only primitive types can be converted to room
    val title: String,
    @PrimaryKey val url: String,
    val urlToImage: String
) : Parcelable