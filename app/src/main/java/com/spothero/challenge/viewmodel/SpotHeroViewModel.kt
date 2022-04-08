package com.spothero.challenge.viewmodel

import androidx.lifecycle.ViewModel
import com.spothero.challenge.data.SpotHeroApi
import com.spothero.challenge.data.model.Spot

/**
 * @author Hojat Ghasemi,
 * 2022-04-06
 * https://github.com/hojat72elect
 */
class SpotHeroViewModel(private val repository: SpotHeroApi) : ViewModel() {

    val spots = repository.getSpotsObservable()
    private lateinit var chosenSpot: Spot


}