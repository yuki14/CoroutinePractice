package com.example.coroutinepractice.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    private val mCountUpTimer = MutableLiveData<Long>().also { it.value = 0 }
    val countUpTimer: LiveData<Long>
        get() = mCountUpTimer

    private val mCountDownTimer = MutableLiveData<Long>().also { it.value = 4000 }
    val countDownTimer: LiveData<Long>
        get() = mCountDownTimer

    private val mCountDownPreParationTimer = MutableLiveData<Long>().also { it.value = 5 }
    val countDownPreParationTimer: LiveData<Long>
        get() = mCountDownPreParationTimer

    val countUpTimerText: LiveData<String> = Transformations.map(countUpTimer) { countUpTimer ->
        val hours = countUpTimer / 3600
        val minutes = (countUpTimer % 3600) / 60
        val seconds = (countUpTimer % 3600) % 60

        "Up\n%02d:%02d:%02d".format(hours, minutes, seconds)
    }

    val countDownTimerText: LiveData<String> =
        Transformations.map(countDownTimer) { countDownTimer ->
            val hours = countDownTimer / 3600
            val minutes = (countDownTimer % 3600) / 60
            val seconds = (countDownTimer % 3600) % 60

            "Down\n%02d:%02d:%02d".format(hours, minutes, seconds)
        }

    val countDownPreParationTimerText: LiveData<String> =
        Transformations.map(countDownPreParationTimer) { countDownPreParationTimer ->
            "準備中 %d".format(countDownPreParationTimer)
        }

    suspend fun countUpTimer() {
            countUpTime()
    }

    suspend fun countUpTime() {
        withContext(Dispatchers.IO) {
            delay(1000L)
//            mCountUpTimer.value = countUpTimer.value?.plus(1)
            mCountUpTimer.let {
                it.postValue(it.value?.plus(1))
            }
        }
    }

    suspend fun countDownTimer() {
            countDownTime()
    }

    suspend fun countDownTime() {
        withContext(Dispatchers.IO) {
            delay(1000L)
//            mCountDownTimer.value = countDownTimer.value?.minus(1)
            mCountDownTimer.let {
                it.postValue(it.value?.minus(1))
            }
        }
    }
    suspend fun countDownPreparationTimer() {
            countDownPreparationTime()
    }

    suspend fun countDownPreparationTime() {
        withContext(Dispatchers.IO) {
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
//            mCountDownPreParationTimer.value = countDownPreParationTimer.value?.minus(1)
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
//            mCountDownPreParationTimer.value = countDownPreParationTimer.value?.minus(1)
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
//            mCountDownPreParationTimer.value = countDownPreParationTimer.value?.minus(1)
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
//            mCountDownPreParationTimer.value = countDownPreParationTimer.value?.minus(1)
            delay(1000L)
            mCountDownPreParationTimer.let {
                it.postValue(it.value?.minus(1))
            }
//            mCountDownPreParationTimer.value = countDownPreParationTimer.value?.minus(1)
        }
    }

    fun countup() {
        viewModelScope.launch {
            countDownPreparationTime()
            async { countUpTime() }
            async { countDownTime() }
        }
    }
}