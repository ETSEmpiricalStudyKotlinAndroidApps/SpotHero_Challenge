package com.spothero.challenge

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.spothero.challenge.data.SpotHeroApi
import com.spothero.challenge.data.model.Spot
import com.spothero.challenge.databinding.ActivitySpotDetailsBinding
import com.spothero.challenge.viewmodel.SpotHeroViewModel
import com.spothero.challenge.viewmodel.SpotHeroViewModelFactory
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.NumberFormat

class SpotDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpotDetailsBinding
    private lateinit var spotViewModel: SpotHeroViewModel
    private val TAG = "Details_Activity"
    private var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_spot_details
        ) // for easy connection of Activity with xml files.

        // The builder for ViewModel
        val factory = SpotHeroViewModelFactory(SpotHeroApi(this))

        // All of our interactions with ViewModel will be performed through this "spotViewModel" object.
        spotViewModel = ViewModelProvider(this, factory).get(SpotHeroViewModel::class.java)

        binding.myViewModel = spotViewModel
        // but this binding exists just as long as this Activity lives
        // (this is a good way of getting rid of memory leaks).
        binding.lifecycleOwner = this

        // Get the ID of chosen spot
        val chosenSpotId = intent.getIntExtra(EXTRA_SPOT_ID, -1)

        // Get the Single<Spot> from ViewModel
        spotViewModel.getSpotById(chosenSpotId)
            // we need to have this data on main thread (for loading to UI)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Spot> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "SingleObserver<Spot> onSubscribe() function")
                    compositeDisposable.add(d)
                }

                @SuppressLint("SetTextI18n")
                override fun onSuccess(t: Spot) {
                    // load the UI with your Spot info
                    Glide.with(this@SpotDetailsActivity)
                        .load(Uri.parse("file:/${t.facilityPhoto}"))
                        .into(binding.facilihtyPhoto)
                    binding.buttonBook.text =
                        "Book For ${NumberFormat.getCurrencyInstance().format(t.price / 100.0)}"

                    binding.tvAddressDetailScreen.text = t.address.street
                    binding.tvDistanceDetailScreen.text = t.distance
                    binding.tvDescriptionDetailScreen.text = t.description
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "SingleObserver<Spot> -- ${e.message}")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}