package com.spothero.challenge

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spothero.challenge.data.SpotHeroApi
import com.spothero.challenge.data.model.Spot
import com.spothero.challenge.databinding.ActivityMainBinding
import com.spothero.challenge.viewmodel.SpotHeroViewModel
import com.spothero.challenge.viewmodel.SpotHeroViewModelFactory
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

const val EXTRA_SPOT_ID = "com.spothero.challenge.spotId"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var spotViewModel: SpotHeroViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    private val TAG = "Main_Activity"
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        ) // for easy connection of Activity with xml files.

        // The builder for ViewModel
        val factory = SpotHeroViewModelFactory(SpotHeroApi(this))

        // All of our interactions with ViewModel will be performed through this "spotViewModel" object.
        spotViewModel = ViewModelProvider(this, factory).get(SpotHeroViewModel::class.java)

        binding.myViewModel = spotViewModel
        // but this binding exists just as long as this Activity lives
        // (this is a good way of getting rid of memory leaks).
        binding.lifecycleOwner = this
        initRecyclerView()

    }


    /**
     * Initializes the RecyclerView (you will call it whenever you want the
     * list to be loaded from scratch)
     * Pay attention that the function "displaySpotsList()" is only called via "initRecyclerView()" function.
     */
    private fun initRecyclerView() {
        binding.spotsRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter { selectedItem: Spot -> listItemClicked(selectedItem) }
        binding.spotsRecyclerView.adapter = adapter // loading the adapter for our recycler view

        // divider between recycler view rows
        binding.spotsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )
        displaySpotsList()

    }

    /**
     * Looks into the observable we got from ViewModel and then gives the
     * list of the spots to adapter, to be drawn into recycler view.
     *
     * it should be only called inside "initRecyclerView()"
     */
    private fun displaySpotsList() {
        // we're in the View layer so all our data-related
        // communications will be with ViewModel.
        spotViewModel.getAllSpots()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Spot>> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "SingleObserver<List<Spot>> onSubscribe() function")
                    compositeDisposable.add(d)
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onSuccess(t: List<Spot>) {
                    // give your list of spots to the adapter.
                    Log.i("TAG_Spots_list", t.toString())

                    // incrementally sorting the list of spots according to
                    // their prices and pass it as the list to our adapter.
                    adapter.setList(t.sortedBy { it.price })
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "SingleObserver<List<Spot>> -- ${e.message}")
                }

            })
    }

    /**
     * User has chosen one of the spots in the list, get the data and go to
     * another Activity (for showing details about that spot).
     */
    private fun listItemClicked(spot: Spot) {

        val intent = Intent(this, SpotDetailsActivity::class.java).apply {

            // MainActivity will only send the ID of chosen spot to SpotDetailsActivity;
            // the receiver Activity is responsible to contact ViewModel and ask for needed data
            putExtra(EXTRA_SPOT_ID, spot.id)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }

    }
}
