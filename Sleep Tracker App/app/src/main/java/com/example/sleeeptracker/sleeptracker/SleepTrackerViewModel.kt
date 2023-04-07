package com.example.sleeeptracker.sleeptracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.sleeeptracker.database.SleepDatabaseDao
import com.example.sleeeptracker.database.SleepNight
import com.example.sleeeptracker.formatNights
import kotlinx.coroutines.*

class SleepTrackerViewModel(
    val database : SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application){

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>()

    val nights = database.getAllNights()

    val nightsString = Transformations.map(nights){ nights->
        formatNights(nights = nights , application.resources)
    }

    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()

    val navigateToSleepQuality: LiveData<SleepNight?>
        get() = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    private val _navigateToSleepDataQuality = MutableLiveData<Long?>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun onSleepNightClicked(id: Long){
        _navigateToSleepDataQuality.value = id
    }

    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }

//    val startButtonVisible = Transformations.map(tonight) {
//        null == it
//    }
//    val stopButtonVisible = Transformations.map(tonight) {
//        null != it
//    }
//    val clearButtonVisible = Transformations.map(nights) {
//        it?.isNotEmpty()
//    }
//
//    private var _showSnackbarEvent = MutableLiveData<Boolean>()
//
//    val showSnackBarEvent: LiveData<Boolean>
//        get() = _showSnackbarEvent
//
//    fun doneShowingSnackbar() {
//        _showSnackbarEvent.value = false
//    }

    init {
        initializeTonight()
    }

    fun onStartTracking(){
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
            Log.i("sleepTrackerViewModel", "onStartTracking: triggered ")
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            Log.i("sleepTrackerViewModel", "onStopTracking: triggered ")
            val oldNight = tonight.value ?: return@launch
            Log.i("sleepTrackerViewModel", "onStopTracking: first line ok ")

            oldNight.endTimeMilli = System.currentTimeMillis()
            Log.i("sleepTrackerViewModel", "onStopTracking: second line ok ")

            update(oldNight)
            Log.i("sleepTrackerViewModel", "onStopTracking: third line ok ")

            _navigateToSleepQuality.value = oldNight
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
            tonight.value = null

//            _showSnackbarEvent.value = true
        }
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO){
            var night = database.getTonight()
//            if (night?.endTimeMilli == night?.startTimeMilli){
//                night = null
//            }
            night
        }
    }

    private suspend fun insert(night : SleepNight){
        withContext(Dispatchers.IO){
            database.insert(night)
        }
    }

    private suspend fun update(night: SleepNight){
        withContext(Dispatchers.IO){
            database.update(night)
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}