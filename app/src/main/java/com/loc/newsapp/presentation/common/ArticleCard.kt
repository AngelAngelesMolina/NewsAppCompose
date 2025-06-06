package com.loc.newsapp.presentation.common

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.ColorRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.loc.newsapp.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.model.Source
import com.loc.newsapp.presentation.onboarding.Dimens.ArticleCardSize
import com.loc.newsapp.presentation.onboarding.Dimens.ExtraSmallPadding
import com.loc.newsapp.presentation.onboarding.Dimens.ExtraSmallPadding2
import com.loc.newsapp.presentation.onboarding.Dimens.SmallIconSize
import com.loc.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCard(modifier: Modifier = Modifier, article: Article, onClickListener: () -> Unit) {
    val context = LocalContext.current

    Row(modifier = modifier.clickable { onClickListener() }) {
        AsyncImage(
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(article.urlToImage).build(),
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding)
                .height(ArticleCardSize)
        ) {

            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis //desborde de texto
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(color = colorResource(id = R.color.body)),
                )
                Spacer(
                    modifier = Modifier.width(
                        ExtraSmallPadding2
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(
                    modifier = Modifier.width(
                        ExtraSmallPadding2
                    )
                )
                Text(
                    text = article.publishedAt,
                    style = MaterialTheme.typography.labelMedium.copy(color = colorResource(id = R.color.body)),
                )


            }
        }

    }

}

@Preview(showBackground = true, name = "lightMode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticlePrev() {
    NewsAppTheme {
        ArticleCard(
            article = Article(
                author = "",
                content = "",
                description = "",
                publishedAt = "15-12-23",
                source = Source(id = "", name = "BBC"),
                title = "Her traint broke down. Her phone died. And then she met her saver in a",
                url = "",
                urlToImage = ""
            )
        ) { }
    }

}