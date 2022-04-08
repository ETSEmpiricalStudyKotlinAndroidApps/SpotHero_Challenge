package com.spothero.challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spothero.challenge.Event
import com.spothero.challenge.data.SpotHeroApi
import com.spothero.challenge.data.model.Spot
import io.reactivex.Single

/**
 * @author Hojat Ghasemi,
 * 2022-04-06
 * https://github.com/hojat72elect
 */
class SpotHeroViewModel(private val repository: SpotHeroApi) : ViewModel() {

    fun getAllSpotsObservable(): Single<List<Spot>> {
        return repository.getSpotsObservable()
    }

    // for handling the events that might happen in View layer.
    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    // The public field is immutable live data. And when other
    // classes access this field, we return a private mutable
    // live data. This is a good practice for data security.

}