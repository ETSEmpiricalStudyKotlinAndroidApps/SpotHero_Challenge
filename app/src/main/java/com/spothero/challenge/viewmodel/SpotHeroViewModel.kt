package com.spothero.challenge.viewmodel

import androidx.lifecycle.ViewModel
import com.spothero.challenge.data.SpotHeroApi
import com.spothero.challenge.data.model.Spot
import io.reactivex.Single

/**
 * @author Hojat Ghasemi,
 * 2022-04-06
 * https://github.com/hojat72elect
 */
class SpotHeroViewModel(private val repository: SpotHeroApi) : ViewModel() {

    fun getAllSpots(): Single<List<Spot>> {
        return repository.getSpotsObservable()
    }

}