package com.example.newsapp

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

// ViewModel
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
            News("Отец семейства привёл сыновей и приятелей, чтобы исполнить церемониальный танец для больного друга", MutableLiveData(0)),
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
