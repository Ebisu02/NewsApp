package com.example.newsapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData

// View
@Composable
fun NewsList(newsList: LiveData<List<News>>) {
    val list by newsList.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp)
            .border(BorderStroke(5.dp, Color.Black))
            .padding(3.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            content = {
                items(list.shuffled().distinct().take(4)) { news ->
                    NewsItem(news = news)
                }
            }
        )
    }
}