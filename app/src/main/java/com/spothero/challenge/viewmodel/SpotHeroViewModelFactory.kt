package com.spothero.challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spothero.challenge.data.SpotHeroApi

/**
 * @author Hojat Ghasemi,
 * 2022-04-06
 * https://github.com/hojat72elect
 */
class SpotHeroViewModelFactory(private val repository: SpotHeroApi) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        
        // boilerplate that will be used for all ViewModel factories
        if (modelClass.isAssignableFrom(SpotHeroViewModel::class.java)) {
            return SpotHeroViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}