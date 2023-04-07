package com.example.guessit.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(score : Int) : ViewModel() {
    private var _currentScore = MutableLiveData<Int>()
    val currentScore : LiveData<Int>
    get() = _currentScore

    private var _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain : LiveData<Boolean>
    get() = _eventPlayAgain

    init {
        _currentScore.value = score
    }

    fun onPlayAgain(){
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete(){
        _eventPlayAgain.value = false
    }
}