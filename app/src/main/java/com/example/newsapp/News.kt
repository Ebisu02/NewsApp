package com.example.newsapp

import androidx.lifecycle.MutableLiveData

// Model
data class News(val title: String, val likes: MutableLiveData<Int>)