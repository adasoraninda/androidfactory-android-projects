package com.codetron.senyaepoxy.arch

import android.content.Context
import androidx.annotation.RawRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codetron.senyaepoxy.data.Attraction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AttractionsViewModel(context: Context) : ViewModel() {

    private val repository = AttractionsRepository(context)

    private val _attractions = MutableLiveData<List<Attraction>>()
    val attractions: LiveData<List<Attraction>> get() = _attractions

    private val _attraction = MutableLiveData<Attraction>()
    val attraction: LiveData<Attraction> get() = _attraction

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun getAttractions(@RawRes resId: Int) {
        viewModelScope.launch {
            delay(3000)
            _attractions.postValue(repository.parseAttractions(resId))
            //_attractions.postValue(emptyList())
        }
    }

    fun getAttractionDetail(id: String) {
        _attraction.value = _attractions.value?.find {
            it.id == id
        }
    }

    fun intentEventLocation() {
        viewModelScope.launch {
            eventChannel.send(Event.IntentLocation)
        }
    }

    sealed class Event {
        object IntentLocation : Event()
    }

}