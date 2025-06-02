package com.loc.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.loc.newsapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)


val pages = listOf(
    Page(
        title = "Explore", description = "Discover the latest news and stay updated", image = R
            .drawable.onboarding1
    ),
    Page(
        title = "Explore", description = "Discover the latest news and stay updated", image = R
            .drawable.onboarding2
    ),
    Page(
        title = "Explore", description = "Discover the latest news and stay updated", image = R
            .drawable.onboarding3
    ),
)