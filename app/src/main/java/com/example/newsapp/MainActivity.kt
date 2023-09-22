package com.example.newsapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsList(newsList = viewModel.newsList)
        }

        viewModel.startUpdatingNews()
    }
}

class NewsViewModel: ViewModel() {

    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> = _newsList

    private var currentNewsIndex = 0
    private val handler = Handler(Looper.getMainLooper())

    init {
        _newsList.value = listOf(
            News("Африка снизила расчет в рублях за экспорт из России", MutableLiveData(0)),
            News("Змея проскользнула в гостиную жилого дома и спряталась за фоторамкой", MutableLiveData(0)),
            News("Хозяин занялся серфингом вместе с питоном и был за это оштрафован", MutableLiveData(0)),
            News("Маленький пони упал с обрыва и погиб из-за любителей делать селфи", MutableLiveData(0)),
            News("Рекордсмен 667 раз вытатуировал на теле имя своей дочки", MutableLiveData(0)),
            News(
                "Отец семейства привёл сыновей и приятелей, чтобы исполнить церемониальный танец для больного друга",
                MutableLiveData(0)
            ),
            News("Хозяин сфотографировал своего пса, и получилась головоломка", MutableLiveData(0)),
            News("Коза родила мутанта, похожего на помесь поросёнка с человеком", MutableLiveData(0)),
            News("Очевидцев ужаснул медведь с 10-метровым ленточным червём", MutableLiveData(0)),
            News("Влюбившись в розовый цвет, автовладелица обернула свою машину розовой плёнкой", MutableLiveData(0))
        )
    }

    fun startUpdatingNews() {
        handler.post(object : Runnable {
            override fun run() {
                val randomIndex = Random.nextInt(0, 10)
                _newsList.value = _newsList.value?.replaceAt(
                    currentNewsIndex,
                    _newsList.value!![randomIndex].copy()
                )
                currentNewsIndex = (currentNewsIndex + 1) % 4
                handler.postDelayed(this, 5000)
            }
        })
    }
}

fun <T> List<T>.replaceAt(index: Int, value: T): List<T> {
    return mapIndexed { i, v -> if (i == index) value else v }
}

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

@Composable
fun NewsItem(news: News) {
    val likesState = news.likes.observeAsState(0)
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = news.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(0.8f)
                    .padding(end = 8.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Row(
                modifier = Modifier
                    .weight(0.2f)
                    .wrapContentWidth(Alignment.End)
                    .align(Alignment.Bottom)
            ) {
                Text(
                    text = "${likesState.value}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (likesState.value > 0) Color.Red else Color.Gray,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            news.likes.value = likesState.value + 1
                        }
                )
            }
        }
    }
}


data class News(val title: String, val likes: MutableLiveData<Int>)